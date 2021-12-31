package com.ezshipp.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.exception.ServiceExceptionCode;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.DriverIncentiveEntity;
import com.ezshipp.api.persistence.entity.DriverOrderCountEntity;
import com.ezshipp.api.persistence.entity.DriverPaymentEntity;
import com.ezshipp.api.persistence.entity.DriverPenaltyCountEntity;
import com.ezshipp.api.repository.DriverIncentiveEntityRepository;
import com.ezshipp.api.repository.DriverOrderCountEntityRepository;
import com.ezshipp.api.repository.DriverPaymentRepository;
import com.ezshipp.api.repository.DriverPenaltyCountEntityRepository;
import com.ezshipp.api.util.DateUtil;

@Service
public class AdminBikerService {

	@Autowired
	DriverEntityService driverEntityService;
	
	@Autowired
	DriverIncentiveEntityRepository driverIncentiveEntityRepository;
	
	@Autowired
	DriverOrderCountEntityRepository driverOrderCountEntityRepository;
	
	@Autowired
	DriverPenaltyCountEntityRepository driverPenaltyCountEntityRepository;
	
	@Autowired
	DriverPaymentRepository driverPaymentRepository;
	
	public void updateBikerPayments(int minusDays) throws ServiceException {
        try {
            List<DriverEntity> activeDrivers = driverEntityService.getAllActiveDrivers();
            for (DriverEntity driver : activeDrivers) {
                DriverIncentiveEntity incentiveEntity = driverIncentiveEntityRepository.findDriverIncentiveByDriverId(driver.getId());
                List<DriverOrderCountEntity> driverOrderCountEntityList = driverOrderCountEntityRepository.
                        findByDriverIdAndTrackingDate(driver.getId(), DateUtil.getTodayStartDateTime(minusDays));
                if (incentiveEntity != null && !CollectionUtils.isEmpty(driverOrderCountEntityList)) {
                    double loginHourAmount = incentiveEntity.getLoginHourAmount();
                    double sameDayAmount = incentiveEntity.getSameDayAmount();
                    int totalLoginHours = driverOrderCountEntityList.get(0).getTotalLoginHours();
                    int totalOrderCount = driverOrderCountEntityList.get(0).getTotalOrderCount();
                    double totalDeliveryAmount = totalOrderCount * sameDayAmount;
                    double totalLoginHourAmount = totalLoginHours * loginHourAmount;
                    double totalIncentives = totalLoginHourAmount + totalDeliveryAmount;

                    double penaltyAmount = getPenalties(driver.getId());
                    totalIncentives = totalIncentives - penaltyAmount;

                    DriverPaymentEntity driverPaymentEntity = new DriverPaymentEntity();
                    driverPaymentEntity.setDriverId(driver.getId());
                    driverPaymentEntity.setDriverByDriverId(driver);
                    driverPaymentEntity.setDeliveryAmount(totalDeliveryAmount);
                    driverPaymentEntity.setLoginHourAmount(totalLoginHourAmount);
                    driverPaymentEntity.setPenaltyAmount(penaltyAmount);
                    driverPaymentEntity.setTotalIncentives(totalIncentives);
                    driverPaymentEntity.setPayDate(new Date());
                    driverPaymentRepository.save(driverPaymentEntity);
                }
            }
        } catch (Exception e) {
           // log.error("exception in processing driver incentives", e);
            throw new ServiceException(ServiceExceptionCode.BIKER_PAYMENT_UPDATE_FAILED);
        }

    }
	
	private Double getPenalties(Integer driverId) {
        List<DriverPenaltyCountEntity> penaltyCountEntities = driverPenaltyCountEntityRepository.findAllByDriverIdAndPenaltyDate(driverId, DateUtil.getTodayDate());
        if (!CollectionUtils.isEmpty(penaltyCountEntities)) {
            return penaltyCountEntities.stream().mapToDouble(DriverPenaltyCountEntity::getPenaltyAmount).sum();
        }

        return new Double("0.00");
    }

}
