package com.ezshipp.api.model;



import lombok.Data;

@Data
public class OTPResponse {
    private String otp;
    private String authType;
    private Delivery delivery;
}

