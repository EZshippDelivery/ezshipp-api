package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverIncentiveEntity;

public interface DriverIncentiveEntityRepository extends JpaRepository<DriverIncentiveEntity, Integer>{

	DriverIncentiveEntity findDriverIncentiveByDriverId(Integer driverId);

}
