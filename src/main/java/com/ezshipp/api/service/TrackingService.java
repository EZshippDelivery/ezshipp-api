package com.ezshipp.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BikerTracking;
import com.ezshipp.api.model.MobileTrackingResponse;
import com.ezshipp.api.model.TrackingResponse;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.DriverTrackingEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.OrderEventEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.AddressRepository;
import com.ezshipp.api.repository.DriverTrackingEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.util.DateUtil;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TrackingService {

    private static final String QUERY_LATEST_DRIVER_BY_ORDERS_ID = "SELECT distinct(oe.order_id), oe.id, oe.last_updated_time, oe.status_id, oe.driver_id, \n" +
            "oe.created_by, oe.created_time, oe.modified_by, oe.modified_time, oe.latitude, oe.longitude \n" +
            "FROM   order_event oe \n" +
            "WHERE  oe.order_id = :orderId \n" +
            "AND    oe.status_id NOT IN (12, 13, 14, 15) \n" +
            "AND    oe.status_id = (select max(status_id) from order_event where order_id = :orderId) ";

    final private OrderEntityRepository orderEntityRepository;
    final private AddressRepository addressRepository;
    final private DriverTrackingEntityRepository driverTrackingEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

  
    public TrackingService(OrderEntityRepository orderEntityRepository, AddressRepository addressRepository,
                           DriverTrackingEntityRepository driverTrackingEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.addressRepository = addressRepository;
        this.driverTrackingEntityRepository = driverTrackingEntityRepository;
    }

    private List<OrderEventEntity> findDistinctOrdersByOrderId(Integer orderId) throws ServiceException {
        try {
            Query nativeQuery = entityManager.createNativeQuery(QUERY_LATEST_DRIVER_BY_ORDERS_ID, OrderEventEntity.class);
            nativeQuery.setParameter("orderId", orderId);
            return nativeQuery.getResultList();
        } catch (Exception re)  {
            log.error("exception in native query in finding distinct orders", re);
        }
        return null;
    }

    public TrackingResponse getOrderTracking(Integer orderId) throws BusinessException, ServiceException   {
        Optional<OrderEntity> optionalOrderEntity = orderEntityRepository.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity order = optionalOrderEntity.get();
            AddressEntity dropAddress = addressRepository.findById(order.getDropAddressId()).get();
            AddressEntity pickAddress = addressRepository.findById(order.getPickupAddressId()).get();
            List<OrderEventEntity> orderEventEntities = findDistinctOrdersByOrderId(orderId);
            if (!CollectionUtils.isEmpty(orderEventEntities)) {
                DriverTrackingEntity driverTrackingEntity = driverTrackingEntityRepository.findByDriverIdAndTrackDate(orderEventEntities.get(0).getDriverId(), DateUtil.getTodayDate());
                if (driverTrackingEntity == null) {
                    throw new BusinessException(BusinessExceptionCode.TRACKING_NOT_AVAILABLE);
                }
                TrackingResponse trackingResponse = new TrackingResponse();
                trackingResponse.setBikerLatitude(driverTrackingEntity.getLastLatitude());
                trackingResponse.setBikerLongitude(driverTrackingEntity.getLastLongitude());
                trackingResponse.setDropLatitude(dropAddress.getLatitude());
                trackingResponse.setDropLongitude(dropAddress.getLongitude());
                trackingResponse.setPickLatitude(pickAddress.getLatitude());
                trackingResponse.setPickLongitude(pickAddress.getLongitude());
                trackingResponse.setOrderId(orderId);
                trackingResponse.setDriverId(orderEventEntities.get(0).getDriverId());
                return trackingResponse;
            }
        }
        return null;
    }

    public MobileTrackingResponse getMobileOrderTracking(String orderSeqId) throws BusinessException, ServiceException   {
        List<OrderEntity> orderEntities = orderEntityRepository.findByOrderSeqId(orderSeqId);
        if (!CollectionUtils.isEmpty(orderEntities)) {
            OrderEntity order = orderEntities.get(0);
            AddressEntity dropAddress = addressRepository.findById(order.getDropAddressId()).get();
            AddressEntity pickAddress = addressRepository.findById(order.getPickupAddressId()).get();
            List<OrderEventEntity> orderEventEntities = findDistinctOrdersByOrderId(order.getId());
            if (!CollectionUtils.isEmpty(orderEventEntities)) {
                DriverTrackingEntity driverTrackingEntity = driverTrackingEntityRepository.findByDriverIdAndTrackDate(orderEventEntities.get(0).getDriverId(), DateUtil.getTodayDate());
                if (driverTrackingEntity == null) {
                    throw new BusinessException(BusinessExceptionCode.TRACKING_NOT_AVAILABLE);
                }
                MobileTrackingResponse trackingResponse = new MobileTrackingResponse();
                trackingResponse.setOrderSeqId(orderSeqId);
                trackingResponse.setReceiverName(order.getReceiverName());
                trackingResponse.setReceiverPhone(order.getReceiverPhone());
                trackingResponse.setItemName(order.getItem());
                trackingResponse.setOrderId(order.getId());

                trackingResponse.setPickAddress(pickAddress.getAddress1());
                if (StringUtils.isEmpty(pickAddress.getAddress2())) {
                    trackingResponse.setPickAddress(pickAddress.getAddress1() + "," + pickAddress.getAddress2());
                }
                trackingResponse.setDropAddress(dropAddress.getAddress1());
                if (StringUtils.isEmpty(dropAddress.getAddress2())) {
                    trackingResponse.setDropAddress(dropAddress.getAddress1() + "," + dropAddress.getAddress2());
                }

                trackingResponse.setBikerLatitude(driverTrackingEntity.getLastLatitude());
                trackingResponse.setBikerLongitude(driverTrackingEntity.getLastLongitude());
                trackingResponse.setDropLatitude(dropAddress.getLatitude());
                trackingResponse.setDropLongitude(dropAddress.getLongitude());
                trackingResponse.setPickLatitude(pickAddress.getLatitude());
                trackingResponse.setPickLongitude(pickAddress.getLongitude());

                trackingResponse.setDriverId(orderEventEntities.get(0).getDriverId());
                UserEntity userEntity = orderEventEntities.get(0).getDriverByDriverId().getUserByUserId();
                String name = userEntity.getFirstName() + " " + userEntity.getLastName();
                trackingResponse.setDriverName(name);
                trackingResponse.setDriverPhone(String.valueOf(userEntity.getPhone()));
                trackingResponse.setProfilePic(userEntity.getProfileUrl());
                return trackingResponse;
            }
        }

        return null;
    }

    @Transactional
    public DriverTrackingEntity getTrackingByBikerIdAndTrackingDate(Integer driverId, Date trackingDate) throws ServiceException {
        return driverTrackingEntityRepository.findByDriverIdAndTrackDate(driverId, trackingDate);
    }


    @Transactional
    public List<BikerTracking> getAllBikerTracking()   {
        List<DriverTrackingEntity> driverTrackingEntities = driverTrackingEntityRepository.findAllByTrackDate(new Date());
        List<BikerTracking> trackingResponses = new ArrayList<>();
        for (DriverTrackingEntity trackingEntity : driverTrackingEntities) {
            BikerTracking bikerTracking = new BikerTracking();
            bikerTracking.setLastLatitude(trackingEntity.getLastLatitude());
            bikerTracking.setLastLongitude(trackingEntity.getLastLongitude());
            UserEntity userEntity = trackingEntity.getDriverByDriverId().getUserByUserId();
            bikerTracking.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
            bikerTracking.setBatteryPerc(trackingEntity.getBatteryPercentage());
            bikerTracking.setDriverId(trackingEntity.getDriverId());
            bikerTracking.setKms(trackingEntity.getKms());
            bikerTracking.setIdleTime(trackingEntity.getIdleTime());
            bikerTracking.setTrackDate(trackingEntity.getTrackDate());
            bikerTracking.setLastUpdatedTime(trackingEntity.getLastUpdatedTime());
            bikerTracking.setOnline(trackingEntity.isOnlineMode());
            trackingResponses.add(bikerTracking);
        }
        return trackingResponses;
    }

    @Transactional
    public void updateTracking(DriverTrackingEntity entity, DriverEntity driverEntity, boolean onlineMode, int batteryPerc, LatLng location, double distance) throws ServiceException {
        entity.setOnlineMode(onlineMode);
        entity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
        entity.setKms(distance);
        entity.setLastLongitude(location.lng);
        entity.setLastLatitude(location.lat);
        entity.setBatteryPercentage(batteryPerc);
        entity.setDriverByDriverId(driverEntity);
        entity.setDriverId(driverEntity.getId());
        entity.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
        entity.setCreatedBy(driverEntity.getId());
        entity.setModifiedBy(driverEntity.getId());
        entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        entity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
        driverTrackingEntityRepository.save(entity);
    }

}
