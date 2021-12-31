package com.ezshipp.api.util;



import org.apache.commons.lang3.StringUtils;

import com.ezshipp.api.enums.BookingTypeEnum;
import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.UserEntity;

public class OrderResponseHelper {

	   private static final int ADDITIONAL_WEIGHT_CHARGES = 25;

	    public static OrderResponse buildResponse(OrderEntity entity)  {
	        OrderResponse orderResponse = new OrderResponse();
	        orderResponse.setId(entity.getId());
	        orderResponse.setOrderSeqId(entity.getOrderSeqId());
	        orderResponse.setOrderCreatedTime(entity.getOrderCreatedTime());
	        orderResponse.setDeliveredTime(entity.getOrderCompletedTime());
	        orderResponse.setDeliveredTime(entity.getOrderCompletedTime());
	        orderResponse.setStatusId(entity.getStatusId());
	        orderResponse.setStatus(OrderStatus.getById(entity.getStatusId()).getStatus());

	        orderResponse.setBookingTypeId(entity.getBookingId());
	        orderResponse.setBookingType(BookingTypeEnum.values()[entity.getBookingId()].getType());
	        orderResponse.setOrderType(entity.getOrderType().name());
	        orderResponse.setPaymentType(entity.getPaymentType().name());

	        orderResponse.setSenderName(entity.getSenderName());
	        orderResponse.setSenderPhone(entity.getSenderPhone());
	        orderResponse.setReceiverName(entity.getReceiverName());
	        orderResponse.setReceiverPhone(entity.getReceiverPhone());
	        UserEntity customer = entity.getCustomerByCustomerId().getUserByUserId();
	        orderResponse.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
	        orderResponse.setCustomerPhone(String.valueOf(customer.getPhone()));
	        orderResponse.setPickZone(entity.getZoneByPickupZoneId().getName());
	        orderResponse.setDropZone(entity.getZoneByDropZoneId().getName());

	        orderResponse.setDeliveryCharge(entity.getDeliveryCharge());

	        orderResponse.setCodCharge(entity.getCodCharge());
	        orderResponse.setAdditionalWeightCharge(entity.getWeight() != null ? entity.getWeight() * ADDITIONAL_WEIGHT_CHARGES : 0);
	        orderResponse.setTotalCharge(entity.getDeliveryCharge() + entity.getCodCharge() + orderResponse.getAdditionalWeightCharge());
	        orderResponse.setCollectAtPickup(entity.isCollectAtPickUp());
	        orderResponse.setCollectAt(entity.isCollectAtPickUp() ? "Pickup" : "Delivery");

	        orderResponse.setPickAddressId(entity.getAddressByPickupAddressId().getId());
	        orderResponse.setPickAddress(entity.getAddressByPickupAddressId().getAddress1());
	        if (!StringUtils.isEmpty(entity.getAddressByPickupAddressId().getAddress2()))   {
	            orderResponse.setPickAddress(orderResponse.getPickAddress()+","+entity.getAddressByPickupAddressId().getAddress2());
	        }
	        orderResponse.setPickAddress(orderResponse.getPickAddress().replace("Hyderabad", ""));
	        orderResponse.setPickAddress(orderResponse.getPickAddress().replace("Telangana", ""));
	        orderResponse.setPickAddress(orderResponse.getPickAddress().replace("India", ""));

	        orderResponse.setPickFlatNumber(entity.getAddressByPickupAddressId().getApartment());
	        orderResponse.setPickLandmark(entity.getAddressByPickupAddressId().getLandmark());
	        orderResponse.setPickLongitude(entity.getAddressByPickupAddressId().getLongitude());
	        orderResponse.setPickLatitude(entity.getAddressByPickupAddressId().getLatitude());

	        orderResponse.setDropAddressId(entity.getAddressByDropAddressId().getId());
	        orderResponse.setDropAddress(entity.getAddressByDropAddressId().getAddress1());
	        if (!StringUtils.isEmpty(entity.getAddressByDropAddressId().getAddress2()))   {
	            orderResponse.setDropAddress(orderResponse.getDropAddress()+","+entity.getAddressByDropAddressId().getAddress2());
	        }
	        orderResponse.setDropAddress(orderResponse.getDropAddress().replace("Hyderabad", ""));
	        orderResponse.setDropAddress(orderResponse.getDropAddress().replace("Telangana", ""));
	        orderResponse.setDropAddress(orderResponse.getDropAddress().replace("India", ""));
	        orderResponse.setDropFlatNumber(entity.getAddressByDropAddressId().getApartment());
	        orderResponse.setDropLandmark(entity.getAddressByDropAddressId().getLandmark());
	        orderResponse.setDropLongitude(entity.getAddressByDropAddressId().getLongitude());
	        orderResponse.setDropLatitude(entity.getAddressByDropAddressId().getLatitude());

	        orderResponse.setItem(entity.getItem());
	        orderResponse.setItemImage(entity.getItemImage());
	        orderResponse.setItemWeight(entity.getWeight() != null ? entity.getWeight() : 0);
	        orderResponse.setBarCode(entity.getBarcode());

	        orderResponse.setPickDistance(entity.getPickDistance() != null ? entity.getPickDistance() : 0.00);
	        orderResponse.setPickDuration(entity.getPickDuration() != null ? entity.getPickDuration() : 0.00);
	        orderResponse.setPickToDropDistance(entity.getDistance() != null ? entity.getDistance() : 0.00);
	        orderResponse.setPickToDropDuration(entity.getDuration() != null ? entity.getDuration() : 0.00);

	        if (entity.getDriverByDriverId() != null && entity.getDriverByDriverId().getUserByUserId() != null) {
	            UserEntity userEntity = entity.getDriverByDriverId().getUserByUserId();
	            orderResponse.setBikerId(entity.getDriverByDriverId().getId());
	            orderResponse.setBikerName(userEntity.getFirstName() + " " + userEntity.getLastName());
	            orderResponse.setBikerPhone(userEntity.getPhone());
	            orderResponse.setBikerProfileUrl("");
	        }

	        orderResponse.setFeedbackSubmitted(entity.getRatingByRateId() != null);

	        if (entity.getCancellationReasonByReasonId() != null) {
	            orderResponse.setCancellationReason(entity.getCancellationReasonByReasonId().getName());
	            orderResponse.setCustomerCancelled(!entity.getCancellationReasonByReasonId().isBikerComment());
	        }

	        return orderResponse;
	    }
	    
	    public static CustomerOrderResponse buildCustomerOrderResponse(OrderEntity entity)  {
	        CustomerOrderResponse orderResponse = new CustomerOrderResponse();
	        orderResponse.setId(entity.getId());
	        orderResponse.setOrderSeqId(entity.getOrderSeqId());
	        orderResponse.setExternalId(entity.getExternalId());
	        orderResponse.setOrderCreatedTime(entity.getOrderCreatedTime());

	        orderResponse.setStatusId(entity.getStatusId());
	        orderResponse.setStatus(OrderStatus.getById(entity.getStatusId()).getStatus());

	        orderResponse.setBookingType(BookingTypeEnum.values()[entity.getBookingId()].getType());
	        orderResponse.setOrderType(entity.getOrderType().name());
	        orderResponse.setPaymentType(entity.getPaymentType().name());

	        orderResponse.setReceiverName(entity.getReceiverName());
	        orderResponse.setReceiverPhone(entity.getReceiverPhone());
	        UserEntity customer = entity.getCustomerByCustomerId().getUserByUserId();
	        orderResponse.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
	        orderResponse.setCustomerPhone(String.valueOf(customer.getPhone()));

	        orderResponse.setDeliveryCharge(entity.getDeliveryCharge());
	        double extraWeightCharges = entity.getWeight() != null ? entity.getWeight() * ADDITIONAL_WEIGHT_CHARGES : 0;
	        orderResponse.setCodCharge(entity.getCodCharge());
	        orderResponse.setTotalCharge(entity.getDeliveryCharge() + entity.getCodCharge() + extraWeightCharges);
	        orderResponse.setCollectAtPickup(entity.isCollectAtPickUp());

	        orderResponse.setPickAddressId(entity.getAddressByPickupAddressId().getId());
	        orderResponse.setPickAddress(entity.getAddressByPickupAddressId().getAddress1());
	        orderResponse.setPickFlatNumber(entity.getAddressByPickupAddressId().getApartment());
	        orderResponse.setPickLandmark(entity.getAddressByPickupAddressId().getLandmark());
	        orderResponse.setPickLongitude(entity.getAddressByPickupAddressId().getLongitude());
	        orderResponse.setPickLatitude(entity.getAddressByPickupAddressId().getLatitude());

	        orderResponse.setDropAddressId(entity.getAddressByDropAddressId().getId());
	        orderResponse.setDropAddress(entity.getAddressByDropAddressId().getAddress1());
	        orderResponse.setDropFlatNumber(entity.getAddressByDropAddressId().getApartment());
	        orderResponse.setDropLandmark(entity.getAddressByDropAddressId().getLandmark());
	        orderResponse.setDropLongitude(entity.getAddressByDropAddressId().getLongitude());
	        orderResponse.setDropLatitude(entity.getAddressByDropAddressId().getLatitude());

	        orderResponse.setItem(entity.getItem());
	        orderResponse.setItemDescription(entity.getItemDescription());
	        orderResponse.setItemImage(entity.getItemImage());

	        setBikerDetails(entity, orderResponse);

	        orderResponse.setFeedbackSubmitted(entity.getRatingByRateId() != null);

	        return orderResponse;
	    }
	    
	    private static void setBikerDetails(OrderEntity entity, CustomerOrderResponse response)   {
	        if (entity.getDriverByDriverId() != null && entity.getDriverByDriverId().getUserByUserId() != null) {
	            response.setBikerId(entity.getDriverByDriverId().getId());
	            UserEntity userEntity = entity.getDriverByDriverId().getUserByUserId();
	            response.setBikerName(userEntity.getFirstName() + " " + userEntity.getLastName());
	            response.setBikerPhone(userEntity.getPhone());
	            response.setBikerProfileUrl("");
	        }
	    }
}
