package com.ezshipp.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.persistence.entity.ShiftEntity;
import com.ezshipp.api.persistence.entity.ZoneEntity;
import com.ezshipp.api.repository.ShiftEntityRepository;
import com.ezshipp.api.repository.ZoneEntityRepository;

@Service
public class ReferenceDataService {

	@Autowired
	ZoneEntityRepository zoneEntityRepository;
	
	@Autowired
	ShiftEntityRepository shiftEntityRepository;
	
	public List<ZoneEntity> getAllZones()   {
        return zoneEntityRepository.findAll();
    }
	
	public List<ShiftEntity> getAllShifts()   {
        return shiftEntityRepository.findAll();
    }
}
