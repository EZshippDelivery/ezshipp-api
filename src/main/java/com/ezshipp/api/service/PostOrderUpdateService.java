package com.ezshipp.api.service;

import org.springframework.stereotype.Service;

import com.ezshipp.api.model.UpdateOrderRequest;
import com.ezshipp.api.persistence.entity.OrderEntity;

import lombok.Setter;

@Service
public class PostOrderUpdateService {

	@Setter
    private OrderEntity orderEntity;
    @Setter
    private UpdateOrderRequest updateOrderRequest;

}
