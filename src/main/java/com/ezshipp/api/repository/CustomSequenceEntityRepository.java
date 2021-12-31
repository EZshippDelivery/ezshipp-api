package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.CustomSequenceEntity;


public interface CustomSequenceEntityRepository extends JpaRepository<CustomSequenceEntity, Integer>{

}
