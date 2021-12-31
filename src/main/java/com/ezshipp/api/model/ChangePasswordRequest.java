package com.ezshipp.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    @ApiModelProperty(notes="current password", required = true)
    private String oldPassword;
    @ApiModelProperty(notes="new password", required = true)
    private String newPassword;

}
