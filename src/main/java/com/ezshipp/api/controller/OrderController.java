package com.ezshipp.api.controller;

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
import com.ezshipp.api.model.CreateExternalOrderRequest;
import com.ezshipp.api.model.CreateOrderRequest;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.model.SuccessResponse;
import com.ezshipp.api.model.UpdateComment;
import com.ezshipp.api.model.UpdateOrderRequest;
import com.ezshipp.api.repository.OrderRespository;
import com.ezshipp.api.service.CreateOrderService;
import com.ezshipp.api.service.OrderEntityService;
import com.ezshipp.api.service.UpdateOrderService;

@RequestMapping("/order")
@RestController
public class OrderController {

	 @Autowired
	 OrderRespository orderRespository;
	 
	 @Autowired
	 CreateOrderService createOrderService;
	
	 @Autowired
	 OrderEntityService orderEntityService;
	 
	 @Autowired
	 UpdateOrderService updateOrderService;
	 
	
	 @PostMapping("/createadminorder")
	 public OrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws Exception {
		 OrderResponse orderResponse = createOrderService.create(createOrderRequest);
		 return orderResponse;
	 }
	 
	 @PostMapping("/client/create")
	 public CustomerOrderResponse createClientOrder(@RequestBody CreateExternalOrderRequest createOrderRequest) throws Exception {
		 CustomerOrderResponse orderResponse = createOrderService.createExternalCustomerOrder(createOrderRequest);
		 return orderResponse; 
	 }
	 
	 @PutMapping("/{orderId}") 
     public OrderResponse updateOrder(@PathVariable Integer orderId,@RequestBody UpdateOrderRequest updateOrderRequest) throws BusinessException, ServiceException  {
        OrderResponse orderResponse = updateOrderService.update(orderId, updateOrderRequest);
        return orderResponse;
     }
	 
	  @PutMapping("/update/{orderSeqId}")
      public OrderResponse updateOrderBySeqId(@PathVariable String orderSeqId,@RequestBody UpdateOrderRequest updateOrderRequest) throws BusinessException, ServiceException  {
      Integer orderId = orderEntityService.findByOrderSeqId(orderSeqId);
      OrderResponse orderResponse = updateOrderService.update(orderId, updateOrderRequest);
      return orderResponse;
      }

		@PutMapping("/update/{orderId}/comments")
		public OrderResponse updateOrderComments( @PathVariable Integer orderId,@RequestBody UpdateComment updateComment) throws BusinessException, ServiceException  {
		    OrderResponse orderResponse = updateOrderService.updateComments(orderId, updateComment);
		    return orderResponse;
		}
		
		@GetMapping("/find/{orderId}")
	    public OrderResponse findByOrderId(@PathVariable Integer orderId) throws Exception {
	        OrderResponse orderResponse = orderEntityService.findById(orderId);
	        return orderResponse;
	    }
		
		@GetMapping("/find/barcode/{id}")
	    
	    public OrderResponse findOrderByBarcode(@PathVariable String id) throws BusinessException, ServiceException {
	        OrderResponse orderResponse = orderEntityService.findByBarcode(id);
	        return orderResponse;
	    }

	 
//     @GetMapping("/find/{orderId}")
//     public OrderResponse findByOrderId(@PathVariable Integer orderId) throws Exception { 
//		 OrderResponse orderResponse = orderEntityService.findById(orderId);
//	     return orderResponse; 
//	 }
}
