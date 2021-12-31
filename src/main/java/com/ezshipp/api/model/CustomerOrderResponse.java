package com.ezshipp.api.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CustomerOrderResponse {
    private Integer id;
    private String orderSeqId;
    private String externalId;
    private Timestamp orderCreatedTime;

    private String customerName;
    private String customerPhone;
    private String senderName;
    private String senderPhone;
    private String receiverName;
    private String receiverPhone;

    private Integer pickAddressId;
    private String pickAddress;
    private String pickFlatNumber;
    private String pickLandmark;
    private Double pickLongitude;
    private Double pickLatitude;

    private Integer dropAddressId;
    private String dropAddress;
    private String dropFlatNumber;
    private String dropLandmark;
    private Double dropLongitude;
    private Double dropLatitude;

    private String item;
    private String itemDescription;
    private String itemImage;

    private String orderType;
    private String bookingType;
    private String paymentType;

    private int statusId;
    private String status;

    private double deliveryCharge;
    private double codCharge;
    private double totalCharge;
    private boolean collectAtPickup;

    private Integer bikerId;
    private String bikerName;
    private long bikerPhone;
    private String bikerProfileUrl;

    private boolean feedbackSubmitted;
}
