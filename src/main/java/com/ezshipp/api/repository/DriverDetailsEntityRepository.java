package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverDetailEntity;

public interface DriverDetailsEntityRepository extends JpaRepository<DriverDetailEntity, Integer> {

	DriverDetailEntity findByDriverId(Integer id);

}
