package com.ezshipp.api.model;

import java.util.Date;

import com.ezshipp.api.enums.DeviceTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Integer id;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date customerAdded;
    private String customerId;
    private boolean isWebSignup;
    private String referralCode;
    private boolean notifyReceiver;
    private boolean isPremium;
    private DeviceTypeEnum deviceType;
    private String deviceMake;
    private String deviceModel;
    private String os;

}
