package com.ezshipp.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateOrderRequest {
    private String driverComments;
    private String orderComments;
    private String cancelReason;
    private Integer cancelReasonId;
    private String barcode;
    @ApiParam(value = "statusId is required", required = true)
    @Range(min = 1, max = 16, message = "Please select valid status only, ranging from 1-15")
    @NotNull(message = "statusId is a required field")
    private Integer statusId;
    @PositiveOrZero
    private Integer exceededWeight;
    private Double distance;
    private int zoneId;
    private int waitingTime;
    private int driverId;
    private String signUrl;
    private String deliveredAt;
    private int newDriverId;
    private String collectAt;
    @ApiParam(value = "current biker latitude is required", required = true)
    @NotNull(message = "latitude is a required field")
    private double latitude;
    @ApiParam(value = "current biker longitude is required", required = true)
    @NotNull(message = "longitude is a required field")
    private double longitude;

}
