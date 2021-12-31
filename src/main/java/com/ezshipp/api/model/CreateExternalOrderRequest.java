package com.ezshipp.api.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExternalOrderRequest {
	 private Integer customerId;

	    @Size(min=4, message="External ID should have at least 4 characters")
	    private String externalId;

	    @Size(min=3, message="Receiver Name should have at least 3 characters")
	    private String receiverName;

	    @Size(min=10, message="Receiver Phone Number should have at least 10 characters")
	    private String receiverPhone;

	    @Pattern(regexp = "IOS|ANDROID|WEB|API", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String orderSource;

	    @Pattern(regexp = "SHIP|STORE", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String orderType;

	    @Pattern(regexp = "FOURHOURS|SAMEDAY", flags = Pattern.Flag.CASE_INSENSITIVE)
	    private String bookingType;

	    private String dropAddress1;
	    private String dropAddress2;
	    private String dropAddress3;
	    private String dropPinCode;
	    private String dropFlat;  
	    private String dropLandmark;

	    private String pickAddress1;
	    private String pickAddress2;
	    private String pickAddress3;
	    private String pickPinCode;
	    private String pickFlat;        
	    private String pickLandmark;

	    @NotBlank
	    private String itemName;
	    private String itemDescription;


	    private double codAmount;
	    private double deliveryCharge;
	    private int weight;

	    // added for bulk order re-invent
	    private String bikerName;
	    private String status;
	    private Date orderCompletedTime;
	    private String comments;
	    private int createdBy;
	    private int lastModifiedBy;

}
