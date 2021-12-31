package com.ezshipp.api.enums;


public enum OrderStatus {
    NA(0, "Invalid Status", ""),
    NEW(1, "New", ""),
    ASSIGNED(2, "Assigned", "Order has assigned"),
    ACCEPTED(3, "Accepted", ""),
    ENROUTE_PICKUP(4, "Enroute Pickup", "Rider is on the way to pick the parcel."),
    AT_PICKUP(5, "At Pickup Point", ""),
    PICKED_UP(6, "Picked", "Rider picked the parcel."),
    EXCHANGED(7, "Exchanged", "Rider is exchanged the parcel"),
    DROP_AT_ZONE(8, "Zoned", "Rider exchanged the order to another Rider."),
    ENROUTE_DELIVERY(9, "Enroute Delivery", "Rider is on the way to deliver the parcel."),
    NO_RESPONSE(10, "Customer Not Responding", "Customer is not responding to the Rider"),
    AT_DELIVERY(11, "At Delivery Point", "Rider is at Delivery point."),
    DELIVERED(12, "Delivered", "Your parcel has been delivered."),
    CANCELLED(13, "Cancelled", "your order has been cancelled."),
    REJECTED(14, "Rejected", "Your order has been rejected."),
    EXPIRED(15, "Expired", "Your order has been expired due to open order for long time."),
    SCANNED(16, "Scanned", ""),
    WAITING_FOR_BRANCH_APPROVAL(17,"Waiting For Branch Approval", "Waiting for order approval from branch"),
    BRANCH_REJECTED_ORDER(18,"Branch Rejected Order", "Order rejected from branch"),
    INTIATED(19,"Order Intiated", "Order Initiated");

    OrderStatus(final int statusId, final String status, String userMessage)   {
        this.statusId = statusId;
        this.status = status;
        this.userMessage = userMessage;
    }

    private final int statusId;
    private final String status;
    private final String userMessage;

    public int getStatusId()    {
        return statusId;
    }

    public String getStatus()    {
        return status;
    }

    public String getUserMessage()    {
        return userMessage;
    }

    public static OrderStatus getById(int statusId) {
        for(OrderStatus e : values()) {
            if(e.statusId == statusId)
                return e;
        }
        return NA;
    }

    public static OrderStatus getByName(String status) {
        for(OrderStatus e : values()) {
            if(e.status.equals(status))
                return e;
        }
        return NA;
    }
}
