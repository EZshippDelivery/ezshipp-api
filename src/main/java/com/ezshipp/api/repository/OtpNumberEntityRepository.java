package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezshipp.api.persistence.entity.OtpNumberEntity;

public interface OtpNumberEntityRepository extends JpaRepository<OtpNumberEntity, Integer>{

	
	void deleteOtpNumberEntityByContact(String contact);

	OtpNumberEntity findOtpNumberEntityByContactAndVerified(String contact, boolean otpFlag);

	void deleteByContact(String contact);

	OtpNumberEntity findByContact(String contact);

}
