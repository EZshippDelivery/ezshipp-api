package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DeviceEntity;

public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Integer>{

}
