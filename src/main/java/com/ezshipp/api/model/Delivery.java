package com.ezshipp.api.model;

import java.util.Date;

import lombok.Data;

@Data
public class Delivery {
    private String contact;
    private String sendStatus;
    private Date sendTime;
}