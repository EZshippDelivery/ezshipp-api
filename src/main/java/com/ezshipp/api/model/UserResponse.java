package com.ezshipp.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponse {
    private String name;
    private String phone;
    private String source;
    private Date createdTime;
}
