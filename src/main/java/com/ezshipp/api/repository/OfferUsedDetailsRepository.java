package com.ezshipp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.OfferUsedDetailsEntity;

public interface OfferUsedDetailsRepository extends JpaRepository<OfferUsedDetailsEntity, Integer> {
	List<OfferUsedDetailsEntity> findAllByOfferIdAndCustomerId(int id, int customerId);

}
