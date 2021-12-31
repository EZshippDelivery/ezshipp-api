package com.ezshipp.api.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;


@Data

public class CreateCustomerOrderRequest extends StoreCreateCustomerOrder {
    private Integer customerId;

    @Size(min=3, message="Receiver Name should have at least 3 characters")
    private String receiverName;

    @Size(min=10, message="Receiver Phone Number should have at least 10 characters")
    private String receiverPhone;

    private String senderName;

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

    @Size(min=4, message="External ID should have at least 4 characters")
    private String externalId;

    private String itemName;
    private String itemDescription;
    private String itemImageUrl;

    private Integer pickAddressId;
    private Integer deliveryAddressId;
    private boolean collectAtPickUp = true;
    private double codAmount;
    private double deliveryCharge;
    private Integer offerId;
//    
//  private List<StoreCreateCustomerOrder> storeCreateCustomerOrder;
    //private String cartId;
}
