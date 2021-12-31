package com.ezshipp.api.model;

import com.ezshipp.api.enums.AddressType;

import lombok.Data;

@Data
public class CustomerAddressResponse {
    private Integer addressId;
    private Integer customerId;
    private String address1;
    private String address2;
    private Long pincode;
    private String state;
    private AddressType addressType;
    private double longitude;
    private double latitude;
    private String apartment;
    private String landmark;
    private String city;
}
