package com.example.demo.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServices {

	@Autowired
	private JavaMailSender sender;

	public void sendMail(String mail, int otp) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(mail);
		helper.setText("hello your otp is  =  " + otp);
		helper.setSubject("verify user");
		sender.send(message);
	}
}
