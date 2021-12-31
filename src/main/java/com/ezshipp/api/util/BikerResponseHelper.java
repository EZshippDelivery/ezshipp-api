package com.ezshipp.api.util;

import com.ezshipp.api.model.BikerResponse;
import com.ezshipp.api.persistence.entity.DriverDetailEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;

public class BikerResponseHelper {

	  public static BikerResponse buildResponse(DriverEntity entity)  {
	        BikerResponse bikerResponse = new BikerResponse();
	        bikerResponse.setBikerId(entity.getId());
	        bikerResponse.setDeviceToken(entity.getDeviceByDeviceId().getDeviceToken());
	        bikerResponse.setDeviceType(entity.getDeviceByDeviceId().getDeviceType());
	        bikerResponse.setEmail(entity.getUserByUserId().getEmail());
	        bikerResponse.setName(entity.getUserByUserId().getFirstName()+" "+entity.getUserByUserId().getLastName());
	        bikerResponse.setPhone(entity.getUserByUserId().getPhone());
	        bikerResponse.setActive(entity.isActive());
	        bikerResponse.setProfileUrl(entity.getUserByUserId().getProfileUrl());
	        return bikerResponse;
	    }

	    public static BikerResponse buildResponse(DriverEntity entity, DriverDetailEntity detailEntity) {
	        BikerResponse bikerResponse = buildResponse(entity);
	        bikerResponse.setDeviceToken("");
	        if (detailEntity != null) {
	            bikerResponse.setActive(entity.isActive());
	            bikerResponse.setNumberPlate(detailEntity.getNumberPlate());
	            bikerResponse.setLicenseNumber(detailEntity.getLicense());
	            bikerResponse.setShift(detailEntity.getShiftByShiftId().getName());
	            bikerResponse.setLicenseUrl(detailEntity.getLicenseUrl());
	        }
	        return bikerResponse;
	    }
}
