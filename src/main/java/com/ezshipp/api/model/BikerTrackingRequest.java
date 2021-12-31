package com.ezshipp.api.model;

import lombok.Data;

@Data
public class BikerTrackingRequest {
    private int driverId;
    private double longitude;
    private double latitude;
    private boolean onlineMode;

}
