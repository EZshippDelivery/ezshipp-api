package com.ezshipp.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ezshipp.api.persistence.entity.DriverPaymentEntity;

public interface DriverPaymentRepository extends PagingAndSortingRepository<DriverPaymentEntity, Integer>{

}
