package com.ezshipp.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.ManualAssignRequest;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.service.AdminBikerService;
import com.ezshipp.api.service.BikerAssignmentService;
import com.ezshipp.api.service.BikerStatsAndCODService;
import com.ezshipp.api.service.DriverDetailEntityService;
import com.ezshipp.api.service.DriverEntityService;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

	@Autowired
	DriverEntityService driverEntityService;
	
	@Autowired
	DriverDetailEntityService driverDetailEntityService;
	
	@Autowired
	AdminBikerService adminBikerService;
	
	@Autowired
	BikerStatsAndCODService bikerStatsAndCODService;
	
	@Autowired
	BikerAssignmentService bikerAssignmentService;
	
	@PostMapping("/bikers/detail")
	public PagingResponse getAllBikerDetails(@Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
	    return driverEntityService.getAllBikersForAdmin(baseFilter);
	}
	
	@PostMapping("/bikers/cod")
	public PagingResponse getAllBikersCOD(@Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
	    return driverDetailEntityService.getAllBikersCOD(baseFilter);
	}
	
	@PostMapping("/bikers/delivery")
	public PagingResponse getAllBikersOrderStats(@Valid @RequestBody BaseFilter baseFilter) throws BusinessException {
	    return driverDetailEntityService.getAllBikersOrderStats(baseFilter);
	}
	
	@PostMapping("/bikers/payments")
	public PagingResponse getAllBikersEarnings(@Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
	    return driverDetailEntityService.getAllBikersPayments(baseFilter);
	}
	
	@PostMapping("/bikers/cod/{driverId}")
    public PagingResponse getBikerRecentCODs(@PathVariable Integer driverId,
                                             @Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
        return driverDetailEntityService.getBikerRecentCOD(driverId, baseFilter);
    }
	
	@PostMapping("/bikers/payments/{driverId}")
	public PagingResponse getBikerRecentPayments(@PathVariable Integer driverId,
	                                             @Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
	    return driverDetailEntityService.getBikerRecentPayments(driverId, baseFilter);
	}
	
	@PostMapping("/bikers/orders/{driverId}")
	public PagingResponse getBikerRecentOrders(@PathVariable Integer driverId,
	                                               @Valid @RequestBody BaseFilter baseFilter) throws BusinessException, ServiceException {
	        return driverEntityService.getBikerRecentOrders(driverId, baseFilter);
	}
	
	@PutMapping("/bikers/payment/update/{minusDays}")
	public String updateBikerPayments(@PathVariable Integer minusDays) throws ServiceException {
	    adminBikerService.updateBikerPayments(minusDays);
	    return  "Successfully updated Biker CODs";
	}
	
	@PutMapping("/bikers/cod/update/{minusDays}")
	public String updateBikerCODs(@PathVariable Integer minusDays) throws ServiceException, BusinessException {
	    bikerStatsAndCODService.updateBikerStatsAndCodAmounts(minusDays);
	    return "Successfully updated Biker CODs";
	
	}
	
	@PutMapping("/bikers/manual/assign")
	public String assignBikerManually(@RequestBody ManualAssignRequest manualAssignRequest) throws BusinessException, ServiceException {
	    bikerAssignmentService.manualAssignBiker(manualAssignRequest);
	    return "Manual Assignment success";
	}
}
