package com.ezshipp.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.BikerCODResponse;
import com.ezshipp.api.model.BikerDeliveryStatsResponse;
import com.ezshipp.api.model.BikerEarningsDataResponse;
import com.ezshipp.api.model.BikerPaymentResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.persistence.entity.DriverDueEntity;
import com.ezshipp.api.persistence.entity.DriverIncentiveEntity;
import com.ezshipp.api.persistence.entity.DriverOrderCountEntity;
import com.ezshipp.api.persistence.entity.DriverPaymentEntity;
import com.ezshipp.api.persistence.entity.DriverPenaltyCountEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.DriverDueEntityRepository;
import com.ezshipp.api.repository.DriverIncentiveEntityRepository;
import com.ezshipp.api.repository.DriverOrderCountEntityRepository;
import com.ezshipp.api.repository.DriverPaymentRepository;
import com.ezshipp.api.repository.DriverPenaltyCountEntityRepository;
import com.ezshipp.api.util.BikerDetailResponseHelper;
import com.ezshipp.api.util.DateUtil;

@Service
public class DriverDetailEntityService extends BaseEntityService {

	@Autowired 
	DriverDueEntityRepository driverDueEntityRepository;
	
	@Autowired
	DriverOrderCountEntityRepository driverOrderCountEntityRepository;
	
	@Autowired
	DriverPaymentRepository driverPaymentRepository;
	
	@Autowired
	DriverIncentiveEntityRepository driverIncentiveEntityRepository;
    
	@Autowired
	DriverPenaltyCountEntityRepository driverPenaltyCountEntityRepository;
	
	  public PagingResponse getAllBikersCOD(BaseFilter baseFilter) throws BusinessException {
	        Page<DriverDueEntity> driverDueEntities = null;
	        baseFilter.setSortFields(new String[]{"modifiedTime"});
	        if (baseFilter.getStartDate() == null || baseFilter.getEndDate() == null) {
	            baseFilter.setStartDate(DateUtil.getTodayStartDateTime(0));
	            baseFilter.setEndDate(DateUtil.getTodayEndDateTime(0));
	            baseFilter.setSortFields(new String[]{"modifiedTime"});
	            driverDueEntities = driverDueEntityRepository.findAllByRecordedDate(baseFilter.getStartDate(), createPageRequest(baseFilter));
	        } else  {
	            driverDueEntities = driverDueEntityRepository.findAllByRecordedDateBetween(baseFilter.getStartDate(),baseFilter.getEndDate(), createPageRequest(baseFilter));
	        }

	        // group by driver Id
	        Map<Integer, List<DriverDueEntity>> driversMap = driverDueEntities.stream().collect(Collectors.groupingBy(DriverDueEntity::getDriverId));

	        // List<DriverDueEntity> driverPaymentEntityList = driverDueEntities.getContent();
	        List<BikerCODResponse> responseList = new ArrayList<>();
	        for (Integer driverId : driversMap.keySet()) {
	            List<DriverDueEntity> codEntries = driversMap.get(driverId);
	            BikerCODResponse response = new BikerCODResponse();
	            response.setBikerId(driverId);
	            UserEntity userEntity = codEntries.get(0).getDriverByDriverId().getUserByUserId();
	            response.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
	            response.setDeliveryAmount(codEntries.stream().mapToDouble(DriverDueEntity::getDeliveryAmount).sum());
	            response.setCodAmount(codEntries.stream().mapToDouble(DriverDueEntity::getCodAmount).sum());
	            response.setRecordedDate(codEntries.get(0).getRecordedDate());
	            responseList.add(response);
	        }
	        
	        PagingResponse pagingResponse = buildPagingResponse(driverDueEntities, baseFilter);
	        pagingResponse.setMessage("success Biker COD response");
	        pagingResponse.setData(responseList);
	        return pagingResponse;
	    }
	  
	  
	  public PagingResponse getAllBikersOrderStats(BaseFilter baseFilter) throws BusinessException {
	        Page<DriverOrderCountEntity> driverOrderCountEntities = null;
	        baseFilter.setSortFields(new String[]{"modifiedTime"});
	        if (baseFilter.getStartDate() == null && baseFilter.getEndDate() == null) {
	            baseFilter.setStartDate(DateUtil.getTodayStartDateTime(0));
	            baseFilter.setEndDate(DateUtil.getTodayEndDateTime(0));
	            driverOrderCountEntities = driverOrderCountEntityRepository.findByTrackingDate(baseFilter.getStartDate(), createPageRequest(baseFilter));
	        } else  {
	            driverOrderCountEntities = driverOrderCountEntityRepository.findByTrackingDateBetween(baseFilter.getStartDate(), baseFilter.getEndDate(), createPageRequest(baseFilter));
	        }

	        List<DriverOrderCountEntity> driverOrderCountEntityList = driverOrderCountEntities.getContent();
	        List<BikerDeliveryStatsResponse> statsResponses = new ArrayList<>();
	        for (DriverOrderCountEntity entity : driverOrderCountEntityList) {
	            BikerDeliveryStatsResponse response = new BikerDeliveryStatsResponse();
	            response.setBikerId(entity.getDriverId());
	            UserEntity userEntity = entity.getDriverByDriverId().getUserByUserId();
	            response.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
	            response.setPickedCount(entity.getPickedCount());
	            response.setDeliveredCount(entity.getDeliveredCount());
	            response.setLoginHourCount(entity.getTotalLoginHours());
	            response.setTotalCount(entity.getTotalOrderCount());
	            response.setExchangeCount(entity.getExchangeCount());
	            response.setExtraHourCount(entity.getExtraHour());
	            response.setTrackingDate(entity.getTrackingDate());
	            statsResponses.add(response);
	        }

	        PagingResponse pagingResponse = buildPagingResponse(driverOrderCountEntities, baseFilter);
	        pagingResponse.setMessage("success Biker Delivery Order response");
	        pagingResponse.setData(statsResponses);
	        return pagingResponse;
	    }
	  
	  public PagingResponse getAllBikersPayments(BaseFilter baseFilter) throws BusinessException, ServiceException {
	        baseFilter.setSortFields(new String[]{"id"});
	        Page<DriverPaymentEntity> driverPaymentEntities = driverPaymentRepository.findAll(createPageRequest(baseFilter));
	        List<DriverPaymentEntity> driverPaymentEntityList = driverPaymentEntities.getContent();

	        List<BikerPaymentResponse> bikerPaymentResponseList = new ArrayList<>();
	        for (DriverPaymentEntity paymentEntity : driverPaymentEntities) {
	            bikerPaymentResponseList.add(BikerDetailResponseHelper.
	                    buildPaymentResponse(paymentEntity));

	        }

	        PagingResponse pagingResponse = buildPagingResponse(driverPaymentEntities, baseFilter);
	        pagingResponse.setMessage("success biker payment details response");
	        pagingResponse.setData(bikerPaymentResponseList);
	        return pagingResponse;
	    }
	  
	  public PagingResponse getBikerRecentCOD(Integer driverId, BaseFilter baseFilter) throws BusinessException, ServiceException {
	        if (baseFilter.getStartDate() == null || baseFilter.getEndDate() == null) {
	            baseFilter.setStartDate(DateUtil.getTodayStartDateTime(7));
	            baseFilter.setEndDate(DateUtil.getTodayEndDateTime(0));
	        }

	        baseFilter.setSortFields(new String[]{"modifiedTime"});
	        Page<DriverDueEntity> entities = driverDueEntityRepository.findAllByDriverIdAndRecordedDateBetween(driverId, baseFilter.getStartDate(), baseFilter.getEndDate(), createPageRequest(baseFilter));
	        PagingResponse pagingResponse = buildPagingResponse(entities, baseFilter);
	        List<BikerCODResponse> dueResponseList = new ArrayList<>();
	        for (DriverDueEntity entity : entities) {
	            BikerCODResponse bikerCODResponse = new BikerCODResponse();
	            bikerCODResponse.setCodAmount(entity.getCodAmount());
	            bikerCODResponse.setDeliveryAmount(entity.getDeliveryAmount());
	            bikerCODResponse.setRecordedDate(entity.getRecordedDate());
	            dueResponseList.add(bikerCODResponse);
	        }
	        pagingResponse.setData(dueResponseList);
	        return pagingResponse;
	    }
	  
	  public PagingResponse getBikerRecentPayments(Integer driverId, BaseFilter baseFilter) throws BusinessException, ServiceException {
	        baseFilter.setStartDate(DateUtil.getDate("2018-12-01", "yyyy-MM-dd"));
	        baseFilter.setEndDate(DateUtil.getDate("2019-02-10", "yyyy-MM-dd"));
	        return getBikersEarningsByDriverId(driverId);
	    }
	  
	  public PagingResponse getBikersEarningsByDriverId(Integer driverId) throws BusinessException, ServiceException {
	        DriverIncentiveEntity driverIncentiveEntity = driverIncentiveEntityRepository
	                .findDriverIncentiveByDriverId(driverId);

	        List<BikerEarningsDataResponse> bikerEarningsDataResponseList = new ArrayList<>();
	        double driverPenaltyCountAmount = 0;
	        if (driverIncentiveEntity != null) {
	            List<DriverPenaltyCountEntity> driverPenaltyCountEntityList = driverPenaltyCountEntityRepository
	                    .findAllDriverPenaltyCountByDriverId(driverIncentiveEntity.getDriverId());

	            for (DriverPenaltyCountEntity driverPenaltyCountEntity : driverPenaltyCountEntityList) {
	                driverPenaltyCountAmount = driverPenaltyCountAmount + driverPenaltyCountEntity.getPenaltyAmount();
	            }
	        }
	        bikerEarningsDataResponseList
	                .add(BikerDetailResponseHelper.buildIncentiveResponse(driverIncentiveEntity, driverPenaltyCountAmount));

	        PagingResponse pagingResponse = new PagingResponse();
//	        pagingResponse.setCode(OK.value());
//	        pagingResponse.setStatus(OK.getReasonPhrase());
	        pagingResponse.setMessage("success biker delivery details response");
	        pagingResponse.setData(bikerEarningsDataResponseList);
	        return pagingResponse;
	    }
}
