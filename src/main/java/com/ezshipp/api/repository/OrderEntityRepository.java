package com.ezshipp.api.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.persistence.entity.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer>{

	List<OrderEntity> findTop10ByCustomerIdOrderByOrderCreatedTimeDesc(Integer customerId);

	List<OrderEntity> findByOrderSeqId(String orderSeqId);

	List<OrderEntity> findByOrderCreatedTimeIsBetween(Timestamp timestamp, Timestamp timestamp2);

	Page<OrderEntity> findAllByCustomerId(Integer customerId, Pageable createPageRequest);

	Integer countByCustomerIdAndStatusIdNotIn(Integer customerId, List<Integer> statuses);

	Optional<OrderEntity> findByBarcode(String barcode);

	

	

	

	

}
