package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Controller
public class OTPController {

	public static final String ACCOUNT_SID = "AC43b97a8bc9b4c5c8e8b84d3fcae51778";
	public static final String AUTH_TOKEN = "ff486ae6f0c250a406aba95549df5afa";
	public static final String TWILIO_NUMBER = "+15052950431";

	public static void sendSMS(String mobileNo, String otp) {
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", "Hello your otp is" + otp));
			params.add(new BasicNameValuePair("To", "+91" + mobileNo)); // Add real number here
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}

}
