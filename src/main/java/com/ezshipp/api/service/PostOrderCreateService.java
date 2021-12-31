package com.ezshipp.api.service;


import static com.ezshipp.api.enums.BookingTypeEnum.FOURHOURS;
import static com.ezshipp.api.enums.BookingTypeEnum.INSTANT;
import static com.ezshipp.api.enums.BookingTypeEnum.SAMEDAY;
import static com.ezshipp.api.repository.ApplicationGlobalConstants.CUT_OFF_MESSAGE;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.model.SMSRequest;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.persistence.entity.ZoneEntity;
import com.ezshipp.api.util.DateUtil;
@Service
public class PostOrderCreateService {

	@Autowired
	BikerAssignmentService bikerAssignmentService;
	
//	@Autowired
//	SMSService smsService;
	
	  public void process(OrderEntity orderEntity) {
	        try {
	            ZoneEntity pickZone = orderEntity.getZoneByPickupZoneId();
	            ZoneEntity dropZone = orderEntity.getZoneByDropZoneId();

	            //update the Order with pick and drop zone info
	            orderEntity.setPickupZoneId(pickZone.getId());
	            orderEntity.setDropZoneId(dropZone.getId());
	            boolean isAssigned = false;
	            if (orderEntity.getCustomerByCustomerId().isPremium())  {
	                isAssigned = bikerAssignmentService.assignCustomerBikers(orderEntity);
	            }

	            if (!isAssigned) {
	            	bikerAssignmentService.assignBiker(orderEntity);
	            }

	            // send SMS to customers if order placed after cut off time
	            if (isOrderCreatedAfterCutOff(orderEntity))    {
	                SMSRequest smsRequest = new SMSRequest();
	                smsRequest.setMessage(CUT_OFF_MESSAGE);
	                UserEntity userEntity = orderEntity.getCustomerByCustomerId().getUserByUserId();
	                smsRequest.setCustomerName(userEntity.getFirstName() + " " + userEntity.getLastName());
	                smsRequest.setPhone(String.valueOf(userEntity.getPhone()));
	               // smsService.sendSMS(smsRequest);
	            }
	        } catch (ServiceException se)   {
	        //    log.error("se - exception occurred while post create order activity", se);

	        } catch (Exception e)    {
	        //    log.error("e - exception occurred while post create order activity", e);
	        }
	    }
	  
	    private boolean isOrderCreatedAfterCutOff(OrderEntity orderEntity)  {
	        if (!orderEntity.getCustomerByCustomerId().isPremium()) {
	            if (orderEntity.getBookingId() == FOURHOURS.ordinal() && orderEntity.getOrderCreatedTime().after(DateUtil.getFiveCutOffTime())) {
	                return true;
	            } else if (orderEntity.getBookingId() == SAMEDAY.ordinal() && orderEntity.getOrderCreatedTime().after(DateUtil.getFourCutOffTime())) {
	                return true;
	            } else if (orderEntity.getBookingId() == INSTANT.ordinal() && orderEntity.getOrderCreatedTime().after(DateUtil.getSixCutOffTime())) {
	                return true;
	            }
	        }

	        return false;
	    }
}
