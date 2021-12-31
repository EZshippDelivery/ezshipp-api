package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.OrderEntity;

public interface OrderRespository extends JpaRepository<OrderEntity, String>{

	

	

}
