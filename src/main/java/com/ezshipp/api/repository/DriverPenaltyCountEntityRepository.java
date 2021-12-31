package com.ezshipp.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverPenaltyCountEntity;

public interface DriverPenaltyCountEntityRepository extends JpaRepository<DriverPenaltyCountEntity, Integer>{

	List<DriverPenaltyCountEntity> findAllDriverPenaltyCountByDriverId(int driverId);

	List<DriverPenaltyCountEntity> findAllByDriverIdAndPenaltyDate(Integer driverId, Date todayDate);

}
