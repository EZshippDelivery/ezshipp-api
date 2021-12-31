package com.ezshipp.api.model;

import lombok.Data;

@Data
public class RatingRequest {
    private int driverId;
    private int customerId;
    private int orderId;
    private int rating;
    private boolean wearingTShirt;
    private boolean carryingBag;
    private boolean deliveredProperly;
    private String notes;

}