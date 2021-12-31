package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverEntity;

public interface DriverEntityRepository extends JpaRepository<DriverEntity, Integer>{

	List<DriverEntity> findByActiveIsTrue();

}
