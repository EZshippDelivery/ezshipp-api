package com.ezshipp.api.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikerStatsAndCod {
    private Integer bikerId;
    private String name;
    private Double deliveryAmount;
    private Double codAmount;
    private Integer pickedCount;
    private Integer deliveredCount;
    private Integer exchangedCount;
    private List<String> pickedOrders;
    private List<String> deliveredOrders;
    private List<String> exchangedOrders;
    private List<Integer> pickedCustomers;
    private List<Integer> deliveredCustomers;
    private List<Integer> exchangedCustomers;

    private Date recordedDate;
}

