package com.ezshipp.api.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String addressString;
    private String number;
    private String street;
    private String apartmentNumber;
    private String city;
    private String state;
    private String pinCode;
    List<String> searchList = new ArrayList<>();

    public Address(String addressString) {
        this.addressString = addressString;
    }

}
