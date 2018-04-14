package com.example.traning.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Otp;
import com.example.traning.service.EmailService;
import com.example.traning.utill.CustomExceptionhandler;



@RestController
public class EmailController {
	@Autowired
	EmailService emailservice;
/*
	
	//send email
    @RequestMapping(value="/email",method=RequestMethod.POST)
	public String SendEmail(@RequestBody Email mail) {
    	emailservice.sendSMS(); 
    	return emailservice.sendSimpleMessage(mail);
    }
    */
   
   @RequestMapping(value="/verifyuser",method=RequestMethod.POST)
    public ResponseEntity<Object> validate(@RequestBody Otp tokenOTP) {
	   System.out.println("validation--->1");
	   Otp obj=null;
	   try {
 			obj= emailservice.validation(tokenOTP);
 	            }
 	    	catch(Exception e) {
 	    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false,e.getMessage(), obj);
 	    	}
 	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"OTP validate suceessfuly", obj);

	
  
    }
     
    
    
    }