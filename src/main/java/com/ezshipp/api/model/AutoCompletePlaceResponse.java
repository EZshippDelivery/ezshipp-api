package com.ezshipp.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class AutoCompletePlaceResponse {
    private double longitude;
    private double latitude;
    private String description;
    private String address;
    private String input;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    private String placeId;
}