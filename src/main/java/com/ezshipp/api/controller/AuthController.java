package com.ezshipp.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.model.DriverSignUpRequest;
import com.ezshipp.api.model.LoginRequest;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.security.JwtProvider;
import com.ezshipp.api.security.UserDetailsImpl;
import com.ezshipp.api.service.AuthService;

@RestController
//@RequestMapping(path = "/api/v1/auth")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtUtils;
	
	 @PostMapping("/register/driver")
	 public DriverEntity registerDriver(@RequestBody DriverSignUpRequest signUpRequest) throws Exception{
		 DriverEntity result = authService.registerDriver(signUpRequest);
		 return result;
	 }
	 
	 @PostMapping("/register/customer")
	 public CustomerEntity registerCustomer(@RequestBody DriverSignUpRequest signUpRequest) throws Exception{
		 CustomerEntity result = authService.registerCustomer(signUpRequest);
		 return result;
	 }
	 
	 @PostMapping("/register/signin")
		public String authenticateUser(@RequestBody LoginRequest loginRequest) {

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
//			List<String> roles = [];
//					userDetails.getAuthorities().stream()
//					.map(item -> item.getAuthority())
//					.collect(Collectors.toList());

			return jwt;
		}
}
