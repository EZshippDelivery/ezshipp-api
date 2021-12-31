package com.ezshipp.api.service;

import static com.ezshipp.api.enums.OrderStatus.ACCEPTED;
import static com.ezshipp.api.enums.OrderStatus.DELIVERED;
import static com.ezshipp.api.enums.OrderStatus.DROP_AT_ZONE;
import static com.ezshipp.api.enums.OrderStatus.PICKED_UP;
import static com.ezshipp.api.enums.PaymentTypeEnum.CASH;
import static com.ezshipp.api.enums.PaymentTypeEnum.MONTHLY;
import static com.ezshipp.api.enums.PaymentTypeEnum.CASH;
import static com.ezshipp.api.enums.PaymentTypeEnum.MONTHLY;
import static com.ezshipp.api.repository.ApplicationGlobalConstants.SYSTEM_USER_ID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BikerStatsAndCod;
import com.ezshipp.api.model.OrderEventTimes;
import com.ezshipp.api.model.OrderFilter;
import com.ezshipp.api.persistence.entity.DriverDueEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.DriverOrderCountEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.DriverDueEntityRepository;
import com.ezshipp.api.repository.DriverOrderCountEntityRepository;

@Service
public class BikerStatsAndCODService {

	 @Autowired
	 DriverOrderCountEntityRepository driverOrderCountEntityRepository;

	 @Autowired
	 OrderEntityService orderEntityService;

	 @Autowired
	 DriverEntityService driverEntityService;

	 @Autowired
	 DriverDueEntityRepository driverDueEntityRepository;
	    
	public void updateBikerStatsAndCodAmounts(int minusDays) throws ServiceException, BusinessException {
       // log.info("updateBikerStatsAndCodAmounts: ");

        OrderFilter orderFilter = new OrderFilter();
        Calendar from = Calendar.getInstance();
        from.set(Calendar.DAY_OF_MONTH, from.get(Calendar.DAY_OF_MONTH) - minusDays);

        Calendar to = Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH, to.get(Calendar.DAY_OF_MONTH) - minusDays);

        orderFilter.setStartDate(from.getTime());
        orderFilter.setEndDate(to.getTime());

//        log.info(from.getTime().toString());
//        log.info(to.getTime().toString());

        List<OrderEntity> orderList = orderEntityService.getAllOrdersByDateFilter(orderFilter);

        Map<Integer, BikerStatsAndCod> bikerCODResponseMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (OrderEntity entity : orderList) {
                setOrderEvents(entity, bikerCODResponseMap);
                OrderEventTimes eventTimes = setOrderEventTimes(entity);
//                if (eventTimes != null) {
//                    log.info("bikerName: " + eventTimes.getBikerName());
//                    log.info("acceptedTime: " + eventTimes.getAcceptedTime());
//                    log.info("pickedTime: " + eventTimes.getPickedTime());
//                    log.info("deliveredTime: " + eventTimes.getDeliveredTime());
//                    log.info("zonedTime: " + eventTimes.getZonedTime());
//                }
            }
        }

        for (Integer bikerId : bikerCODResponseMap.keySet()) {
            BikerStatsAndCod codResponse = bikerCODResponseMap.get(bikerId);
//            log.info("bikerName: " +  codResponse.getName());
//            log.info("deliveredCount: " + codResponse.getDeliveredCount());
//            log.debug("exchangedCount: " + codResponse.getExchangedCount());
//            log.info("pickedCount: " + codResponse.getPickedCount());
//            log.info("picked clients: " + codResponse.getPickedCustomers());
//            log.info("delivered clients: " + codResponse.getDeliveredCustomers());
            // log.debug("deliveryCharges: " + codResponse.getDeliveryAmount());
            // log.debug("codCharges: " + codResponse.getCodAmount());
            //log.debug("\n********************************************");
        }

        saveBikerCodAndDeliveryAmounts(bikerCODResponseMap, from);
        saveBikerStats(bikerCODResponseMap, from);

    }
	
	 private void setOrderEvents(OrderEntity entity, Map<Integer, BikerStatsAndCod> bikerCODResponseMap) throws ServiceException {
	       // log.debug("setOrderEvents");

	        Collection<OrderEventEntity> orderEvents = entity.getOrderEventsById();
	        if (!CollectionUtils.isEmpty(orderEvents)) {
	            Map<Integer, List<OrderEventEntity>> eventsByStatusId = orderEvents.stream()
	                    .collect(Collectors.groupingBy(OrderEventEntity::getStatusId));

	            List<OrderEventEntity> deliveredEvent = eventsByStatusId.get(DELIVERED.getStatusId());
	            List<OrderEventEntity> pickedEvent = eventsByStatusId.get(OrderStatus.PICKED_UP.getStatusId());
	            List<OrderEventEntity> zonedEvent = eventsByStatusId.get(OrderStatus.DROP_AT_ZONE.getStatusId());

	            if (deliveredEvent != null && deliveredEvent.get(0) != null) {
	                setCodAndStatsResponse(bikerCODResponseMap, deliveredEvent.get(0), entity);

	            }
	            if (pickedEvent != null && pickedEvent.get(0) != null) {
	                setCodAndStatsResponse(bikerCODResponseMap, pickedEvent.get(0), entity);
	            }
	            if (zonedEvent != null && zonedEvent.get(0) != null) {
	                setCodAndStatsResponse(bikerCODResponseMap, zonedEvent.get(0), entity);
	            }
	        }
	    }
	 
	 
	  private void setCodAndStatsResponse(Map<Integer, BikerStatsAndCod> bikerCODResponseMap, OrderEventEntity orderEventEntity, OrderEntity entity) {
	        int bikerId = orderEventEntity.getDriverId();
	        BikerStatsAndCod codResponse = bikerCODResponseMap.get(bikerId);
	        boolean isAccepted = orderEventEntity.getStatusId() == ACCEPTED.getStatusId();
	        boolean isDelivered = orderEventEntity.getStatusId() == DELIVERED.getStatusId();
	        boolean isPicked = orderEventEntity.getStatusId() == PICKED_UP.getStatusId();
	        boolean isZoned = orderEventEntity.getStatusId() == DROP_AT_ZONE.getStatusId();
	        if (codResponse == null) {
	            codResponse = new BikerStatsAndCod();
	            codResponse.setName(getName(orderEventEntity.getDriverByDriverId()));
	            codResponse.setBikerId(bikerId);
	            if (!entity.getCustomerByCustomerId().isPremium() && entity.getPaymentType() == CASH) {
	                if ((isPicked && entity.isCollectAtPickUp()) || (isDelivered && !entity.isCollectAtPickUp())) {
	                    codResponse.setDeliveryAmount(entity.getDeliveryCharge());
	                }
	            }

	            if (entity.getPaymentType() == CASH || entity.getPaymentType() == MONTHLY)  {
	                if (isDelivered) {
	                    codResponse.setCodAmount(entity.getCodCharge());
	                }
	            }

	            if (isDelivered)    {
	                List<String> orderSeqIds = new ArrayList<>();
	                orderSeqIds.add(entity.getOrderSeqId());
	                codResponse.setDeliveredOrders(orderSeqIds);
	                codResponse.setDeliveredCount(1);
	            }
	            if (isPicked)    {
	                List<String> orderSeqIds = new ArrayList<>();
	                orderSeqIds.add(entity.getOrderSeqId());
	                codResponse.setPickedOrders(orderSeqIds);
	                codResponse.setPickedCount(1);
	            }
	            if (isZoned)    {
	                List<String> orderSeqIds = new ArrayList<>();
	                orderSeqIds.add(entity.getOrderSeqId());
	                codResponse.setExchangedOrders(orderSeqIds);
	                codResponse.setExchangedCount(1);
	            }
	        } else  {
	            if (!entity.getCustomerByCustomerId().isPremium() && entity.getPaymentType() == CASH) {
	                if ((isPicked && entity.isCollectAtPickUp()) || (isDelivered && !entity.isCollectAtPickUp())) {
	                    if (codResponse.getDeliveryAmount() == null)    {
	                        codResponse.setDeliveryAmount(0.00);
	                    }

	                    double deliveryAmount = codResponse.getDeliveryAmount() + entity.getDeliveryCharge();
	                    codResponse.setDeliveryAmount(deliveryAmount);
	                }
	            }
	            if (entity.getPaymentType() == CASH || entity.getPaymentType() == MONTHLY)  {
	                if (isDelivered) {
	                    if (codResponse.getCodAmount() == null)    {
	                        codResponse.setCodAmount(0.00);
	                    }
	                    double codAmount = codResponse.getCodAmount() + entity.getCodCharge();
	                    codResponse.setCodAmount(codAmount);
	                }
	            }
	            if (isDelivered)    {
	                if (codResponse.getDeliveredCount() == null)    {
	                    codResponse.setDeliveredCount(0);
	                }
	                if (codResponse.getDeliveredOrders() == null)    {
	                    codResponse.setDeliveredOrders(new ArrayList<>());
	                }
	                codResponse.setDeliveredCount(codResponse.getDeliveredCount() + 1);
	                codResponse.getDeliveredOrders().add(entity.getOrderSeqId());
	            }
	            if (isPicked)    {
	                if (codResponse.getPickedCount() == null)    {
	                    codResponse.setPickedCount(0);
	                }
	                if (codResponse.getPickedOrders() == null)    {
	                    codResponse.setPickedOrders(new ArrayList<>());
	                }
	                if (codResponse.getPickedCustomers() == null)    {
	                    codResponse.setPickedCustomers(new ArrayList<>());
	                }

	                // check to see if it is picked from same customer, ex: Indian peacock
	                if (!codResponse.getPickedCustomers().contains(entity.getCustomerId())) {
	                    codResponse.setPickedCount(codResponse.getPickedCount() + 1);
	                    codResponse.getPickedCustomers().add(entity.getCustomerId());
	                } else  {
	                    codResponse.setPickedCount(codResponse.getPickedCount() + 1);
	                }
	                codResponse.getPickedOrders().add(entity.getOrderSeqId());
	            }
	            if (isZoned)    {
	                if (codResponse.getExchangedCount() == null)    {
	                    codResponse.setExchangedCount(0);
	                }
	                if (codResponse.getExchangedOrders() == null)    {
	                    codResponse.setExchangedOrders(new ArrayList<>());
	                }
	                if (codResponse.getExchangedCustomers() == null)    {
	                    codResponse.setExchangedCustomers(new ArrayList<>());
	                }
	                // check to see if it is picked from same customer, ex: Indian peacock
	                if (codResponse.getExchangedCustomers() != null && !codResponse.getExchangedCustomers().contains(entity.getCustomerId())) {
	                    codResponse.setExchangedCount(codResponse.getExchangedCount() + 1);
	                    codResponse.getExchangedCustomers().add(entity.getCustomerId());
	                }
	                codResponse.getExchangedOrders().add(entity.getOrderSeqId());
	            }
	        }

	        codResponse.setRecordedDate(new Date());
	        bikerCODResponseMap.put(bikerId, codResponse);
	    }
	  
	  private String getName(DriverEntity driverEntity)   {
	        if (driverEntity != null && driverEntity.getUserByUserId() != null) {
	            UserEntity userEntity = driverEntity.getUserByUserId();
	            return userEntity.getFirstName() + " " + userEntity.getLastName();
	        }

	        return "";
	    }
	  
	  private OrderEventTimes setOrderEventTimes(OrderEntity entity) throws ServiceException {
	        //log.debug("setOrderEventTimes");

	        Collection<OrderEventEntity> orderEvents = entity.getOrderEventsById();
	        if (!CollectionUtils.isEmpty(orderEvents)) {
	            Map<Integer, List<OrderEventEntity>> eventsByStatusId = orderEvents.stream()
	                    .collect(Collectors.groupingBy(OrderEventEntity::getStatusId));

	            List<OrderEventEntity> acceptedEvent = eventsByStatusId.get(ACCEPTED.getStatusId());
	            List<OrderEventEntity> deliveredEvent = eventsByStatusId.get(DELIVERED.getStatusId());
	            List<OrderEventEntity> pickedEvent = eventsByStatusId.get(OrderStatus.PICKED_UP.getStatusId());
	            List<OrderEventEntity> zonedEvent = eventsByStatusId.get(OrderStatus.DROP_AT_ZONE.getStatusId());
	            OrderEventTimes eventTimes = new OrderEventTimes();

	            if (deliveredEvent != null && deliveredEvent.get(0) != null) {
	                eventTimes.setBikerName(getName(deliveredEvent.get(0).getDriverByDriverId()));
	                eventTimes.setSequenceId(entity.getOrderSeqId());
	                eventTimes.setDeliveredTime(deliveredEvent.get(0).getLastUpdatedTime());
	            }
	            if (pickedEvent != null && pickedEvent.get(0) != null) {
	                eventTimes.setBikerName(getName(pickedEvent.get(0).getDriverByDriverId()));
	                eventTimes.setPickedTime(pickedEvent.get(0).getLastUpdatedTime());
	            }
	            if (zonedEvent != null && zonedEvent.get(0) != null) {
	                eventTimes.setBikerName(getName(zonedEvent.get(0).getDriverByDriverId()));
	                eventTimes.setZonedTime(zonedEvent.get(0).getLastUpdatedTime());
	                eventTimes.setSequenceId(entity.getOrderSeqId());
	            }
	            if (acceptedEvent != null && acceptedEvent.get(0) != null) {
	                eventTimes.setBikerName(getName(acceptedEvent.get(0).getDriverByDriverId()));
	                eventTimes.setSequenceId(entity.getOrderSeqId());
	                eventTimes.setAcceptedTime(acceptedEvent.get(0).getLastUpdatedTime());
	            }

	            return eventTimes;
	        }

	        return null;
	    }
	  
	  
	  private void saveBikerCodAndDeliveryAmounts(Map<Integer, BikerStatsAndCod> bikerCODResponseMap, Calendar date) throws BusinessException {
	        List<DriverDueEntity> entityList = new ArrayList<>();
	        for (Integer bikerId : bikerCODResponseMap.keySet()) {
	            BikerStatsAndCod codResponse = bikerCODResponseMap.get(bikerId);
	            DriverDueEntity driverDueEntity = new DriverDueEntity();
	            driverDueEntity.setDeliveryAmount(codResponse.getDeliveryAmount() == null ? 0.00 : codResponse.getDeliveryAmount());
	            driverDueEntity.setCodAmount(codResponse.getCodAmount() == null ? 0.00 : codResponse.getCodAmount());
	            driverDueEntity.setDriverId(bikerId);
	            driverDueEntity.setDriverByDriverId(driverEntityService.findById(bikerId));
	            driverDueEntity.setRecordedDate(date.getTime());
	            driverDueEntity.setModifiedBy(SYSTEM_USER_ID);
	            driverDueEntity.setCreatedBy(SYSTEM_USER_ID);
	            driverDueEntity.setModifiedTime(new Timestamp(date.getTimeInMillis()));
	            driverDueEntity.setCreatedTime(new Timestamp(date.getTimeInMillis()));
	            entityList.add(driverDueEntity);
	        }
	        driverDueEntityRepository.saveAll(entityList);
	    }
	  
	  private void saveBikerStats(Map<Integer, BikerStatsAndCod> bikerCODResponseMap, Calendar date) throws BusinessException {
	        List<DriverOrderCountEntity> entityList = new ArrayList<>();
	        for (Integer bikerId : bikerCODResponseMap.keySet()) {
	            BikerStatsAndCod codResponse = bikerCODResponseMap.get(bikerId);
	            DriverOrderCountEntity driverOrderCountEntity = new DriverOrderCountEntity();
	            driverOrderCountEntity.setPickedCount(codResponse.getPickedCount() == null ? 0 : codResponse.getPickedCount());
	            driverOrderCountEntity.setDeliveredCount(codResponse.getDeliveredCount() == null ? 0 : codResponse.getDeliveredCount());
	            driverOrderCountEntity.setExchangeCount(codResponse.getExchangedCount() == null ? 0 : codResponse.getExchangedCount());
	            driverOrderCountEntity.setTotalOrderCount(driverOrderCountEntity.getExchangeCount()+driverOrderCountEntity.getPickedCount()+driverOrderCountEntity.getDeliveredCount());
	            driverOrderCountEntity.setTotalLoginHours(0);
	            driverOrderCountEntity.setExtraHour(0);
	            driverOrderCountEntity.setExtraKms(0);
	            driverOrderCountEntity.setDriverId(bikerId);
	            driverOrderCountEntity.setDriverByDriverId(driverEntityService.findById(bikerId));
	            driverOrderCountEntity.setTrackingDate(date.getTime());
	            driverOrderCountEntity.setModifiedBy(SYSTEM_USER_ID);
	            driverOrderCountEntity.setCreatedBy(SYSTEM_USER_ID);
	            driverOrderCountEntity.setModifiedTime(new Timestamp(date.getTimeInMillis()));
	            driverOrderCountEntity.setCreatedTime(new Timestamp(date.getTimeInMillis()));
	            entityList.add(driverOrderCountEntity);
	        }
	        driverOrderCountEntityRepository.saveAll(entityList);
	    }
}
