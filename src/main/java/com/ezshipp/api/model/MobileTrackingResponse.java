package com.ezshipp.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileTrackingResponse {
    private String dropAddress;
    private String pickAddress;
    private String orderSeqId;
    private String receiverName;
    private String receiverPhone;
    private String itemName;

    private double pickLatitude;
    private double pickLongitude;
    private double dropLatitude;
    private double dropLongitude;
    private double bikerLongitude;
    private double bikerLatitude;

    private Integer driverId;
    private String driverName;
    private String driverPhone;
    private String profilePic;
    private Integer orderId;

}
