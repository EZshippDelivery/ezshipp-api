package com.ezshipp.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CreateOrderRequest {

	 private Integer customerId;

	    private Integer userId; // in case of Admin creates the order, it will be userId

	    @Size(min=3, message="Receiver Name should have at least 3 characters")
	    private String receiverName;

	    @Size(min=10, message="Receiver Phone Number should have at least 10 characters")
	    private String receiverPhone;

	    @Size(min=3, message="Sender Name should have at least 3 characters")
	    private String senderName;

	    @Size(min=10, message="Sender Phone Number should have at least 10 characters")
	    private String senderPhone;

	    @Pattern(regexp = "CASH|ONLINE|MONTHLY", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String payType;
	    private String paymentId;

	    @Pattern(regexp = "IOS|ANDROID|WEB|API", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String orderSource;

	    @Pattern(regexp = "SHIP|STORE", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String orderType;

	    @Pattern(regexp = "FOURHOURS|SAMEDAY|INSTANT", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String bookingType;

	    @NotBlank
	    private String pickAddress;
	    @NotBlank
	    private String dropAddress;

	    @Size(min=4, message="External ID should have at least 4 characters")
	    private String externalId;

	    private double pickLatitude;

	    private double pickLongitude;

	    private double dropLatitude;

	    private double dropLongitude;

	    @NotBlank
	    private String itemName;
	    private String itemDescription;
	    private String itemImageUrl;

	    private String pickFlat;
	    private String dropFlat;
	    private String pickLandmark;
	    private String dropLandmark;
	    private boolean collectAtPickUp = true;
	    private double codAmount;
	    private double deliveryCharge;

	    private boolean offerCode;

	    @JsonIgnore
	    private String orderSeqId; // this is for purpose of adding orders.

}
