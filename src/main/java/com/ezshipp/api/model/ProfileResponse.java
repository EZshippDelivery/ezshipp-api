package com.ezshipp.api.model;

import com.ezshipp.api.enums.UserType;

import lombok.Data;

@Data
public class ProfileResponse {
    private Integer customerId;
    private String name;
    private String firstName;
    private String lastName;
    private String username;
    private String profileUrl;
    private String email;
    private String referralCode;
    private Long phone;
    private UserType userType;
    private boolean receiveSMS;
    private boolean receivePush;
    private boolean receiveEmail;
    private boolean premium;
}
