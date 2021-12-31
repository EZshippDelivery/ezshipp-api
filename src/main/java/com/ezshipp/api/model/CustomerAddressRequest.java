package com.ezshipp.api.model;

import com.ezshipp.api.enums.AddressType;

import lombok.Data;

@Data
public class CustomerAddressRequest {

    private Integer customerId;
    private String address1;
    private String address2;
    private String landmark;
    private String sTag;
    private String city;
    private String apartment;
    private String complexName;
    private Long pincode;
    private String state;
    private double latitude;
    private double longitude;
    private AddressType type;

}
