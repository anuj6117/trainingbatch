package com.example.trainingnew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.services.EmailServices;

@RestController
public class EmailController {

	@Autowired
	EmailServices service;

	
	@RequestMapping(value="/sendotp", method=RequestMethod.POST)
	public boolean SendOtpOnMailAndPhone(@RequestBody String email, int otp) {
			return service.sendSimpleMessage(email,otp);
	}
	
//	public String validate(long otp) 
//	{
//		retrun service.otpValidate();
//	}

}
