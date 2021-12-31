package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.PaymentEntity;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, Integer>{

}
