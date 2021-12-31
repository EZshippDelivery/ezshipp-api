package com.ezshipp.api.service;

import static com.ezshipp.api.enums.OrderStatus.NA;
import static com.ezshipp.api.enums.OrderStatus.getById;
import static com.ezshipp.api.repository.ApplicationGlobalConstants.DEFAULT_DRIVER_ID;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.model.UpdateComment;
import com.ezshipp.api.model.UpdateOrderRequest;
import com.ezshipp.api.persistence.entity.DriverCommentEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.OrderCommentEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.OrderStatusEntity;
import com.ezshipp.api.repository.CancellationReasonRepository;
import com.ezshipp.api.repository.DriverCommentEntityRepository;
import com.ezshipp.api.repository.OrderCommentEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.repository.OrderStatusRepository;
import com.ezshipp.api.util.OrderResponseHelper;
@Service
public class UpdateOrderService {

	@Autowired
	OrderEntityRepository orderEntityRepository;
	
	@Autowired
	OrderEventEntityService orderEventEntityService;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	DriverEntityService driverEntityService;
	
	@Autowired
	CancellationReasonRepository cancellationReasonRepository;
	
	@Autowired
	DriverCommentEntityRepository driverCommentEntityRepository;
	
	@Autowired
	OrderCommentEntityRepository orderCommentEntityRepository;
	
	@Autowired 
	PostOrderUpdateService postOrderUpdateService;
	
	
    public OrderResponse update(Integer orderId, UpdateOrderRequest updateOrderRequest) throws BusinessException, ServiceException  {
        OrderEntity orderEntity = getOrder(orderId);
        validateUpdate(updateOrderRequest, orderEntity);

        boolean isStatusChanged = false;
        boolean isUpdatable = false;
        boolean isEnrouteDeliveryFromNoResponseStatus = false;
        if (updateOrderRequest.getStatusId() > 0 && orderEntity.getStatusId() != updateOrderRequest.getStatusId()) {
            if (orderEntity.getStatusId() == OrderStatus.DELIVERED.getStatusId())   {
                throw new BusinessException(BusinessExceptionCode.ORDER_ALREADY_DELIVERED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DROP_AT_ZONE)    {
                orderEntity.setZonedId(updateOrderRequest.getZoneId());
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.ENROUTE_DELIVERY &&
                    orderEntity.getStatusId() == OrderStatus.NO_RESPONSE.getStatusId())    {
                List<OrderEventEntity> orderEvents = orderEventEntityService.findOrderEventsByDriverIdAndOrderIdAndStatusId
                        (updateOrderRequest.getDriverId(),orderEntity.getId(), OrderStatus.ENROUTE_DELIVERY.getStatusId());
                if (!CollectionUtils.isEmpty(orderEvents))  {
                    orderEventEntityService.update(orderEvents.get(0));
                    isEnrouteDeliveryFromNoResponseStatus = true;
                }
            }
            //update status
            OrderStatusEntity orderStatusEntity = orderStatusRepository.findById(updateOrderRequest.getStatusId()).orElseThrow();
            
            orderEntity.setOrderStatusByStatusId(orderStatusEntity);
            orderEntity.setStatusId(updateOrderRequest.getStatusId());
            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DELIVERED)  {
                orderEntity.setOrderCompletedTime(new Timestamp(System.currentTimeMillis()));
                DriverEntity driverEntity = driverEntityService.findById(updateOrderRequest.getDriverId());
                orderEntity.setDriverId(updateOrderRequest.getDriverId());
                orderEntity.setDriverByDriverId(driverEntity);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.CANCELLED) {
                if (updateOrderRequest.getCancelReasonId() == null) {
                    throw new BusinessException(BusinessExceptionCode.CANCEL_REASON_REQUIRED);
                } else  {
                    orderEntity.setCancellationReasonByReasonId(cancellationReasonRepository.getOne(updateOrderRequest.getCancelReasonId()));
                    orderEntity.setCancellationReasonId(updateOrderRequest.getCancelReasonId());
                }
            }

            isStatusChanged = true;
            isUpdatable = true;
        }

        //update exceeded weight
        if (updateOrderRequest.getExceededWeight() != null && updateOrderRequest.getExceededWeight() > 0) {
            orderEntity.setWeight(updateOrderRequest.getExceededWeight());
            if (!orderEntity.getCustomerByCustomerId().isPremium()) {
                orderEntity.setDeliveryCharge(orderEntity.getDeliveryCharge() + (updateOrderRequest.getExceededWeight() * 25));
            } else {
                orderEntity.setDeliveryCharge(orderEntity.getDeliveryCharge());
            }
            isUpdatable = true;
        }

        if (!StringUtils.isEmpty(updateOrderRequest.getCollectAt())) {
            if (updateOrderRequest.getCollectAt().equalsIgnoreCase("PICKUP")) {
                orderEntity.setCollectAtPickUp(true);
                isUpdatable = true;
            }

            if (updateOrderRequest.getCollectAt().equalsIgnoreCase("DELIVERY")) {
                orderEntity.setCollectAtPickUp(false);
                isUpdatable = true;
            }
        }

        //update driver comments
        if (!StringUtils.isEmpty(updateOrderRequest.getDriverComments())) {
            DriverCommentEntity driverCommentEntity = new DriverCommentEntity();
            driverCommentEntity.setComments(updateOrderRequest.getDriverComments());
            driverCommentEntity.setOrderByOrderId(orderEntity);
            driverCommentEntityRepository.save(driverCommentEntity);
        }

        //update admin/customer comments
        if (!StringUtils.isEmpty(updateOrderRequest.getOrderComments())) {
            UpdateComment updateComment = new UpdateComment();
            updateComment.setOrderComments(updateOrderRequest.getOrderComments());
            updateComments(orderEntity.getId(), updateComment);
        }

        //update distance
        if (updateOrderRequest.getDistance() != null && updateOrderRequest.getDistance() > 0 && orderEntity.getDistance() != updateOrderRequest.getDistance()) {
            orderEntity.setDistance(updateOrderRequest.getDistance());
            isUpdatable = true;
        }

        //update signature url
        if (!StringUtils.isEmpty(updateOrderRequest.getSignUrl()) && !updateOrderRequest.getSignUrl().equalsIgnoreCase(orderEntity.getCustomerSignature())) {
            orderEntity.setCustomerSignature(updateOrderRequest.getSignUrl());
            isUpdatable = true;
        }

        //update waiting time
        if (updateOrderRequest.getWaitingTime() > 0 && updateOrderRequest.getWaitingTime() != orderEntity.getWaitingTime()) {
            orderEntity.setWaitingTime(updateOrderRequest.getWaitingTime());
            isUpdatable = true;
        }

        //update delivered at
        if (!StringUtils.isEmpty(updateOrderRequest.getDeliveredAt()) && !updateOrderRequest.getDeliveredAt().equalsIgnoreCase(orderEntity.getDeliveredAt())) {
            orderEntity.setDeliveredAt(updateOrderRequest.getDeliveredAt());
            isUpdatable = true;
        }

        if (!StringUtils.isEmpty(updateOrderRequest.getBarcode()) && !updateOrderRequest.getBarcode().equalsIgnoreCase(orderEntity.getBarcode())) {
            //update barcode
            orderEntity.setBarcode(updateOrderRequest.getBarcode());
            isUpdatable = true;
        }

        //update barcode
        if (!StringUtils.isEmpty(updateOrderRequest.getBarcode()) && updateOrderRequest.getNewDriverId() > 0) {
            if (StringUtils.isEmpty(orderEntity.getBarcode()) || !orderEntity.getBarcode().equalsIgnoreCase(orderEntity.getBarcode()))  {
                throw new BusinessException(BusinessExceptionCode.BARCODE_ID_DOES_NOT_EXIST);
            }
            orderEntity.setBarcode(updateOrderRequest.getBarcode());
            DriverEntity driverEntity = driverEntityService.findById(updateOrderRequest.getNewDriverId());
            orderEntity.setDriverId(driverEntity.getId());
            orderEntity.setDriverByDriverId(driverEntity);
            isUpdatable = true;
        }

        if (isUpdatable) {
            orderEntityRepository.save(orderEntity);
        }
        if ((isStatusChanged && !isEnrouteDeliveryFromNoResponseStatus) || getById(updateOrderRequest.getStatusId()) == OrderStatus.SCANNED) {
            orderEventEntityService.addOrderEvent(orderEntity, updateOrderRequest);
            if (updateOrderRequest.getDriverId() == 0)  {
                updateOrderRequest.setDriverId(DEFAULT_DRIVER_ID);
            }
            //update defualt driver
            DriverEntity driverEntity = driverEntityService.findById(updateOrderRequest.getDriverId());
            orderEntity.setDriverId(updateOrderRequest.getDriverId());
            orderEntity.setDriverByDriverId(driverEntity);
        }

        postOrderUpdateService.setOrderEntity(orderEntity);
        postOrderUpdateService.setUpdateOrderRequest(updateOrderRequest);
        //postOrderUpdateService.process(userPrincipal);

        return OrderResponseHelper.buildResponse(orderEntity);
    }
    
    protected void validateUpdate(UpdateOrderRequest updateOrderRequest, OrderEntity entity) throws BusinessException {
        if (updateOrderRequest.getStatusId() > 0) {
            if (getById(updateOrderRequest.getStatusId()) == NA)    {
                throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_STATUS_ID);
            }
            if (!StringUtils.isEmpty(updateOrderRequest.getOrderComments()))    {
                return;
            }
            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DROP_AT_ZONE && updateOrderRequest.getZoneId() == 0)    {
                throw new BusinessException(BusinessExceptionCode.ZONE_ID_REQUIRED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DROP_AT_ZONE
                    && StringUtils.isEmpty(updateOrderRequest.getBarcode()))    {
                throw new BusinessException(BusinessExceptionCode.BARCODE_ID_REQUIRED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.ENROUTE_DELIVERY &&
                    entity.getStatusId() == OrderStatus.DROP_AT_ZONE.getStatusId()
                    && StringUtils.isEmpty(updateOrderRequest.getBarcode()))    {
                throw new BusinessException(BusinessExceptionCode.BARCODE_ID_REQUIRED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.ENROUTE_DELIVERY &&
                    entity.getStatusId() == OrderStatus.DROP_AT_ZONE.getStatusId()
                    && updateOrderRequest.getNewDriverId() <= 0 )    {
                throw new BusinessException(BusinessExceptionCode.NEW_DRIVER_ID_REQUIRED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.CANCELLED) {
                if (StringUtils.isEmpty(updateOrderRequest.getDriverComments()) && updateOrderRequest.getCancelReasonId() == null) {
                    throw new BusinessException(BusinessExceptionCode.CANCEL_REASON_REQUIRED);
                }
                if (updateOrderRequest.getCancelReasonId() == null) {
                    throw new BusinessException(BusinessExceptionCode.CANCEL_REASON_REQUIRED);
                }
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DELIVERED
                    && StringUtils.isEmpty(updateOrderRequest.getDeliveredAt()))    {
                throw new BusinessException(BusinessExceptionCode.DELIVERED_AT_REQUIRED);
            }

            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DELIVERED
                    && StringUtils.isEmpty(updateOrderRequest.getSignUrl()))    {
                throw new BusinessException(BusinessExceptionCode.SIGN_URL_REQUIRED);
            }

//            if (getById(updateOrderRequest.getStatusId()) == OrderStatus.DELIVERED &&
//                    entity.getStatusId() == OrderStatus.DROP_AT_ZONE.getStatusId() &&
//                    (StringUtils.isEmpty(entity.getBarcode()) ||
//                            (StringUtils.isEmpty(updateOrderRequest.getBarcode()) &&
//                    !updateOrderRequest.getBarcode().equalsIgnoreCase(entity.getBarcode()))))  {
//                throw new BusinessException(BusinessExceptionCode.BARCODE_ID_DOES_NOT_EXIST);
//            }
        } else  {
            throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_STATUS_ID);
        }
    }
    
    private OrderEntity getOrder(Integer orderId) throws BusinessException   {
       
        if (orderId <= 0)    {
            throw new BusinessException(BusinessExceptionCode.INVALID_ORDER_ID);
        }

        return orderEntityRepository.findById(orderId).orElseThrow(() -> new BusinessException(BusinessExceptionCode.INVALID_ORDER_ID));
    }
    
    public OrderResponse updateComments(Integer orderId, UpdateComment updateComment) throws BusinessException, ServiceException  {
        OrderEntity orderEntity = getOrder(orderId);
        if (!StringUtils.isEmpty(updateComment.getOrderComments())) {
            OrderCommentEntity orderCommentEntity = new OrderCommentEntity();
            orderCommentEntity.setComments(updateComment.getOrderComments());
            orderCommentEntity.setOrderByOrderId(orderEntity);
            orderCommentEntityRepository.save(orderCommentEntity);
        }
        return OrderResponseHelper.buildResponse(orderEntity);
    }
}
