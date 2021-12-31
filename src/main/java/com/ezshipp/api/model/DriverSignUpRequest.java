package com.ezshipp.api.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DriverSignUpRequest extends SignUpRequest {
    @Pattern(regexp="(IOS|ANDROID)", message="invalid device type")
    @ApiModelProperty(value = "deviceType", notes="invalid device type", required = true, allowableValues = "IOS|ANDROID")
    private String deviceType;

    @ApiModelProperty(value = "deviceToken", notes="device token", required = true)
    @Size(max = 200)
    private String deviceToken;

    @ApiModelProperty(value = "deviceId", notes="device id", required = true)
    @Size(max = 50)
    private String deviceId;

    @ApiModelProperty(value = "deviceMake", notes="make of the device", required = true)
    @Size(max = 45)
    private String deviceMake;

    @ApiModelProperty(value = "deviceModel", notes="model of the device", required = true)
    @Size(max = 45)
    private String deviceModel;

    @ApiModelProperty(value = "os", notes="operating system of the device")
    @Size(max = 15)
    private String os;

    @JsonIgnore
    private String customerId;
}

