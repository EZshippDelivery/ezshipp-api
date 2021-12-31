package com.ezshipp.api.model;

import java.util.List;

import com.ezshipp.api.enums.EMessageType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Otp {
    @JsonProperty("eMessageType")
    private EMessageType eMessageType;
    private String otpCode;
    /**
     * The recipients.
     */
    private List<String> recipients;
}