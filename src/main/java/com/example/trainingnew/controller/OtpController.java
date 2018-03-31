package com.example.trainingnew.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.services.OtpServices;

@RestController
public class OtpController {
	

private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OtpServices otpServices;

	public String generateOtp() {


		String username = "user";
		int otp = otpServices.generateOTP(username);
		
		logger.info("OTP : "+otp);
		return "";
	}
	
}
