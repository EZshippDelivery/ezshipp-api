package com.ezshipp.api.model;

import lombok.Data;

@Data
public class ManualAssignRequest {
    private Integer orderId;
    private Integer statusId;
    private Integer newBikerId;
    private Integer oldBikerId;
}