package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.CustomerDriverEntity;

public interface CustomerDriverEntityRepository extends JpaRepository<CustomerDriverEntity, Integer>{

	List<CustomerDriverEntity> findAllByCustomerId(Integer id);

}
