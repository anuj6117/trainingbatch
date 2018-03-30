package com.crud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
	@Value("${spring.mail.username}")
	private String emailFrom;
	@Autowired
	private JavaMailSender javaMailSender;

	/*public String sendEmailToGmail(Email email) {
		System.out.println("hitting to Email service");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setFrom(email.getFrom());
		message.setSubject(email.getSubject());
		message.setText(email.getMessage());
		javaMailSender.send(message);
		return "Mail sent successfully";
		
	}*/
	public void sendEmailToGmail(String emailTo,String automaticOTP)
	{
		System.out.println("hitting to Email service");
		System.out.println("EmailTo is:::"+emailTo+":::::::::::Automatic otp is::::::"+automaticOTP);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailTo);
		message.setFrom(emailFrom);
		message.setSubject("You otp is");
		message.setText("You otp is"+automaticOTP);
		javaMailSender.send(message);
		System.out.println("otp successfully send to Email");
	}

}
