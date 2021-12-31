package com.ezshipp.api.helper;

import org.springframework.stereotype.Service;

import com.ezshipp.api.model.CustomerResponse;
import com.ezshipp.api.persistence.entity.CustomerEntity;

public class CustomerResponseHelper {

	 public static CustomerResponse buildResponse(CustomerEntity entity)  {
	        CustomerResponse customerResponse = new CustomerResponse();
	        customerResponse.setCustomerAdded(entity.getSignUpDate());
	        customerResponse.setCustomerId(entity.getCustomerId());
	        customerResponse.setId(entity.getId());
	        customerResponse.setName(entity.getUserByUserId().getFirstName() + " " + entity.getUserByUserId().getLastName());
	        customerResponse.setPhone(String.valueOf(entity.getUserByUserId().getPhone()));
	        customerResponse.setWebSignup(entity.isWebSignUp());
	        customerResponse.setPremium(entity.isPremium());
	        customerResponse.setReferralCode(entity.getReferralCode());
	        if (entity.getDeviceByDeviceId() != null) {
	            customerResponse.setDeviceMake(entity.getDeviceByDeviceId().getDeviceMake());
	            customerResponse.setDeviceModel(entity.getDeviceByDeviceId().getDeviceModel());
	            customerResponse.setDeviceType(entity.getDeviceByDeviceId().getDeviceType());
	            customerResponse.setOs(entity.getDeviceByDeviceId().getOs());
	        }
	        return customerResponse;
	    }
}
