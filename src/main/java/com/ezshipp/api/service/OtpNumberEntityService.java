package com.ezshipp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.persistence.entity.OtpNumberEntity;
import com.ezshipp.api.repository.OtpNumberEntityRepository;

@Service
public class OtpNumberEntityService {

	@Autowired
	OtpNumberEntityRepository otpNumberEntityRepository;
	
	 public boolean deleteByContact(String contact) {
	      boolean 	status = false; 
	      otpNumberEntityRepository.deleteOtpNumberEntityByContact(contact);
	      return status;
	  }
	 
	 public boolean saveOrUpdateOtp(OtpNumberEntity otpNumberEntity) {
		 OtpNumberEntity available = otpNumberEntityRepository.findByContact(otpNumberEntity.getContact());
		 if(available !=null) {
			 available.setVerificationCode(otpNumberEntity.getVerificationCode());
			 available.setVerified(false);
			 otpNumberEntityRepository.save(available);
			 return true;
		 }
		 else if(available == null){
			 otpNumberEntityRepository.save(otpNumberEntity);
			 return true;
		 }
		return false;
		 
	 }
	 public OtpNumberEntity findByContact(String contact, boolean otpFlag) throws ServiceException {
	        return otpNumberEntityRepository.findOtpNumberEntityByContactAndVerified(contact, otpFlag);
	    }
}
