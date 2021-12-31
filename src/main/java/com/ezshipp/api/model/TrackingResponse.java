package com.ezshipp.api.model;

import lombok.Data;

@Data
public class TrackingResponse {
    private double pickLatitude;
    private double pickLongitude;
    private double dropLatitude;
    private double dropLongitude;
    private double bikerLongitude;
    private double bikerLatitude;
    private Integer driverId;
    private Integer orderId;
}
