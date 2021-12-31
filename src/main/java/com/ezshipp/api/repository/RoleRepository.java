package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;

import com.ezshipp.api.persistence.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{

	Object findByName(String name);

}
