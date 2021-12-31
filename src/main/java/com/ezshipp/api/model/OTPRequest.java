package com.ezshipp.api.model;

import lombok.Data;

@Data
public class OTPRequest {
    private int customerId;
    private String phone;
    private String email;
    private String authType; //SMS or EMAIL;

}
