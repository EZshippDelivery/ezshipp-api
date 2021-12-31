package com.ezshipp.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFilter extends BaseFilter {
    private int orderId;
    private String orderSeqId;
    private String customerName;
    private String customerPhone;
    private List<Integer> bikers;
    private int customerId;
    private String receiverPhone;
    private boolean clientOrders;
    private List<Integer> bookingTypes;
    private  List<Integer> statuses;
    private  List<Integer> paymentTypes;

}
