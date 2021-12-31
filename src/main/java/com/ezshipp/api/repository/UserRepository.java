package com.ezshipp.api.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.enums.UserType;
import com.ezshipp.api.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Object findByUsername(String username);

	Optional<UserEntity> findByEmail(String username);

	List<UserEntity> findByUserTypeAndCreatedTimeBetween(UserType customer, Timestamp startDate, Timestamp endDate);

	Optional<UserEntity> findByPhone(Long valueOf);

}
