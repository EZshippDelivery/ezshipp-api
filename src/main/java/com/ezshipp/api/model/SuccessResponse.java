package com.ezshipp.api.model;

import lombok.Data;

@Data
public class SuccessResponse {
    private String status;
    private int code;
    private String message;
    private Object data;
}