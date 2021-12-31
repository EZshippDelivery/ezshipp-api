package com.ezshipp.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverTrackingEntity;

public interface DriverTrackingEntityRepository extends JpaRepository<DriverTrackingEntity, Integer>{

	DriverTrackingEntity findByDriverIdAndTrackDate(Integer bikerId, Date todayDate);

	List<DriverTrackingEntity> findAllByTrackDate(Date date);

}
