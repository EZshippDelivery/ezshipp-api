package com.ezshipp.api.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ezshipp.api.persistence.entity.DriverDueEntity;

public interface DriverDueEntityRepository extends PagingAndSortingRepository<DriverDueEntity, Integer>{

	Page<DriverDueEntity> findAllByRecordedDate(Date startDate, Pageable createPageRequest);

	Page<DriverDueEntity> findAllByRecordedDateBetween(Date startDate, Date endDate, Pageable createPageRequest);

	Page<DriverDueEntity> findAllByDriverIdAndRecordedDateBetween(Integer driverId, Date startDate, Date endDate,
			Pageable createPageRequest);

}
