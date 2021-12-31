package com.ezshipp.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BikerTracking {
    private double lastLongitude;
    private double lastLatitude;
    private Integer driverId;
    private double kms;
    private double idleTime;
    private int batteryPerc;
    private String name;
    private Date trackDate;
    private boolean online;
    private Date lastUpdatedTime;

}
