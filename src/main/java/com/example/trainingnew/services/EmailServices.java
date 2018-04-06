package com.example.trainingnew.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.trainingnew.model.OTPModel;
import com.example.trainingnew.reprository.OTPRepo;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service
public class EmailServices {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	OTPRepo otprepo;

	public static final String ACCOUNT_SID = "AC1f7cb11ff4bce6470202ae013267e224";
	public static final String AUTH_TOKEN = "3b39a697a4a308dcfa4e3802e6886243";
	private static final String TWILIO_NUMBER = "+12252634931";

	String body = "Hello User Your Generated Otp is -";
	
	Date date = new Date();

	public boolean sendSimpleMessage(String email,int otp) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("Account Verification");
		message.setText(body + otp);
		message.setTo(email);
		message.setFrom("mohit.kumar@oodlestechnologies.com");

		mailSender.send(message);

		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("Body", "Hello User! Your otp is -" + otp));
			params.add(new BasicNameValuePair("To", "+917500922508"));
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message msg = messageFactory.create(params);
			if(msg!=null)
			{
				return true;
			}
			System.out.println(msg.getSid());
	
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}

		return true;
	}

	
	

}