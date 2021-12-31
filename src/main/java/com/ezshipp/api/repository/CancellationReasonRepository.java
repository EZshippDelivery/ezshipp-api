package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.CancellationReasonEntity;

public interface CancellationReasonRepository extends JpaRepository<CancellationReasonEntity, Integer> {
}
