package com.ezshipp.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikerCODResponse {
    private Integer bikerId;
    private String name;
    private Double deliveryAmount;
    private Double codAmount;
    private Date recordedDate;
}