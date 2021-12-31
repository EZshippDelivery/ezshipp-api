package com.ezshipp.api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SMSRequest {
    private String message;
    private String phone;
    private String customerName;

}
