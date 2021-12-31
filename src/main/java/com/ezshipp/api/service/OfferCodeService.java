package com.ezshipp.api.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.persistence.entity.OfferUsedDetailsEntity;
import com.ezshipp.api.repository.OfferUsedDetailsRepository;

@Service
public class OfferCodeService {

	@Autowired
	OfferUsedDetailsRepository offerUsedDetailsRepository;
	
	void addOfferCodeUsedByCustomer(Integer offerId, Integer customerId) throws ServiceException {
        OfferUsedDetailsEntity offerUsedDetailsEntity = new OfferUsedDetailsEntity();
        offerUsedDetailsEntity.setCustomerId(customerId);
        offerUsedDetailsEntity.setOfferId(offerId);
        Date date= new Date();
        long time = date.getTime();
        offerUsedDetailsEntity.setOfferUsedDate(new Timestamp(time));
        offerUsedDetailsRepository.save(offerUsedDetailsEntity);
    }
	
	
}
