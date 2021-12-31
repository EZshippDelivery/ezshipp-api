package com.ezshipp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.persistence.entity.BookingTypeEntity;
import com.ezshipp.api.persistence.entity.OrderStatusEntity;
import com.ezshipp.api.repository.BookingTypeRepository;
import com.ezshipp.api.repository.OrderStatusRepository;


@Service
public class ReferenceDataMigrationService {
     @Autowired
	 BookingTypeRepository bookingTypeRepository;
	 @Autowired
	 OrderStatusRepository orderStatusRepository;
	 
	 public BookingTypeEntity getBookingType(Integer bookingType)    {
	        return bookingTypeRepository.getOne(bookingType);
	    }
	 
	  public OrderStatusEntity getOrderStatus(Integer statusId)    {
	        return orderStatusRepository.getOne(statusId);
	    }
}
