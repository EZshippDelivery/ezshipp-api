package com.ezshipp.api.model;

import com.ezshipp.api.enums.DeviceTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BikerResponse {
    private Integer bikerId;
    private String name;
    private String email;
    private String profileUrl;
    private Long phone;
    private String deviceToken;
    private boolean active;
    @JsonIgnore
    private DeviceTypeEnum deviceType;
    private long totalOrdersDelivered;
    private double lastOrderAmount;
    private double todayEarnings;

    private String licenseNumber;
    private String aadhaarNumber;
    private String numberPlate;
    private String shift;
    private String licenseUrl;

}

