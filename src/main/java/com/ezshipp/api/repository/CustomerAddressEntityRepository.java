package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.enums.AddressType;
import com.ezshipp.api.persistence.entity.CustomerAddressEntity;

public interface CustomerAddressEntityRepository extends JpaRepository<CustomerAddressEntity, Integer>{

	List<CustomerAddressEntity> findTop10ByCustomerIdOrderByAddressIdDesc(Integer customerId);

	List<CustomerAddressEntity> findByCustomerIdAndAddressType(Integer customerId, AddressType type);

	void deleteAddressByCustomerIdAndAddressId(Integer customerId, Integer addressId);

}
