package com.ezshipp.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String licenseUrl;
    private String aadhaarUrl;
    private String aadhaarNumber;
    private String license;
    private String vehicleRegn;
    private String deviceToken;
    private Long phoneNumber;
    private Boolean active;
    private Integer shiftId;
}
