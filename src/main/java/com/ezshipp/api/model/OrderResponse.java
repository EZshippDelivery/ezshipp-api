package com.ezshipp.api.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Integer id;
    private String orderSeqId;
    private Timestamp orderCreatedTime;
    private long dateTime;

    private String customerName;
    private String customerPhone;
    private String senderName;
    private String senderPhone;
    private String receiverName;
    private String receiverPhone;

    private String item;
    private String itemDescription;
    private String itemImage;
    private int itemWeight;

    private String orderType;
    private String bookingType;
    private Integer bookingTypeId;
    private String paymentType;

    private Integer pickAddressId;
    private String pickAddress;
    private Integer dropAddressId;
    private String dropAddress;
    private String pickFlatNumber;
    private String pickLandmark;
    private String dropLandmark;
    private String dropFlatNumber;
    private String pickZone;
    private String dropZone;
    private Double pickLongitude;
    private Double pickLatitude;
    private Double dropLongitude;
    private Double dropLatitude;

    private int statusId;
    private String status;
    private String deliveredAt;
    private String zonedAt;

    private double deliveryCharge;
    private double additionalWeightCharge;
    private double codCharge;
    private double totalCharge;
    private boolean collectAtPickup;
    private String collectAt;

    private double pickDistance;
    private double pickDuration;
    private double pickToDropDistance;
    private double pickToDropDuration;

    private String barCode;

    private Integer bikerId;
    private String bikerName;
    private long bikerPhone;
    private String bikerProfileUrl;
    private Date acceptedTime;
    private Date pickedTime;
    private String pickedBy;
    private String deliveredBy;
    private Date deliveredTime;
    private double totalDuration;
    private boolean feedbackSubmitted;

    private String bikerComments;
    private String orderComments;
    private String cancellationReason;
    private String commentsUpdatedBy;
    private String bikerCommentsUpdatedBy;
    private String cancelledBy;
    private String statusUpdatedBy;
    private boolean customerCancelled;
    private int orderCount;

}