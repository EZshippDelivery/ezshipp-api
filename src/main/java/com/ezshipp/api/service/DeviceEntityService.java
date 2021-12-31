package com.ezshipp.api.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.persistence.entity.DeviceEntity;
import com.ezshipp.api.repository.DeviceEntityRepository;

@Service
public class DeviceEntityService {

	@Autowired
	DeviceEntityRepository deviceEntityRepository;
	
	 public DeviceEntity createDevice(DeviceEntity deviceEntity) throws ServiceException {
	        return deviceEntityRepository.save(deviceEntity);
	    }
}
