package com.ezshipp.api.service;





import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.enums.OrderTypeEnum;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.model.UpdateOrderRequest;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.OrderStatusEntity;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.DriverEntityRepository;
import com.ezshipp.api.repository.OrderEventRepository;
import com.ezshipp.api.repository.OrderStatusRepository;
import com.ezshipp.api.util.OrderResponseHelper;
import com.google.maps.model.LatLng;
@Service
public class OrderEventEntityService extends BaseEntityService{

	@Autowired
	DriverEntityRepository driverEntityRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderEventRepository orderEventRepository;
	
	@Autowired
	CustomerEntityRepository customerEntityRepository;
	
	 public OrderStatusEntity addOrderEvent(OrderEntity orderEntity, int statusId, Integer driverId, LatLng latLng) {
	        DriverEntity driverEntity = driverEntityRepository.findById(driverId).get();
	        OrderStatusEntity orderStatusEntity = orderStatusRepository.getOne(statusId);
	        try {
	        	if(orderEntity.getOrderType() == OrderTypeEnum.SHIP) {
	            OrderEventEntity orderEventEntity = new OrderEventEntity();
	            orderEventEntity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
	            orderEventEntity.setStatusId(orderStatusEntity.getId());
	            orderEventEntity.setOrderStatusByStatusId(orderStatusEntity);
	            orderEventEntity.setOrderByOrderId(orderEntity);
	            orderEventEntity.setOrderId(orderEntity.getId());
	            orderEventEntity.setDriverId(driverEntity.getId());
	            orderEventEntity.setDriverByDriverId(driverEntity);
	            orderEventEntity.setLatitude(latLng != null ? latLng.lat : 0.00);
	            orderEventEntity.setLongitude(latLng != null ? latLng.lng : 0.00);

	            
	            orderEventRepository.save(orderEventEntity);
	            
	        	}else if(orderEntity.getOrderType() == OrderTypeEnum.STORE) {
	        		CustomerEntity customer = customerEntityRepository.findById(orderEntity.getCustomerId()).get();
	        		OrderEventEntity orderEventEntity = new OrderEventEntity();
	                orderEventEntity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
	                orderEventEntity.setStatusId(orderStatusEntity.getId());
	                orderEventEntity.setOrderStatusByStatusId(orderStatusEntity);
	                orderEventEntity.setOrderByOrderId(orderEntity);
	                orderEventEntity.setOrderId(orderEntity.getId());
	                orderEventEntity.setDriverId(driverEntity.getId());
	                orderEventEntity.setDriverByDriverId(driverEntity);
	                orderEventEntity.setLatitude(latLng != null ? latLng.lat : 0.00);
	                orderEventEntity.setLongitude(latLng != null ? latLng.lng : 0.00);
	                orderEventEntity.setCreatedBy(customer.getUserId());
	                orderEventEntity.setLastModifiedBy(customer.getUserId());
	                
	                orderEventRepository.save(orderEventEntity);
	        	}
	        } catch (Exception e)  {
	         //   log.warn("this should not happen for orderId: " + orderEntity.getOrderSeqId());
	            //throw new BusinessException(BusinessExceptionCode.ORDER_STATUS_ALREADY_EXISTS);
	        }
	        return orderStatusEntity;
	    }

	public List<OrderEventEntity> findOrderEventsByDriverIdAndOrderId(Integer driverId, Integer orderId)    {
        return orderEventRepository.findOrderEventEntityByDriverIdAndOrderId(driverId, orderId);
    }
	
	public OrderEventEntity update(OrderEventEntity entity) {
        return orderEventRepository.save(entity);
    }

	  public void addOrderEvent(OrderEntity orderEntity, UpdateOrderRequest updateOrderRequest) throws BusinessException, ServiceException {
        int driverId = updateOrderRequest.getNewDriverId() > 0 ? updateOrderRequest.getNewDriverId() : updateOrderRequest.getDriverId();
        if (driverId == 0)  {
            // set default driver on order in case of cancel
            if (updateOrderRequest.getStatusId() == OrderStatus.CANCELLED.getStatusId())    {
                driverId = orderEntity.getDriverId();
            }
        }
        DriverEntity driverEntity = driverEntityRepository.findById(driverId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.INVALID_DRIVER_ID));

        try {
            OrderEventEntity orderEventEntity = new OrderEventEntity();
            orderEventEntity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
            OrderStatusEntity orderStatusEntity = orderStatusRepository.getOne(updateOrderRequest.getStatusId());
            orderEventEntity.setStatusId(orderStatusEntity.getId());
            orderEventEntity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
            orderEventEntity.setOrderStatusByStatusId(orderStatusEntity);
            orderEventEntity.setOrderByOrderId(orderEntity);
            orderEventEntity.setOrderId(orderEntity.getId());
            orderEventEntity.setDriverId(driverEntity.getId());
            orderEventEntity.setDriverByDriverId(driverEntity);
            orderEventEntity.setLongitude(updateOrderRequest.getLongitude());
            orderEventEntity.setLatitude(updateOrderRequest.getLatitude());
            orderEventRepository.save(orderEventEntity);
        } catch (Exception e)  {
            //log.warn("this should not happen for orderId: " + orderEntity.getOrderSeqId());
            //throw new BusinessException(BusinessExceptionCode.ORDER_STATUS_ALREADY_EXISTS);
        }
    }

	  public List<OrderEventEntity> findOrderEventsByDriverIdAndOrderIdAndStatusId(Integer userId, Integer orderId, Integer statusId)    {
	        return orderEventRepository.findOrderEventEntityByDriverIdAndOrderIdAndStatusId(userId, orderId, statusId);
	    }

	 public PagingResponse findOrdersByDriverIdAndStatusId(Integer driverId, Integer statusId, BaseFilter baseFilter) throws ServiceException    {
        Page<OrderEventEntity> orderEventEntities = null;
        if (baseFilter.getStartDate() != null && baseFilter.getEndDate() != null) {
            orderEventEntities = orderEventRepository.findByDriverIdAndStatusIdAndLastUpdatedTimeBetweenOrderByLastUpdatedTimeDesc
                    (driverId, statusId, createPageRequest(baseFilter), baseFilter.getStartDate(), baseFilter.getEndDate());
        } else  {
            orderEventEntities = orderEventRepository.findOrderEventEntityByDriverIdAndStatusIdOrderByLastUpdatedTimeDesc(driverId, statusId,
                    createPageRequest(baseFilter));
        }

        List<OrderEventEntity> orderEventEntityList = orderEventEntities.getContent();
        List<OrderEntity> orderEntities = orderEventEntityList.stream()
                .map(OrderEventEntity::getOrderByOrderId).collect(Collectors.toList());
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (OrderEntity entity : orderEntities) {
            orderResponseList.add(OrderResponseHelper.buildResponse(entity));
        }

        PagingResponse pagingResponse = buildPagingResponse(orderEventEntities, baseFilter);
        pagingResponse.setData(orderResponseList);
        return pagingResponse;
    }
	 
	 public OrderStatusEntity updateOrderEvent(OrderEntity orderEntity, int statusId, Integer oldDriverId, Integer newDriverId, LatLng latLng) throws BusinessException, ServiceException {
	        DriverEntity newDriverEntity = driverEntityRepository.findById(newDriverId).get();
	        OrderStatusEntity orderStatusEntity = orderStatusRepository.getOne(statusId);
	        List<OrderEventEntity> orderEventEntities = orderEventRepository.findOrderEventEntityByDriverIdAndOrderIdAndStatusId(oldDriverId, orderEntity.getId(), statusId);
	        if (CollectionUtils.isEmpty(orderEventEntities))    {
	           // log.info("No order-event records found, so inserting new event");
	            return addOrderEvent(orderEntity, statusId, newDriverId, latLng);
	        } else {
	            try {
	               // log.info("updating existing order event");
	                OrderEventEntity orderEventEntity = orderEventEntities.get(0);
	                orderEventEntity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
	                orderEventEntity.setStatusId(orderStatusEntity.getId());
	                orderEventEntity.setOrderStatusByStatusId(orderStatusEntity);
	                orderEventEntity.setDriverId(newDriverEntity.getId());
	                orderEventEntity.setDriverByDriverId(newDriverEntity);
	                orderEventRepository.save(orderEventEntity);
	                return orderStatusEntity;
	            } catch (Exception e) {
	               // log.warn("this should not happen for orderId: " + orderEntity.getOrderSeqId());
	                //throw new BusinessException(BusinessExceptionCode.ORDER_STATUS_ALREADY_EXISTS);
	            }
	        }
	        return orderStatusEntity;
	    }
}
