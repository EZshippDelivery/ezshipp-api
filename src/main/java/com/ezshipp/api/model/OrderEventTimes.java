package com.ezshipp.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEventTimes {
    private Date pickedTime;
    private Date deliveredTime;
    private Date zonedTime;
    private Date acceptedTime;
    private Date assignedTime;
    private String sequenceId;
    private String bikerName;
}
