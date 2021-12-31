package com.ezshipp.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BikerTracking;
import com.ezshipp.api.model.MobileTrackingResponse;
import com.ezshipp.api.model.TrackingResponse;
import com.ezshipp.api.service.TrackingService;

@RestController
@RequestMapping(path = "/api/v1/tracking")
public class TrackingController {

	private final TrackingService trackingService;
	
	public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }
	
	
	@GetMapping("/order/{orderId}")
    public TrackingResponse getTracking(@PathVariable Integer orderId) throws BusinessException, ServiceException {
        TrackingResponse response = trackingService.getOrderTracking(orderId);
        return response;
    }

    @GetMapping("/order/mobile/{orderSeqId}")
    public MobileTrackingResponse getMobileTracking(@PathVariable String orderSeqId) throws BusinessException, ServiceException {
        MobileTrackingResponse response = trackingService.getMobileOrderTracking(orderSeqId);
        return response;
    }

    @GetMapping("/bikers")
    public List<BikerTracking> getAllBikersTracking()  {
        List<BikerTracking> response = trackingService.getAllBikerTracking();
        return response;
    }

}
