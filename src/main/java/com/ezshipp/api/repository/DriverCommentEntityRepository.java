package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.DriverCommentEntity;

public interface DriverCommentEntityRepository extends JpaRepository<DriverCommentEntity, Integer> {
}
