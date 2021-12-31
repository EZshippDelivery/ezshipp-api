package com.ezshipp.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ezshipp.api.persistence.entity.CustomerEntity;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Integer>, JpaSpecificationExecutor<CustomerEntity> {

	CustomerEntity findCustomerEntityById(Integer customerId);

	Optional<CustomerEntity> findByUserId(int customerId);

	CustomerEntity findCustomerEntityByUserId(Integer id);

	List<CustomerEntity> findAllByIdIn(List<Integer> ids);

	List<CustomerEntity> findAllByPremiumFalse();

	

	

	

}
