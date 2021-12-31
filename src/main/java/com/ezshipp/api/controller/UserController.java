package com.ezshipp.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.ChangePasswordRequest;
import com.ezshipp.api.model.SuccessResponse;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.service.AuthService;
import com.ezshipp.api.service.PushNotificationService;
import com.ezshipp.api.service.UserService;
import com.google.maps.errors.ApiError;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public class UserController implements ControllerConstants{

	@Autowired
	 UserService userService;
	
	@Autowired
	 AuthService authService;
	
	@Autowired
	 PushNotificationService pushNotificationService;
	
	
	
	@RequestMapping(method= RequestMethod.GET, value="/{id}")
    public UserEntity show(@PathVariable int id) throws BusinessException, ServiceException {
        return userService.findById(id);
    }
	
	
	@RequestMapping(value = "/password/change", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "change password for the user", notes = "change password for the user with temporary password", response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = SUCCESS_CODE, message = SUCCESS_MESSAGE),
            @ApiResponse(code = BUSINESS_EXCEPTION_CODE, message = BUSINESS_EXCEPTION_MESSAGE, response = ApiError.class),
            @ApiResponse(code = SERVICE_EXCEPTION_CODE, message = SERVICE_EXCEPTION_MESSAGE, response = ApiError.class) })
    //@formatter:on
    public String changePassword(@ApiParam(value = "Password") @Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws BusinessException, ServiceException  {
        authService.changePassword(changePasswordRequest);
        return "User password updated successfully";
    }
}
