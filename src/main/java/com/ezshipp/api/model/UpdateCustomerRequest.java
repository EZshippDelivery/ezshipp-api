package com.ezshipp.api.model;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String deviceToken;
    private boolean receiveSMS;
    private boolean receivePush;
    private boolean receiveEmail;

}
