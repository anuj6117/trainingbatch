package com.crud.demo.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.service.OTPSMSService;

@RestController
public class OPTSMSConroller {
	@Autowired
	private OTPSMSService otpsmsService;
	
	@RequestMapping(value="/sendotp")
	public void sendOTPSMS(String automaticOTP)
	{
		otpsmsService.sendOTPSMS(automaticOTP);;
	}

	@RequestMapping(value="/verifyotp")
	public void verifyOTPSMS(String automaticOTP)
	{
		otpsmsService.verifyOTPSMS(automaticOTP);
	}
}
