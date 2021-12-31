package com.ezshipp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezshipp.api.model.TestBean;
import com.ezshipp.api.repository.TestRepository;
import com.ezshipp.api.util.SendSMS;

@RestController
public class TestController {

	@Autowired
	TestRepository testRepo;
	
	@Autowired 
	SendSMS sendSMS;
	
	@GetMapping("/profilecheck")
	public String profilecheck() {
		return "ok";
	}
	
	@PostMapping("/saveUser")
	public TestBean saveUser( @RequestBody TestBean testBean) {
		return testRepo.save(testBean);
	}
	
//	@GetMapping("/sendSms")
//	public String sendOtp() {
//		sendSMS.sendSms("string", "string");
//		return "success";
//	}
}
