package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.PremiumCustomerEntity;

public interface PremiumCustomerEntityRepository extends JpaRepository<PremiumCustomerEntity, Integer>{

	PremiumCustomerEntity findByCustomerId(Integer id);

}
