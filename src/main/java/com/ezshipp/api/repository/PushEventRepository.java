package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.PushEventEntity;

public interface PushEventRepository extends JpaRepository<PushEventEntity, Integer>{

}
