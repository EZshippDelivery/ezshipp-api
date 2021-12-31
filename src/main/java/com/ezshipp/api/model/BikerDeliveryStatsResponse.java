package com.ezshipp.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikerDeliveryStatsResponse {
    private Integer bikerId;
    private String name;
    private Integer deliveredCount;
    private Integer pickedCount;
    private Integer exchangeCount;
    private Integer totalCount;
    private Integer loginHourCount;
    private Integer extraHourCount;
    private Integer extraKmsCount;
    private Date trackingDate;

}
