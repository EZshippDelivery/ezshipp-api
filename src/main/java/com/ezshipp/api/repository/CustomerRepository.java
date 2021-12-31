package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String>{

}
