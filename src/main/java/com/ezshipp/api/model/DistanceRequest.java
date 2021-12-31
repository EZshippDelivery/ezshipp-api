package com.ezshipp.api.model;

import lombok.Data;

@Data
public class DistanceRequest {
    private Integer orderId;
    private double longitude;
    private double latitude;

}
