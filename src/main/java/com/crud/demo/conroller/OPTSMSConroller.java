package com.crud.demo.conroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.model.OTPSMS;
import com.crud.demo.model.TokenModel;
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

	@RequestMapping(value="/verifyuser",method=RequestMethod.POST)
	public ResponseEntity<Object> verifyOTPSMS(@RequestBody OTPSMS otpsms)
	{  System.out.println("verifyOTPSMS contoller::::::::::::::::::::"+otpsms.getUserId());
		
		Map<String, Object> map = null;
		try {
			map = otpsmsService.verifyOTPSMS(otpsms);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
		}
	}
}
