package com.ezshipp.api.service;

import static org.springframework.util.StringUtils.isEmpty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.enums.EmployeeType;
import com.ezshipp.api.enums.GenderEnum;
import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.exception.ServiceExceptionCode;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.BikerResponse;
import com.ezshipp.api.model.BikerTrackingRequest;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.DistanceRequest;
import com.ezshipp.api.model.MatrixDistance;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.model.RatingRequest;
import com.ezshipp.api.model.UpdateProfileRequest;
import com.ezshipp.api.persistence.entity.DriverDetailEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.DriverRatingEntity;
import com.ezshipp.api.persistence.entity.DriverTrackingEntity;
import com.ezshipp.api.persistence.entity.EmployeeEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.ShiftEntity;
import com.ezshipp.api.repository.DriverDetailsEntityRepository;
import com.ezshipp.api.repository.DriverEntityRepository;
import com.ezshipp.api.repository.DriverRatingRepository;
import com.ezshipp.api.repository.DriverTrackingEntityRepository;
import com.ezshipp.api.repository.EmployeeEntityRepository;
import com.ezshipp.api.repository.OrderEventRepository;
import com.ezshipp.api.util.BaseFilterHelper;
import com.ezshipp.api.util.BikerResponseHelper;
import com.ezshipp.api.util.DateUtil;
import com.ezshipp.api.util.OrderResponseHelper;
import com.google.maps.model.LatLng;

@Service
public class DriverEntityService extends BaseEntityService {

	@Autowired
	DriverTrackingEntityRepository driverTrackingEntityRepository;

	@Autowired
	DriverEntityRepository driverEntityRepository;

	@Autowired
	DriverDetailsEntityRepository driverDetailsEntityRepository;

	@Autowired
	EmployeeEntityRepository employeeEntityRepository;

	@Autowired
	ReferenceDataService referenceDataService;

	@Autowired
	OrderEventRepository orderEventRepository;

	@Autowired
	OrderEventEntityService orderEventEntityService;

	@Autowired
	DriverRatingRepository driverRatingRepository;

	@Autowired
	OrderEntityService orderEntityService;
	
	@Autowired
	GoogleMapService googleMapService;

	private final static String QUERY_COD_CHARGES = "select SUM(a.cod_charge) from `order` a where a.order_complete_time >= :orderCompletedStartTime and a.order_complete_time < :orderCompletedEndTime and a.status_id = :statusId and a.driver_id = :driverId";
	private final static String QUERY_DELIVERY_CHARGES = "select SUM(a.delivery_charge) from `order` a where a.order_complete_time >= :orderCompletedStartTime and a.order_complete_time < :orderCompletedEndTime and a.status_id = :statusId and a.driver_id = :driverId";

	public DriverTrackingEntity updateTracking(int driverId, BikerTrackingRequest trackingRequest)
			throws BusinessException {
		DriverTrackingEntity driverTrackingEntity = driverTrackingEntityRepository.findByDriverIdAndTrackDate(driverId,
				DateUtil.getTodayDate());
		DriverEntity driverEntity = findById(driverId);

		if (driverTrackingEntity == null) {
			DriverTrackingEntity newEntity = new DriverTrackingEntity();
			newEntity.setLastLatitude(trackingRequest.getLatitude());
			newEntity.setLastLongitude(trackingRequest.getLongitude());
			newEntity.setBatteryPercentage(0);
			newEntity.setDriverId(driverEntity.getId());
			newEntity.setDriverByDriverId(driverEntity);
			newEntity.setKms(0);
			newEntity.setOnlineMode(trackingRequest.isOnlineMode());
			newEntity.setIdleTime(0);
			newEntity.setLastUpdatedTime(new Timestamp((System.currentTimeMillis())));
			newEntity.setTrackDate(new Date(System.currentTimeMillis()));
			try {
				newEntity = driverTrackingEntityRepository.save(newEntity);
			} catch (ConstraintViolationException cve) {

				driverTrackingEntity = driverTrackingEntityRepository.findByDriverIdAndTrackDate(driverId,
						DateUtil.getTodayDate());
				if (driverTrackingEntity != null) {
					driverTrackingEntity.setLastUpdatedTime(new Timestamp((System.currentTimeMillis())));
					driverTrackingEntity.setLastLatitude(trackingRequest.getLatitude());
					driverTrackingEntity.setLastLongitude(trackingRequest.getLongitude());
					driverTrackingEntity.setOnlineMode(trackingRequest.isOnlineMode());
					driverTrackingEntityRepository.save(driverTrackingEntity);
				}
			}
			return newEntity;
		} else {
			driverTrackingEntity.setLastUpdatedTime(new Timestamp((System.currentTimeMillis())));
			driverTrackingEntity.setLastLatitude(trackingRequest.getLatitude());
			driverTrackingEntity.setLastLongitude(trackingRequest.getLongitude());
			driverTrackingEntity.setOnlineMode(trackingRequest.isOnlineMode());
			driverTrackingEntityRepository.save(driverTrackingEntity);
			return driverTrackingEntity;
		}
	}

	// @Cacheable(value="driversCache", key = "#id")
	public DriverEntity findById(Integer id) throws BusinessException {
		Optional<DriverEntity> driverEntity = driverEntityRepository.findById(id);
		if (!driverEntity.isPresent()) {
			throw new BusinessException(BusinessExceptionCode.INVALID_DRIVER_ID);
		}
		return driverEntity.get();
	}

	public BikerResponse findProfileById(Integer id) throws ServiceException {
		DriverEntity driverEntity = driverEntityRepository.findById(id).get();
		DriverDetailEntity driverDetailEntity = driverDetailsEntityRepository.findByDriverId(id);

		BikerResponse response = BikerResponseHelper.buildResponse(driverEntity, driverDetailEntity);
		response.setDeviceToken(driverEntity.getDeviceByDeviceId().getDeviceToken());

		EmployeeEntity employeeEntity = employeeEntityRepository.findByUserId(driverEntity.getUserId());
		if (employeeEntity != null) {
			response.setAadhaarNumber(employeeEntity.getAadhaarNumber());
		}

		return response;
	}

	public BikerResponse updateBikerProfile(Integer driverId, UpdateProfileRequest updateProfileRequest)
			throws BusinessException, ServiceException {
		try {
			Optional<DriverEntity> entity = driverEntityRepository.findById(driverId);
			if (entity.isPresent()) {
				DriverEntity driverEntity = entity.get();
				DriverDetailEntity driverDetailEntity = null;
				if (!isEmpty(updateProfileRequest.getActive())) {
					driverEntity.setActive(updateProfileRequest.getActive());
				}
				if (!isEmpty(updateProfileRequest.getDeviceToken())) {
					driverEntity.getDeviceByDeviceId().setDeviceToken(updateProfileRequest.getDeviceToken());
				}
				if (!isEmpty(updateProfileRequest.getFirstName())) {
					driverEntity.getUserByUserId().setFirstName(updateProfileRequest.getFirstName());
				}
				if (!isEmpty(updateProfileRequest.getEmail())) {
					driverEntity.getUserByUserId().setEmail(updateProfileRequest.getEmail());
				}
				if (!isEmpty(updateProfileRequest.getLastName())) {
					driverEntity.getUserByUserId().setLastName(updateProfileRequest.getLastName());
				}
				if ((updateProfileRequest.getPhoneNumber() != null && updateProfileRequest.getPhoneNumber() > 0)
						&& updateProfileRequest.getPhoneNumber() != driverEntity.getUserByUserId().getPhone()) {
					driverEntity.getUserByUserId().setPhone(updateProfileRequest.getPhoneNumber());
				}
				if (updateProfileRequest.getImageUrl() != driverEntity.getUserByUserId().getProfileUrl()) {
					driverEntity.getUserByUserId().setProfileUrl(updateProfileRequest.getImageUrl());
				}

				if (!StringUtils.isEmpty(updateProfileRequest.getLicenseUrl())
						|| !StringUtils.isEmpty(updateProfileRequest.getLicense())
						|| !StringUtils.isEmpty(updateProfileRequest.getAadhaarNumber())
						|| !StringUtils.isEmpty(updateProfileRequest.getAadhaarUrl())
						|| (updateProfileRequest.getShiftId() != null && updateProfileRequest.getShiftId() > 0)
						|| !StringUtils.isEmpty(updateProfileRequest.getVehicleRegn())) {
					driverDetailEntity = driverDetailsEntityRepository.findByDriverId(driverId);
					if (driverDetailEntity == null) {
						driverDetailEntity = new DriverDetailEntity();
						driverDetailEntity.setZoneByZoneId(referenceDataService.getAllZones().get(0));
						if (updateProfileRequest.getShiftId() != null && updateProfileRequest.getShiftId() > 0) {
							driverDetailEntity.setShiftByShiftId(getShiftById(updateProfileRequest.getShiftId()));
						}
						driverDetailEntity.setDriverByDriverId(driverEntity);
						driverDetailEntity.setDriverSeqId(Integer.parseInt(RandomStringUtils.randomNumeric(8)));
						driverDetailEntity.setLicense(updateProfileRequest.getLicense());
						driverDetailEntity.setLicenseUrl(updateProfileRequest.getLicenseUrl());
						driverDetailEntity.setNumberPlate(updateProfileRequest.getVehicleRegn());

						driverDetailsEntityRepository.save(driverDetailEntity);
					} else {
						if (!StringUtils.isEmpty(updateProfileRequest.getLicenseUrl())) {
							driverDetailEntity.setLicenseUrl(updateProfileRequest.getLicenseUrl());
						}
						if (!StringUtils.isEmpty(updateProfileRequest.getLicense())) {
							driverDetailEntity.setLicense(updateProfileRequest.getLicense());
						}
						if (!StringUtils.isEmpty(updateProfileRequest.getVehicleRegn())) {
							driverDetailEntity.setNumberPlate(updateProfileRequest.getVehicleRegn());
						}
						if (updateProfileRequest.getShiftId() != null && updateProfileRequest.getShiftId() > 0) {
							driverDetailEntity.setShiftByShiftId(getShiftById(updateProfileRequest.getShiftId()));
						}
						driverDetailsEntityRepository.save(driverDetailEntity);
					}
				}

				if (!StringUtils.isEmpty(updateProfileRequest.getAadhaarNumber())
						|| !StringUtils.isEmpty(updateProfileRequest.getAadhaarUrl())) {
					EmployeeEntity employeeEntity = employeeEntityRepository.findByUserId(driverEntity.getUserId());
					if (employeeEntity == null) {
						employeeEntity = new EmployeeEntity();
						employeeEntity.setUserByUserId(driverEntity.getUserByUserId());
						employeeEntity.setEmployeeType(EmployeeType.BIKER);
						employeeEntity.setEmployeeId("EMP" + RandomStringUtils.randomNumeric(5));
						employeeEntity.setGender(GenderEnum.MALE);
						employeeEntity.setAadhaarNumber(updateProfileRequest.getAadhaarNumber());
						employeeEntity.setAadhaarCardUrl(updateProfileRequest.getAadhaarUrl());
						employeeEntityRepository.save(employeeEntity);
					} else {
						if (!StringUtils.isEmpty(updateProfileRequest.getAadhaarNumber())) {
							employeeEntity.setAadhaarNumber(updateProfileRequest.getAadhaarNumber());
						} else if (!StringUtils.isEmpty(updateProfileRequest.getAadhaarUrl())) {
							employeeEntity.setAadhaarCardUrl(updateProfileRequest.getAadhaarUrl());
						}
						employeeEntityRepository.save(employeeEntity);
					}
				}

				driverEntityRepository.save(driverEntity);
				return BikerResponseHelper.buildResponse(driverEntity, driverDetailEntity);

			} else {
				throw new BusinessException(BusinessExceptionCode.BIKER_NOT_FOUND);
			}
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionCode.DRIVER_UPDATE_FAILED);
		}
	}

	private ShiftEntity getShiftById(Integer shiftId) {
		List<ShiftEntity> shiftEntities = referenceDataService.getAllShifts();
		for (ShiftEntity shiftEntity : shiftEntities) {
			if (shiftEntity.getId() == shiftId) {
				return shiftEntity;
			}
		}
		return shiftEntities.get(0);
	}

	public List<OrderResponse> getAllOrdersByDriverId(Integer driverId, Boolean isAssigned)
			throws BusinessException, ServiceException {
		validateDriverId(driverId);

		List<OrderEventEntity> results = null;
		if (isAssigned) {
			results = orderEventRepository.findByDriverIdAndStatusId(driverId, 2);
		} else {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.addAll(Arrays.asList(1, 2, 8, 12, 13, 14));
			results = orderEventRepository.findByDriverIdAndStatusIdNotIn(driverId, statusList);
		}

		List<OrderResponse> orderResponseList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(results)) {
			for (OrderEventEntity entity : results) {
				OrderResponse response = OrderResponseHelper.buildResponse(entity.getOrderByOrderId());
				response.setStatusId(entity.getStatusId());
				response.setStatus(OrderStatus.getById(response.getStatusId()).getStatus());
				orderResponseList.add(response);
			}
		}

		return orderResponseList;
	}

	private List<OrderEventEntity> findDistinctOrdersByDriverIdAndStatusId(Integer driverId, int i) {

		return orderEventRepository.findByDriverIdAndStatusId(driverId, i);
	}

	private void validateDriverId(Integer driverId) throws BusinessException {
		Optional<DriverEntity> driverEntity = driverEntityRepository.findById(driverId);

		if (driverEntity == null) {
			throw new BusinessException(BusinessExceptionCode.BIKER_NOT_FOUND);
		}
	}

	public OrderResponse getNewOrdersByDriverId(Integer driverId, Integer orderId)
			throws BusinessException, ServiceException {
		validateDriverId(driverId);
		validateOrderId(orderId);

		List<OrderEventEntity> results = orderEventEntityService.findOrderEventsByDriverIdAndOrderId(driverId, orderId);
		OrderResponse response = OrderResponseHelper.buildResponse(results.get(0).getOrderByOrderId());
		response.setStatusId(OrderStatus.ASSIGNED.getStatusId());
		response.setStatus(OrderStatus.getById(response.getStatusId()).getStatus());
		return response;
	}

	private void validateOrderId(Integer orderId) {

	}

	public void saveRating(RatingRequest ratingRequest) throws BusinessException, ServiceException {
		DriverEntity driverEntity = findById(ratingRequest.getDriverId());
		if (driverEntity == null) {
			throw new BusinessException(BusinessExceptionCode.BIKER_NOT_FOUND);
		}

		DriverRatingEntity driverRatingEntity = new DriverRatingEntity();
		driverRatingEntity.setRating(ratingRequest.getRating());
		driverRatingEntity.setCustomerId(ratingRequest.getCustomerId());
		driverRatingEntity.setOrderId(ratingRequest.getOrderId());
		driverRatingEntity.setDriverId(ratingRequest.getDriverId());
		driverRatingEntity.setCarryingBag(ratingRequest.isCarryingBag());
		driverRatingEntity.setDeliveredProperly(ratingRequest.isDeliveredProperly());
		driverRatingEntity.setWearingTShirt(ratingRequest.isWearingTShirt());
		driverRatingEntity.setRatedDate(new Timestamp(System.currentTimeMillis()));
		driverRatingEntity.setRatedBy(ratingRequest.getCustomerId());
		driverRatingEntity.setNotes(ratingRequest.getNotes());
		driverRatingEntity = driverRatingRepository.save(driverRatingEntity);

		OrderEntity orderEntity = orderEntityService.findOrderEntityById(ratingRequest.getOrderId());
		if (orderEntity != null) {
			orderEntity.setRatingId(driverRatingEntity.getId());
			orderEntity.setRatingByRateId(driverRatingEntity);
			orderEntityService.persist(orderEntity);
		}
	}

	public PagingResponse getAllBikersForAdmin(BaseFilter baseFilter) throws ServiceException {
		baseFilter.setSortFields(new String[] { "modifiedTime" });
		Page<DriverEntity> driverEntities = driverEntityRepository.findAll(createPageRequest(baseFilter));
		List<DriverEntity> driverEntityList = driverEntities.getContent();

		List<BikerResponse> bikerResponseList = new ArrayList<>();
		for (DriverEntity entity : driverEntityList) {
			DriverDetailEntity detailEntity = driverDetailsEntityRepository.findByDriverId(entity.getId());
			bikerResponseList.add(BikerResponseHelper.buildResponse(entity, detailEntity));
		}

		PagingResponse pagingResponse = buildPagingResponse(driverEntities, baseFilter);
		pagingResponse.setMessage("success driver response");
		pagingResponse.setData(bikerResponseList);
		return pagingResponse;
	}

	protected PagingResponse buildPagingResponse(Page<?> orderEntities, BaseFilter baseFilter) {
		PagingResponse pagingResponse = new PagingResponse();
		int totalPages = orderEntities.getTotalPages();
		pagingResponse.setTotalPageCount(totalPages);
		pagingResponse.setTotalCount(orderEntities.getTotalElements());
		pagingResponse.setPageNumber(baseFilter.getPageNumber());
		pagingResponse.setNextPageNumber(
				orderEntities.isLast() ? baseFilter.getPageNumber() : baseFilter.getPageNumber() + 1);
		pagingResponse.setPrevPageNumber(
				orderEntities.isFirst() ? baseFilter.getPageNumber() : baseFilter.getPageNumber() - 1);
		pagingResponse.setPageSize(baseFilter.getPageSize());
		pagingResponse.setLastPage(orderEntities.isLast());
		pagingResponse.setFirstPage(orderEntities.isFirst());

//		        pagingResponse.setCode(OK.value());
//		        pagingResponse.setStatus(OK.getReasonPhrase());
		pagingResponse.setMessage("success order response");
		return pagingResponse;
	}

	public PagingResponse getBikerRecentOrders(Integer driverId, BaseFilter baseFilter)
			throws BusinessException, ServiceException {
		baseFilter.setStartDate(DateUtil.getDate("2018-12-01", "yyyy-MM-dd"));
		baseFilter.setEndDate(DateUtil.getDate("2019-02-10", "yyyy-MM-dd"));
		return getAllCompletedOrders(driverId, baseFilter);
	}

	public PagingResponse getAllCompletedOrders(Integer driverId, BaseFilter baseFilter)
			throws BusinessException, ServiceException {
		validateDriverId(driverId);
		BaseFilterHelper.setDates(baseFilter);

		PagingResponse pagingResponse = orderEventEntityService.findOrdersByDriverIdAndStatusId(driverId,
				OrderStatus.DELIVERED.getStatusId(), baseFilter);

		double clientCODDues = orderEntityService.findDues(QUERY_COD_CHARGES, driverId,
				OrderStatus.DELIVERED.getStatusId(), baseFilter.getStartDate(), baseFilter.getEndDate());
		double deliveryDues = orderEntityService.findDues(QUERY_DELIVERY_CHARGES, driverId,
				OrderStatus.DELIVERED.getStatusId(), baseFilter.getStartDate(), baseFilter.getEndDate());
		pagingResponse.setDeliveryDues(deliveryDues);
		pagingResponse.setClientCODDues(clientCODDues);

		return pagingResponse;
	}
	
	
	
	public PagingResponse getAcceptedAndinProgressOrders(Integer driverId, int pageSize, int pageNumber)
			throws BusinessException, ServiceException {
		validateDriverId(driverId);
	    List<Integer> statuses = new ArrayList<>();
        statuses.add(OrderStatus.ACCEPTED.getStatusId());
        statuses.add(OrderStatus.AT_DELIVERY.getStatusId());
        statuses.add(OrderStatus.AT_PICKUP.getStatusId());
        statuses.add(OrderStatus.PICKED_UP.getStatusId());
        statuses.add(OrderStatus.SCANNED.getStatusId());
        Page<OrderEventEntity> orderEntitiesPage = orderEventRepository.findOrdersByDriverIdAndStatusIdIn(driverId,
				statuses, createPageRequestDriver(pageNumber, pageSize));
        
        List<OrderEventEntity> orderEventEntityList = orderEntitiesPage.getContent();
        List<OrderEntity> orderEntities = orderEventEntityList.stream()
                .map(OrderEventEntity::getOrderByOrderId).collect(Collectors.toList());

        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (OrderEntity entity : orderEntities) {
            orderResponseList.add(OrderResponseHelper.buildResponse(entity));
        }
    
	        PagingResponse pagingResponse = buildPagingResponse(orderEntitiesPage, new BaseFilter(pageNumber, pageSize));
	        pagingResponse.setMessage("success customer response");
	        pagingResponse.setData(orderResponseList);
	        return pagingResponse;

		
	}
	public PagingResponse getAllCompletedOrdersByStatus(Integer driverId, BaseFilter baseFilter,Integer statusId)
			throws BusinessException, ServiceException {
		validateDriverId(driverId);
		BaseFilterHelper.setDates(baseFilter);

		PagingResponse pagingResponse = orderEventEntityService.findOrdersByDriverIdAndStatusId(driverId,
				statusId, baseFilter);

		double clientCODDues = orderEntityService.findDues(QUERY_COD_CHARGES, driverId,
				statusId, baseFilter.getStartDate(), baseFilter.getEndDate());
		double deliveryDues = orderEntityService.findDues(QUERY_DELIVERY_CHARGES, driverId,
				statusId, baseFilter.getStartDate(), baseFilter.getEndDate());
		pagingResponse.setDeliveryDues(deliveryDues);
		pagingResponse.setClientCODDues(clientCODDues);

		return pagingResponse;
	}
	

	public List<DriverEntity> getAllActiveDrivers() {
		return driverEntityRepository.findByActiveIsTrue();
	}

	public List<MatrixDistance> getDistance(DistanceRequest distanceRequest) throws BusinessException, ServiceException
			{
		OrderResponse orderResponse = orderEntityService.findById(distanceRequest.getOrderId());
		LatLng driverLocation = new LatLng(distanceRequest.getLatitude(), distanceRequest.getLongitude());
		LatLng pickupLocation = new LatLng(orderResponse.getPickLatitude(), orderResponse.getPickLongitude());
		return googleMapService.calculateDistance(new LatLng[] { driverLocation }, new LatLng[] { pickupLocation });
	}
}
