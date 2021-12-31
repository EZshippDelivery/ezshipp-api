package com.ezshipp.api.service;

import static com.ezshipp.api.constants.ApplicationGlobalConstants.DEFAULT_DRIVER_ID;
import static com.ezshipp.api.enums.BookingTypeEnum.FOURHOURS;
import static com.ezshipp.api.enums.BookingTypeEnum.INSTANT;
//import static com.ezshipp.api.util.SlackUtil.SLACK_MESSAGE;
import static java.util.Comparator.comparingDouble;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.constants.ApplicationGlobalConstants;
import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.enums.UserType;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.ManualAssignRequest;
import com.ezshipp.api.model.MatrixDistance;
import com.ezshipp.api.model.PushResponse;
import com.ezshipp.api.notification.FCMNotification;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.persistence.entity.CustomerDriverEntity;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.DriverTrackingEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderStatusEntity;
import com.ezshipp.api.persistence.entity.PushEventEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.CustomerDriverEntityRepository;
import com.ezshipp.api.repository.DriverEntityRepository;
import com.ezshipp.api.repository.DriverTrackingEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.repository.PushEventRepository;
import com.ezshipp.api.util.DateUtil;
//import com.ezshipp.api.util.SlackUtil;
import com.google.maps.model.LatLng;
@Service	
public class BikerAssignmentService {

	@Autowired
	CustomerDriverEntityRepository customerDriverEntityRepository;
	
	@Autowired
	DriverTrackingEntityRepository driverTrackingEntityRepository;
	
	@Autowired
	OrderEventEntityService orderEventEntityService;
	
	@Autowired
	GoogleMapService googleMapService;
	
	@Autowired
	OrderEntityRepository orderEntityRepository;
	
	@Autowired
	DriverEntityRepository driverEntityRepository;
	
	@Autowired
	FCMNotification fcmNotification;
	
	@Autowired
	PushEventRepository pushEventRepository;
	
    public boolean assignCustomerBikers(OrderEntity orderEntity) throws ServiceException {
        CustomerEntity customerEntity = orderEntity.getCustomerByCustomerId();
        LatLng latLng = new LatLng(17.100, 73.4000);
        LatLng driverLocation = null;
        boolean isAssigned = false;
        if (customerEntity.isPremium()) {
            List<CustomerDriverEntity> assignedBikerList = customerDriverEntityRepository.findAllByCustomerId(customerEntity.getId());
            if (!CollectionUtils.isEmpty(assignedBikerList))    {
                Map<Integer, CustomerDriverEntity> customerDriverEntityMap = assignedBikerList.stream().collect(Collectors.toMap(CustomerDriverEntity::getDriverId, u -> u));
                for (Integer bikerId : customerDriverEntityMap.keySet()) {
                    DriverTrackingEntity tracking = driverTrackingEntityRepository.findByDriverIdAndTrackDate(bikerId, DateUtil.getTodayDate());
                    if (tracking != null && tracking.isOnlineMode() && tracking.getLastLatitude() > 0.00 && tracking.getLastLongitude() > 0.00) {
                        driverLocation = new LatLng(tracking.getLastLatitude(), tracking.getLastLongitude());
                    }
                    OrderStatusEntity orderStatusEntity = orderEventEntityService.addOrderEvent(orderEntity, OrderStatus.ASSIGNED.getStatusId(),
                            bikerId, driverLocation != null ? driverLocation : latLng);
                    DriverEntity driverEntity = customerDriverEntityMap.get(bikerId).getDriverByDriverId();

                    // calculate the distance
                    AddressEntity pickAddress = orderEntity.getAddressByPickupAddressId();
                    AddressEntity dropAddress = orderEntity.getAddressByDropAddressId();
                    double distance = 0.00;
                    if (null != pickAddress && null != dropAddress) {
                        LatLng fromLatLng = new LatLng(pickAddress.getLatitude(), pickAddress.getLongitude());
                        LatLng toLatLng = new LatLng(dropAddress.getLatitude(), dropAddress.getLongitude());
                        List<MatrixDistance> matrixDistances = googleMapService.calculateDistance(new LatLng[]{fromLatLng}, new LatLng[]{toLatLng});
                        if (!CollectionUtils.isEmpty(matrixDistances))  {
                            distance = matrixDistances.get(0).getDistance();
                        }
                    }
                    orderEntity.setDistance(distance);
                    orderEntity.setDriverByDriverId(driverEntity);
                    orderEntity.setDriverId(driverEntity.getId());
                    orderEntity.setOrderStatusByStatusId(orderStatusEntity);
                    orderEntityRepository.save(orderEntity);

                    buildFCMMessage(driverEntity.getDeviceByDeviceId().getDeviceToken(), orderEntity);

                    isAssigned = true;
                }
            }
        }
        return isAssigned;
    }
    
    public void assignBiker(OrderEntity orderEntity) throws ServiceException {
        Map<Integer, MatrixDistance> driverByDistanceMatrixMap = new HashMap<>();
        Map<Integer, Double> driverByDistanceMap = new HashMap<>();
        Map<Integer, DriverEntity> bikersResponseMap = new HashMap<>();
        Map<Integer, LatLng> bikersLongLatMap = new HashMap<>();
        DriverEntity defaultDriver = null;

//        if (orderEntity.getCustomerByCustomerId().isPremium())  {
//            assignCustomerBikers(orderEntity);
//        }

        List<DriverEntity> bikerResponses = driverEntityRepository.findByActiveIsTrue();
        LatLng pickupLocation = new LatLng(orderEntity.getAddressByPickupAddressId().getLatitude(), orderEntity.getAddressByPickupAddressId().getLongitude());
        for (DriverEntity biker : bikerResponses) {
            if (biker.getId() == ApplicationGlobalConstants.DEFAULT_DRIVER_ID)   {
                defaultDriver = biker;
            }
            DriverTrackingEntity tracking = driverTrackingEntityRepository.findByDriverIdAndTrackDate(biker.getId(), DateUtil.getTodayDate());
            //List<CustomerDriverEntity> assignedBikerList = customerDriverEntityRepository.findAllByCustomerId(CENTRAL_BOOKS_CUSTOMER);
            //Map<Integer, CustomerDriverEntity> customerDriverEntityMap = assignedBikerList.stream().collect(Collectors.toMap(CustomerDriverEntity::getDriverId, u -> u));

            // skip if biker tracking not available for now
            if (tracking != null && tracking.isOnlineMode() && tracking.getLastLatitude() > 0.00 && tracking.getLastLongitude() > 0.00) {
                LatLng driverLocation = new LatLng(tracking.getLastLatitude(), tracking.getLastLongitude());
               // log.info("Biker LongLats: "+ tracking.getLastLongitude() + "->"+ tracking.getLastLatitude());
                List<MatrixDistance> matrixDistanceList = null;
                matrixDistanceList = googleMapService.calculateDistance(new LatLng[]{driverLocation}, new LatLng[]{pickupLocation});

                orderEntity.setPickDistance(matrixDistanceList.get(0).getDistance());
                orderEntity.setPickDuration(matrixDistanceList.get(0).getDuration());
                bikersResponseMap.put(biker.getId(), biker);
                driverByDistanceMap.put(biker.getId(), matrixDistanceList.get(0).getDistance());
                driverByDistanceMatrixMap.put(biker.getId(), matrixDistanceList.get(0));
                bikersLongLatMap.put(biker.getId(), driverLocation);
            }
        }

        // nearest biker and extract distance and duration
        if (driverByDistanceMap.size() > 0) {
            Integer assignedBikerId = Collections.min(driverByDistanceMap.entrySet(), comparingDouble(Map.Entry::getValue)).getKey();
            DriverEntity assignedDriver = bikersResponseMap.get(assignedBikerId);
            String name = assignedDriver.getUserByUserId().getFirstName()+assignedDriver.getUserByUserId().getLastName();
         //   log.info(String.format("Biker[%1$s] assigned to OrderSeqId[%2$s]", name, orderEntity.getOrderSeqId()));

         //   log.info("before inserting order event: " + orderEntity.getId() + "->" + OrderStatus.ASSIGNED.getStatusId() + "->" + assignedBikerId);
            OrderStatusEntity orderStatusEntity = null;
            try {
                orderStatusEntity = orderEventEntityService.addOrderEvent(orderEntity, OrderStatus.ASSIGNED.getStatusId(), assignedBikerId, bikersLongLatMap.get(assignedBikerId));
            } catch (Exception se) {
                // ignore this in case if this occurs
                if (se.getMessage().contains("order_event_status_orderid_uindex")) {
                  //  log.info("order_event_status_orderid_uindex occured");
                }
            }

            updateOrder(orderEntity, assignedDriver, orderStatusEntity);
            pushMessage(assignedDriver, orderEntity);
            //sendEmail(orderEntity);
            if (orderEntity.getBookingId() == FOURHOURS.ordinal() || orderEntity.getBookingId() == INSTANT.ordinal() )  {
                UserEntity userEntity = orderEntity.getCustomerByCustomerId().getUserByUserId();
//                SlackUtil.sendSlackMessage(String.format(SLACK_MESSAGE, orderEntity.getOrderSeqId(),
//                        (userEntity.getFirstName() + " " + userEntity.getLastName()), userEntity.getPhone(), orderEntity.getOrderCreatedTime()),
//                        orderEntity.getBookingId() == FOURHOURS.ordinal()  ? SlackUtil.FOUR_HOUR_CHANNEL : SlackUtil.INSTANT_CHANNEL,
//                        orderEntity.getOrderSeqId());
            }

        } else  {
            OrderStatusEntity orderStatusEntity = orderEventEntityService.addOrderEvent(orderEntity, OrderStatus.NEW.getStatusId(), DEFAULT_DRIVER_ID, null);
            updateOrder(orderEntity, defaultDriver, orderStatusEntity);
        }
    }
    
    private void updateOrder(OrderEntity orderEntity, DriverEntity driverEntity, OrderStatusEntity orderStatusEntity)  throws ServiceException {
        if (orderStatusEntity != null) {
            setOrderShipDistance(orderEntity);
            orderEntity.setStatusId(orderStatusEntity.getId());
            orderEntity.setDriverByDriverId(driverEntity);
            orderEntity.setDriverId(driverEntity.getId());
            orderEntity.setOrderStatusByStatusId(orderStatusEntity);
            orderEntityRepository.save(orderEntity);
        }
    }
    
    
    private void setOrderShipDistance(OrderEntity orderEntity) throws ServiceException    {
        LatLng pickupLocation = new LatLng(orderEntity.getAddressByPickupAddressId().getLatitude(), orderEntity.getAddressByPickupAddressId().getLongitude());
        LatLng dropLocation = new LatLng(orderEntity.getAddressByDropAddressId().getLatitude(), orderEntity.getAddressByDropAddressId().getLongitude());
        List<MatrixDistance> matrixDistanceList = googleMapService.calculateDistance(new LatLng[]{pickupLocation}, new LatLng[]{dropLocation});
        orderEntity.setDistance(matrixDistanceList.get(0).getDistance());
        orderEntity.setDuration(matrixDistanceList.get(0).getDuration());
    }
    
    private void pushMessage(DriverEntity driverEntity, OrderEntity orderEntity)    {
        String message = buildFCMMessage(driverEntity.getDeviceByDeviceId().getDeviceToken(), orderEntity);
        // if (driverEntity.getDeviceByDeviceId().getDeviceType() == DeviceTypeEnum.ANDROID)   {
        PushResponse pushResponse = fcmNotification.pushFCMNotification(message, driverEntity.getDeviceByDeviceId().getDeviceType());
        PushEventEntity pushEventEntity = new PushEventEntity();
        pushEventEntity.setEventId(orderEntity.getId());
        pushEventEntity.setFailure(pushResponse.getFailure() == 1);
        pushEventEntity.setSuccess(pushResponse.getSuccess() == 1);
        pushEventEntity.setMultiCastId(pushResponse.getMultiCastId());
        pushEventEntity.setResult(pushResponse.results());
        pushEventEntity.setType(UserType.DRIVER.name().toLowerCase());
        pushEventRepository.save(pushEventEntity);
        //  }
    }
    
    private String buildFCMMessage(String deviceToken, OrderEntity orderEntity)   {
        JSONObject json = new JSONObject();

        JSONObject message = new JSONObject();
        JSONObject notification = new JSONObject();
        notification.put("title", "ezshipp"); // Notification title
        notification.put("body", "NEW ORDER REQUEST"); // Notification body
        notification.put("android_channel_id", "ezship_channel");
        message.put("title", "ezshipp"); // data title
        message.put("body", "NEW ORDER REQUEST"); // data body
        message.put("orderid", orderEntity.getId());
        message.put("orderSeqId", orderEntity.getOrderSeqId());
        message.put("type", "NEW");
        message.put("ordermsg", "new order waiting for driver");

        json.put("notification", notification);
        json.put("data", message);
        json.put("priority", "high");
        json.put("collapse_key", "your_collapse_key");
        json.put("to",deviceToken.trim());
        return json.toString();
    }
    
    public boolean manualAssignBiker(ManualAssignRequest manualAssignRequest) throws BusinessException,ServiceException {
        LatLng latLng = new LatLng(17.100, 73.4000);

        DriverEntity newDriverEntity = null;
        if (manualAssignRequest.getOldBikerId() != null && manualAssignRequest.getOldBikerId() <= 0) {
            if (manualAssignRequest.getStatusId() !=  OrderStatus.NEW.getStatusId()) {
                throw new BusinessException(BusinessExceptionCode.INVALID_DRIVER_ID);
            }
        }

        if (manualAssignRequest.getNewBikerId() != null && manualAssignRequest.getNewBikerId() <= 0) {
            throw new BusinessException(BusinessExceptionCode.INVALID_DRIVER_ID);
        }

        Optional<OrderEntity> orderEntityOptional = orderEntityRepository.findById(manualAssignRequest.getOrderId());
        if (!orderEntityOptional.isPresent())    {
            throw new BusinessException(BusinessExceptionCode.ORDER_NOT_FOUND);
        }

        newDriverEntity = driverEntityRepository.findById(manualAssignRequest.getNewBikerId()).get();
        //assign new event
        OrderStatus orderStatus = OrderStatus.ASSIGNED;
        if (manualAssignRequest.getStatusId() !=  OrderStatus.NEW.getStatusId())    {
            orderStatus = OrderStatus.getById(manualAssignRequest.getStatusId());
        }

        String newBikerName = newDriverEntity.getUserByUserId().getFirstName() + " " + newDriverEntity.getUserByUserId().getLastName();
       // log.info(String.format("manual assignment to new biker %s: ", newBikerName));
        OrderStatusEntity orderStatusEntity = orderEventEntityService.updateOrderEvent(orderEntityOptional.get(), orderStatus.getStatusId(), manualAssignRequest.getOldBikerId(), manualAssignRequest.getNewBikerId(), latLng);
        OrderEntity entity = orderEntityOptional.get();
        entity.setDriverByDriverId(newDriverEntity);
        entity.setDriverId(manualAssignRequest.getNewBikerId());
        entity.setOrderStatusByStatusId(orderStatusEntity);
        orderEntityRepository.save(orderEntityOptional.get());

        buildFCMMessage(newDriverEntity.getDeviceByDeviceId().getDeviceToken(), orderEntityOptional.get());

        return true;
    }
}
