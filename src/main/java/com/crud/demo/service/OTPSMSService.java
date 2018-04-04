package com.crud.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.jpaRepositories.OTPJpaRepository;
import com.crud.demo.model.OTPSMS;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service
public class OTPSMSService {
	
	public static final String ACCOUNT_SID = "AC5da7a1a487df49c69c60d8ef2047d625";
    public static final String AUTH_TOKEN = "70aa5bc28558fb8aba65b8c7e73d0b59";
    public static final String TWILIO_NUMBER = "+14845467279";
    private static final Logger LOGGER = LoggerFactory.getLogger(OTPSMSService.class);
    @Autowired
    private OTPJpaRepository otpJpaRepository;
    
    
    
    public void saveOTP(String automaticOTP)
    { OTPSMS otpsms=new OTPSMS();
      otpsms.setDate(new Date());
      otpsms.setTokenOTP(automaticOTP);
      otpJpaRepository.save(otpsms);
      LOGGER.info("Message on service (saveOTP):::::::::::::::::OTP successfully saved");
      /*if otp successfully saved*/
      sendOTPSMS(automaticOTP);
      
    }
	
    public void sendOTPSMS(String automaticOTP) {
		try {
			TwilioRestClient	twilioRestClient=new TwilioRestClient(ACCOUNT_SID,AUTH_TOKEN);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", "Your otp is    "+automaticOTP));
		params.add(new BasicNameValuePair("To", "+919650452899"));
		params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
		
		MessageFactory messageFactory = twilioRestClient.getAccount().getMessageFactory();
        Message message=messageFactory.create(params);
        System.out.println(message.getSid());
        LOGGER.info("Message on service (sendOTPSMS):::::::::::::::::OTP send to phone successfully");
		}catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
			LOGGER.error("Message on service (sendOTPSMS):::::::::::::::::OTP not send");
		}
		
		
	}

	public void verifyOTPSMS(String automaticOTP) {
		OTPSMS verifyOTPSMS=otpJpaRepository.findByTokenOTP(automaticOTP);
		if(verifyOTPSMS!=null)
		{
			otpJpaRepository.delete(verifyOTPSMS);
		}
		
	}	

}
