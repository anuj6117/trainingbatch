package com.example.demo.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendEmail {

	public static void sendEmail(String userEmail, Integer otp, String userName, JavaMailSender sender)
			throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(userEmail);
		helper.setText("Your OTP is :" + otp);
		helper.setSubject("Hi, " + userName);
		sender.send(message);
	}
}
