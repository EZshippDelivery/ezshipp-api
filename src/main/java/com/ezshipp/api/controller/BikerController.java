package com.ezshipp.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BikerResponse;
import com.ezshipp.api.model.BikerTrackingRequest;
import com.ezshipp.api.model.DistanceRequest;
import com.ezshipp.api.model.MatrixDistance;
import com.ezshipp.api.model.OrderFilter;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.model.RatingRequest;
import com.ezshipp.api.model.UpdateProfileRequest;
import com.ezshipp.api.repository.DriverDetailsEntityRepository;
import com.ezshipp.api.repository.OrderEventRepository;
import com.ezshipp.api.service.DriverEntityService;

@RestController
@RequestMapping("/biker")
public class BikerController {

	@Autowired
	DriverEntityService driverEntityService;
	
	@Autowired
	OrderEventRepository orderEventRepository;
	
	@Autowired
	DriverDetailsEntityRepository driverDetailsEntityRepository;
	
    @PutMapping("/onoff/{driverId}")
    public String offLineMode(@PathVariable Integer driverId, @Valid @RequestBody BikerTrackingRequest bikerTrackingRequest) throws BusinessException  {
        driverEntityService.updateTracking(driverId, bikerTrackingRequest);
        String message = "Biker is " + (bikerTrackingRequest.isOnlineMode() ? "online" : "offline") + " mode now";
        return message;
    }
    
    @GetMapping("/profile/{driverId}")
    public BikerResponse getProfile(@PathVariable Integer driverId) throws ServiceException {
        BikerResponse response = driverEntityService.findProfileById(driverId);
        return response;
    }
    
    @PutMapping("/profile/{driverId}")
    public BikerResponse updateProfile(@PathVariable Integer driverId,@RequestBody UpdateProfileRequest updateProfileRequest) throws BusinessException, ServiceException {
        BikerResponse response = driverEntityService.updateBikerProfile(driverId, updateProfileRequest);
        return response;
    }
    
    @GetMapping("/orders/{driverId}/{isAssigned}")
    public List<OrderResponse> getAllOrdersByBikerId(@PathVariable Integer driverId,@PathVariable Boolean isAssigned) throws BusinessException, ServiceException  {
        return driverEntityService.getAllOrdersByDriverId(driverId, isAssigned);
    }
    
    @GetMapping("/orders/{driverId}/new/{orderId}")
    public OrderResponse getNewOrders(@PathVariable Integer driverId, @PathVariable Integer orderId)  throws BusinessException, ServiceException   {
        return driverEntityService.getNewOrdersByDriverId(driverId, orderId);
    }
    
    @PostMapping("/rating")
    public String bikerRating(@RequestBody RatingRequest ratingRequest) throws ServiceException, BusinessException {
        driverEntityService.saveRating(ratingRequest);
        return "rating updated.";
    }
    
    @PostMapping("/orders/completed/{driverId}")
    public PagingResponse getAllCompletedOrders(@Valid @PathVariable @Size(min = 1, message = "driverId should be valid") Integer driverId,
                                                @Valid @RequestBody OrderFilter orderFilter) throws BusinessException, ServiceException {
        return driverEntityService.getAllCompletedOrders(driverId, orderFilter);
    }
    
    @GetMapping("/orders/acceptedandinprogressorders/{driverId}/{pageNumber}/{pageSize}")
    public PagingResponse getAcceptedAndinProgressOrders(@PathVariable Integer driverId, @PathVariable int pageNumber,
			 @PathVariable int pageSize) throws BusinessException, ServiceException {
        return driverEntityService.getAcceptedAndinProgressOrders(driverId, pageSize, pageNumber);
    }
    
    @PostMapping("/orders/{driverId}/distance")
    public List<MatrixDistance> getDistance(@Valid @RequestBody DistanceRequest distanceRequest) throws BusinessException, ServiceException {
        return driverEntityService.getDistance(distanceRequest);
    }
    

}
