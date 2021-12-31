package com.ezshipp.api.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BikerEarningsDataResponse {

    private double baseSalary;
    private double basePetrol;
    private double instantAmount;
    private double fourHourAmount;
    private double sameDayAmount;
    private double extraKmAmount;
    private double extraFourHourAmount;
    private double extraSameDayAmount;
    private double extraInstantAmount;
    private double extraHourAmount;
    private int minOrderCount;
    private int maxKms;
    private Timestamp recordedDate;
    private Double penaltyAmount;
    private Double totalAmount;
}
