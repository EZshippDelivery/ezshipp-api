package com.ezshipp.api.controller;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.CalculateOrderRequest;
import com.ezshipp.api.model.CreateCustomerOrderRequest;
import com.ezshipp.api.model.CustomerAddressRequest;
import com.ezshipp.api.model.CustomerAddressResponse;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.CustomerResponse;
import com.ezshipp.api.model.OTPRequest;
import com.ezshipp.api.model.OTPResponse;
import com.ezshipp.api.model.OTPVerifyResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.model.ProfileResponse;
import com.ezshipp.api.model.UpdateCustomerRequest;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.CustomerRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.service.CreateOrderService;
import com.ezshipp.api.service.CustomerEntityService;
import com.ezshipp.api.service.OTPService;
import com.ezshipp.api.service.OrderEntityService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerEntityRepository customerEntityRepository;
	
	@Autowired
	CustomerEntityService customerEntityService;
	
	@Autowired
	OrderEntityRepository orderEntityRepository;
	
	@Autowired
	OTPService otpService;
	
	@Autowired 
	OrderEntityService orderEntityService;
	
	@Autowired
	CreateOrderService createOrderService;
	
	@GetMapping("/{customerId}")
	public ProfileResponse getCustomer(@PathVariable Integer customerId) throws Exception {
		ProfileResponse profile = customerEntityService.findById(customerId);
		return profile;
	}
	
	@GetMapping("/{customerId}/address")
	public List<CustomerAddressResponse> getFirstTenAddresses(@PathVariable String customerId) throws Exception {
		List<CustomerAddressResponse> response = customerEntityService.getFirstTopTenAddresses(Integer.valueOf(customerId));
		return response;
	}
	
	@PostMapping("/address/add")
	public Integer addCustomerAddress(@RequestBody CustomerAddressRequest customerAddressRequest) {
		Integer addressId = customerEntityService.createCustomerAddress(customerAddressRequest);
		return addressId;
	}
	
	@PutMapping("/{customerId}")
	public CustomerResponse update(@PathVariable String customerId,
								   @RequestBody UpdateCustomerRequest updateCustomerRequest) throws Exception {
		updateCustomerRequest.setCustomerId(Integer.parseInt(customerId));
		CustomerResponse response = customerEntityService.updateCustomer(updateCustomerRequest);
		return response;
		
	}
	
	@DeleteMapping("/{customerId}")
	public String delete(@PathVariable String customerId) {
		CustomerEntity customer = customerEntityRepository.getOne(Integer.parseInt(customerId));
		customerEntityRepository.delete(customer);
		return "deleted";
	}
	
	@DeleteMapping("/{customerId}/address/{addressId}")
	public String deleteAddress(@PathVariable Integer customerId, @PathVariable Integer addressId) throws Exception{
		customerEntityService.deleteCustomerAddress(customerId, addressId);
		return "deleted";
	}
	
	@GetMapping("/{customerId}/orders")
	public List<CustomerOrderResponse> getCustomerOrderHistory(@PathVariable Integer customerId) throws Exception {
		List<CustomerOrderResponse> response = customerEntityService.getCustomerOrderHistory(customerId);
		return response;
	}

	
	@PostMapping("/otp/generate")
	public OTPResponse challengeOTP(@RequestBody OTPRequest otpRequest) throws ServiceException, JSONException, BusinessException{
		OTPResponse otpResponse = otpService.sendOTP(otpRequest);
		return otpResponse;
	}
	
	@GetMapping("/otp/validate")
	public OTPVerifyResponse verifyOTP(@RequestParam String phoneNumber, @RequestParam String otp)
			throws BusinessException, ServiceException {
		OTPVerifyResponse otpVerifyResponse = otpService.verifyOTP(phoneNumber, otp);
		return otpVerifyResponse;
	}
	
	
	@GetMapping("/{customerId}/myorders/{pageNumber}/{pageSize}")
	public PagingResponse getCustomerOrders(@PathVariable Integer customerId, @PathVariable int pageNumber,
											 @PathVariable int pageSize)
			throws BusinessException, ServiceException {
		PagingResponse response = customerEntityService.getCustomerOrders(customerId, pageSize, pageNumber);
		return response;
	}
	
	@GetMapping("/{customerId}/orders/count")
	public Integer getCustomerInProgressOrdersCount(@PathVariable Integer customerId)
			throws BusinessException, ServiceException {
		Integer response = customerEntityService.getCustomerInProgressOrdersCount(customerId);
		return response;
	}
	
	@PostMapping("/order/cost")
	public double calculateOrderCost(@RequestBody CalculateOrderRequest calculateOrderRequest) throws BusinessException, ServiceException {
		double cost = orderEntityService.calculateOrderCost(calculateOrderRequest);
		return cost;
	}
	
	@PostMapping("/order/create")
	public CustomerOrderResponse createOrder(@RequestBody CreateCustomerOrderRequest createCustomerOrderRequest) throws BusinessException, ServiceException {
		CustomerOrderResponse orderResponse = createOrderService.createCustomerOrder(createCustomerOrderRequest);
		return orderResponse;
	}
	
	@GetMapping("/profile/{customerId}")
	public ProfileResponse getProfile(@PathVariable Integer customerId) throws ServiceException, BusinessException {
		ProfileResponse profile = customerEntityService.findById(customerId);
		return profile;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/find/{orderId}")
	public CustomerOrderResponse findByOrderId(@PathVariable Integer orderId) throws ServiceException {
		CustomerOrderResponse orderResponse = orderEntityService.findByOrderId(orderId);
		return orderResponse;
	}
	
//	@PostMapping("/device/add")
//	public ResponseEntity<?> addDevice(@RequestBody DeviceRequest deviceRequest)
//			throws BusinessException, ServiceException {
//		DeviceEntity entity = customerEntityService.createCustomerDevice(deviceRequest, currentUser);
//		return ResponseEntity.ok()
//				.body(new SuccessResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), "device created", entity));
//	}
}
