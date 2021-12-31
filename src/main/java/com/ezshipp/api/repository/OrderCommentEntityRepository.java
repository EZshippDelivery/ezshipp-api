package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.OrderCommentEntity;

public interface OrderCommentEntityRepository extends JpaRepository<OrderCommentEntity, Integer>{

}
