package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.ShiftEntity;

public interface ShiftEntityRepository extends JpaRepository<ShiftEntity, Integer>{

}
