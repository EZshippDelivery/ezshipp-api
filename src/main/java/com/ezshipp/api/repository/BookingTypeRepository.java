package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.persistence.entity.BookingTypeEntity;

public interface BookingTypeRepository extends JpaRepository<BookingTypeEntity, Integer>{

}
