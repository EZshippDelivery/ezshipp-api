package com.ezshipp.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikerPaymentResponse {
    private Integer bikerId;
    private String name;
    private double deliveryAmount;
    private double loginHourAmount;
    private double penaltyAmount;
    private double totalIncentives;
    private Date payDate;
}
