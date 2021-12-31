package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.OrderStatusEntity;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Integer>{

}
