package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverRatingEntity;

public interface DriverRatingRepository extends JpaRepository<DriverRatingEntity, Integer>{

}
