package com.ezshipp.api.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CalculateOrderRequest {

    private Integer customerId;
    private Integer pickAddressId;
    private Integer deliveryAddressId;
    @Pattern(regexp = "FOURHOURS|SAMEDAY|INSTANT", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String bookingType;
}
