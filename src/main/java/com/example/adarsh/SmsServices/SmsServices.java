package com.example.adarsh.SmsServices;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service

public class SmsServices {

	public static final String ACCOUNT_SID = "ACccb5c41d79c1c5d488e02b637cbaeca3";
	public static final String AUTH_TOKEN = "6fb69e93b039680579e4a720bd3037e0";
	public static final String TWILIO_NUMBER = "+12622143651";

	public void sedSms(int otp) {

		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", "your Otp Is= " + otp));
			params.add(new BasicNameValuePair("To", "+917290801395")); // Add real number here
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}
}
