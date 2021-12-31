package com.ezshipp.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.ezshipp.api.enums.AuthType;
import com.ezshipp.api.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignUpRequest {
    @ApiModelProperty(notes="first name", required = true)
    @Size(min = 3, max = 75)
    private String firstName;

    @ApiModelProperty(notes="last name", required = true)
    @Size(min = 3, max = 75)
    private String lastName;

    @Size(max = 70)
    @Email
    @ApiModelProperty(value = "email")
    private String email;

    //@Size(min=10, max = 10, message = "phone number should be 10 digits")
    //@ApiModelProperty(value = "phone", notes="phone number should be 10 digits", required = false)
    private String phone;

    @ApiModelProperty(value = "password", required = true)
    @Size(min = 6, max = 20)
    private String password;

    @ApiModelProperty(value = "userType", required = true, allowableValues = "employee|driver|customer|api|admin")
    private UserType userType;

    @ApiModelProperty(value = "isWebSignUp", required = false)
    private boolean isWebSignUp;

    @JsonIgnore
    private AuthType authType;

}

