package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

	List<AddressEntity> findAddressEntitiesByAddress1(String trim);

}
