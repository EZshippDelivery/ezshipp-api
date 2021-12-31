package com.ezshipp.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverOrderCountEntity;

public interface DriverOrderCountEntityRepository extends JpaRepository<DriverOrderCountEntity, Integer>{

	Page<DriverOrderCountEntity> findByTrackingDate(Date startDate, Pageable createPageRequest);

	Page<DriverOrderCountEntity> findByTrackingDateBetween(Date startDate, Date endDate, Pageable createPageRequest);

	List<DriverOrderCountEntity> findByDriverIdAndTrackingDate(Integer id, Date todayStartDateTime);

}
