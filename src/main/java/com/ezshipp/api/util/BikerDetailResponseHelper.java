package com.ezshipp.api.util;

import com.ezshipp.api.model.BikerDetailsResponse;
import com.ezshipp.api.model.BikerEarningsDataResponse;
import com.ezshipp.api.model.BikerPaymentResponse;
import com.ezshipp.api.persistence.entity.DriverDetailEntity;
import com.ezshipp.api.persistence.entity.DriverIncentiveEntity;
import com.ezshipp.api.persistence.entity.DriverPaymentEntity;

public class BikerDetailResponseHelper {

	public static BikerDetailsResponse buildResponse(DriverDetailEntity entity) {
		BikerDetailsResponse bikerDetailsResponse = new BikerDetailsResponse();
		bikerDetailsResponse.setNumberPlate(entity.getNumberPlate());
		bikerDetailsResponse.setName(entity.getDriverByDriverId().getUserByUserId().getFirstName() + " "
				+ entity.getDriverByDriverId().getUserByUserId().getLastName());
		bikerDetailsResponse.setPhone(entity.getDriverByDriverId().getUserByUserId().getPhone());
		bikerDetailsResponse.setLicenseUrl(entity.getLicenseUrl());
		bikerDetailsResponse.setShift(entity.getShiftByShiftId().getName());
		bikerDetailsResponse.setZone(entity.getZoneByZoneId().getName());
		return bikerDetailsResponse;
	}

	public static BikerEarningsDataResponse buildIncentiveResponse(DriverIncentiveEntity driverIncentiveEntity,
			double driverPenaltyCountAmount) {
		BikerEarningsDataResponse bikerEarningsDataResponse = new BikerEarningsDataResponse();
		if (driverIncentiveEntity != null) {
			bikerEarningsDataResponse.setBasePetrol(driverIncentiveEntity.getBasePetrol());
			bikerEarningsDataResponse.setBaseSalary(driverIncentiveEntity.getBaseSalary());
			bikerEarningsDataResponse.setExtraFourHourAmount(driverIncentiveEntity.getExtraFourHourAmount());
			bikerEarningsDataResponse.setExtraHourAmount(driverIncentiveEntity.getExtraHourAmount());
			bikerEarningsDataResponse.setExtraInstantAmount(driverIncentiveEntity.getExtraInstantAmount());
			bikerEarningsDataResponse.setExtraKmAmount(driverIncentiveEntity.getExtraKmAmount());
			bikerEarningsDataResponse.setMinOrderCount(driverIncentiveEntity.getMinOrderCount());
			bikerEarningsDataResponse.setMaxKms(driverIncentiveEntity.getMaxKms());
			bikerEarningsDataResponse.setPenaltyAmount(driverPenaltyCountAmount);
			bikerEarningsDataResponse.setRecordedDate(driverIncentiveEntity.getRecordedDate());
			bikerEarningsDataResponse.setSameDayAmount(driverIncentiveEntity.getSameDayAmount());
			bikerEarningsDataResponse.setTotalAmount(driverIncentiveEntity.getBasePetrol()
					+ driverIncentiveEntity.getBaseSalary() + driverIncentiveEntity.getExtraFourHourAmount()
					+ driverIncentiveEntity.getExtraHourAmount() + driverIncentiveEntity.getExtraInstantAmount()
					+ driverIncentiveEntity.getExtraKmAmount() + driverPenaltyCountAmount
					+ driverIncentiveEntity.getSameDayAmount());
		}
		return bikerEarningsDataResponse;
	}

	public static BikerPaymentResponse buildPaymentResponse(DriverPaymentEntity entity) {
		BikerPaymentResponse response = new BikerPaymentResponse();
		response.setBikerId(entity.getDriverId());
		response.setName(entity.getDriverByDriverId().getUserByUserId().getFirstName() + " "
				+ entity.getDriverByDriverId().getUserByUserId().getLastName());
		response.setDeliveryAmount(entity.getDeliveryAmount());
		response.setPenaltyAmount(entity.getPenaltyAmount());
		response.setLoginHourAmount(entity.getLoginHourAmount());
		response.setTotalIncentives(entity.getTotalIncentives());
		response.setPayDate(entity.getPayDate());
		return response;
	}
}
