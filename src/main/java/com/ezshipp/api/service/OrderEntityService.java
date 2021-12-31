package com.ezshipp.api.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.enums.BookingTypeEnum;
import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.CalculateOrderRequest;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.OrderFilter;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.AddressRepository;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.repository.UserRepository;
import com.ezshipp.api.util.OrderResponseHelper;
import com.google.maps.model.LatLng;

@Service
public class OrderEntityService {

//	@Autowired
//	OrderEntityRepository orderEntityRepository;
	
	final private OrderEntityRepository orderEntityRepository;

    final private OrderEventEntityService orderEventEntityService;

    final private CreateOrderService createOrderService;

    //final private OrderService orderService;

    //final private CustomerService customerService;

    final private AuthService authService;

    final private CustomerEntityRepository customerEntityRepository;

    final private AddressRepository addressRepository;

    final private DistanceService distanceService;

    final private UserRepository userRepository;
   
    
    @PersistenceContext
    private EntityManager entityManager;

   // final private CustomerOrderRepository customerOrderRepository;
	 public OrderEntityService(OrderEntityRepository orderEntityRepository,
             OrderEventEntityService orderEventEntityService,
             CreateOrderService createOrderService,
            
             AuthService authService, CustomerEntityRepository customerEntityRepository,
             AddressRepository addressRepository,
             UserRepository userRepository , DistanceService distanceService) {
		 // OrderService orderService, CustomerService customerService, DistanceService distanceService,CustomerOrderRepository customerOrderRepository
this.orderEntityRepository = orderEntityRepository;
this.orderEventEntityService = orderEventEntityService;
this.createOrderService = createOrderService;
this.authService = authService;
this.customerEntityRepository = customerEntityRepository;
this.addressRepository = addressRepository;
this.distanceService = distanceService;
this.userRepository = userRepository;

}
	 
	 public OrderEntity findOrderEntityById(Integer id) throws ServiceException {
	        return orderEntityRepository.findById(id).get();
	    
	 }
	 
	 public OrderEntity persist(OrderEntity entity) throws ServiceException {
	        return orderEntityRepository.save(entity);
	    
	 }
	 
	public OrderResponse findById(Integer id) throws BusinessException, ServiceException {
        Optional<OrderEntity> orderEntityOptional = orderEntityRepository.findById(id);
        if (!orderEntityOptional.isPresent())    {
        	 throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_ID);
        }

        OrderEntity orderEntity = orderEntityOptional.get();
        OrderResponse orderResponse = OrderResponseHelper.buildResponse(orderEntity);
        setOrderEvents(orderEntity, orderResponse);
        return orderResponse;
    }
	
	  private void setOrderEvents(OrderEntity entity, OrderResponse orderResponse)    {
	        Collection<OrderEventEntity> orderEvents = entity.getOrderEventsById();
	        if (!CollectionUtils.isEmpty(orderEvents)) {
	            Map<Integer, List<OrderEventEntity>> eventsByStatusId = orderEvents.stream()
	                    .collect(Collectors.groupingBy(OrderEventEntity::getStatusId));
	            int maxStatusId = orderEvents.stream().mapToInt(OrderEventEntity::getStatusId).max().orElse(-1);

	            List<OrderEventEntity> acceptedEvent = eventsByStatusId.get(OrderStatus.ACCEPTED.getStatusId());
	            List<OrderEventEntity> pickedEvent = eventsByStatusId.get(OrderStatus.PICKED_UP.getStatusId());
	            if (acceptedEvent != null && acceptedEvent.get(0) != null) {
	                orderResponse.setAcceptedTime(acceptedEvent.get(0).getLastUpdatedTime());
	            }
	            if (pickedEvent != null && pickedEvent.get(0) != null) {
	                orderResponse.setPickedTime(pickedEvent.get(0).getLastUpdatedTime());
	                UserEntity userEntity = pickedEvent.get(0).getDriverByDriverId().getUserByUserId();
	                if (userEntity != null) {
	                    orderResponse.setPickedBy(userEntity.getFirstName()+" "+userEntity.getLastName());
	                }
	            }
	            List<OrderEventEntity> deliveredEvent = eventsByStatusId.get(OrderStatus.DELIVERED.getStatusId());
	            if (deliveredEvent != null && deliveredEvent.get(0) != null) {
	                orderResponse.setDeliveredTime(deliveredEvent.get(0).getLastUpdatedTime());
	                UserEntity userEntity = deliveredEvent.get(0).getDriverByDriverId().getUserByUserId();
	                if (userEntity != null) {
	                    orderResponse.setDeliveredBy(userEntity.getFirstName()+" "+userEntity.getLastName());
	                }
	            }
	        }
	    }
	  
	  
	  public Integer findByOrderSeqId(String orderSeqId) throws BusinessException {
	        List<OrderEntity> orderEntities = orderEntityRepository.findByOrderSeqId(orderSeqId);
	        if (!CollectionUtils.isEmpty(orderEntities)) {
	            return orderEntities.get(0).getId();
	        } else  {
	            throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_ID);
	        }
	    }

	 public Double findDues(String query, Integer driverId, Integer statusId, Date orderCompletedStartTime, Date orderCompletedEndTime) throws ServiceException {
        return getaDouble(query, driverId, statusId, orderCompletedStartTime, orderCompletedEndTime, entityManager);
    }
	 
	 private Double getaDouble(String query, Integer driverId, Integer statusId, Date orderCompletedStartTime, Date orderCompletedEndTime, EntityManager entityManager) {
	        try {
	            Query sumQuery = entityManager.createNativeQuery(query);
	            sumQuery.setParameter("statusId", statusId);
	            sumQuery.setParameter("driverId", driverId);
	            sumQuery.setParameter("orderCompletedStartTime", orderCompletedStartTime);
	            sumQuery.setParameter("orderCompletedEndTime", orderCompletedEndTime);
	            Object obj = sumQuery.getSingleResult();
	            if(obj != null) {
	            	return (Double)obj;
	            }
	            else {
	            	return (double) 0.00;
	            }
	        } catch (Exception re)  {
	           // log.error("exception while calculating driver dues: ", re);
	        }
	        return null;
	    }
	 
	 public List<OrderEntity> getAllOrdersByDateFilter(OrderFilter orderFilter) throws ServiceException {
	        List<OrderEntity> orderEntities = null;
	        boolean dateFilterExists = orderFilter.getStartDate() != null && orderFilter.getEndDate() != null;

	        if (dateFilterExists) {
	            Calendar from = setTimes(orderFilter, true);
	            Calendar to = setTimes(orderFilter, false);
//	            log.info("from: " + from.getTime().toString());
//	            log.info("to: " + to.getTime().toString());
	            orderEntities = orderEntityRepository.findByOrderCreatedTimeIsBetween(new Timestamp(from.getTimeInMillis()), new Timestamp(to.getTimeInMillis()));
	        }

	        return orderEntities;
	    }
	 
	 private Calendar setTimes(OrderFilter orderFilter, boolean isFrom)    {
	        if (isFrom) {
	            Calendar from = Calendar.getInstance();
	            from.setTime(orderFilter.getStartDate());
	            from.set(Calendar.HOUR_OF_DAY, 01);
	            from.set(Calendar.MINUTE, 01);
	            return from;
	        } else  {
	            Calendar to = Calendar.getInstance();
	            to.setTime(orderFilter.getEndDate());
	            to.set(Calendar.HOUR_OF_DAY, 23);
	            to.set(Calendar.MINUTE, 59);
	            return to;
	        }
	    }
	 
	 
	  public Double calculateOrderCost(CalculateOrderRequest calculateOrderRequest) throws BusinessException, ServiceException  {


	        // fourhours price
	        /*if (calculateOrderRequest.getBookingType().equalsIgnoreCase("FOURHOURS"))   {
	            return BookingTypeEnum.valueOf(calculateOrderRequest.getBookingType()).getPrice();
	        }*/

	        AddressEntity pickAddress = addressRepository.findById(calculateOrderRequest.getPickAddressId()).get();
	        AddressEntity dropAddress = addressRepository.findById(calculateOrderRequest.getDeliveryAddressId()).get();
	        if (null != pickAddress && null != dropAddress) {
	            LatLng fromLatLng = new LatLng(pickAddress.getLatitude(), pickAddress.getLongitude());
	            LatLng toLatLng = new LatLng(dropAddress.getLatitude(), dropAddress.getLongitude());
	            double distance = distanceService.calculateDistance(fromLatLng, toLatLng);
	            /*if (distance <= 5)  {
	                log.debug("calculateOrderCost(<5): " + distance);
	                return new Double("49.00");
	            } else if (distance > 5 && distance <= 15)  {*/
	            if (distance <= 15)  {
	               // log.debug("calculateOrderCost(<15): " + distance);
	                return new Double("99.00");
	            } else if (distance > 15)  {
	               // log.debug("calculateOrderCost(>15): " + distance);
	                return new Double("129.00");
	            }
	        }

	        /*BookingTypeEnum bookingType = BookingTypeEnum.valueOf(calculateOrderRequest.getBookingType());
	        return bookingType.getPrice();*/
	        return BookingTypeEnum.SAMEDAY.getPrice();
	    }
	  
	  public CustomerOrderResponse findByOrderId(Integer id) throws ServiceException {
	        return OrderResponseHelper.buildCustomerOrderResponse(orderEntityRepository.findById(id).get());
	    }
	  
	  
	  public OrderResponse findByBarcode(String barcode) throws BusinessException, ServiceException {
	        Optional<OrderEntity> orderEntityOptional = orderEntityRepository.findByBarcode(barcode);
	        if (orderEntityOptional.isPresent()) {
	            return OrderResponseHelper.buildResponse(orderEntityOptional.get());
	        } else if (barcode.startsWith("E000"))  {
	            List<OrderEntity> orderEntities = orderEntityRepository.findByOrderSeqId(barcode.trim());
	            if (!CollectionUtils.isEmpty(orderEntities)) {
	                return OrderResponseHelper.buildResponse(orderEntities.get(0));
	            }
	        } else  {
	            throw new BusinessException(BusinessExceptionCode.BARCODE_ID_DOES_NOT_EXIST);
	        }

	        return null;
	    }
}
