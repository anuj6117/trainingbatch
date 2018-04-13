package com.example.adarsh.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailSenderController {

	@Autowired
	private JavaMailSender sender;

	@GetMapping("/sendMail")

	public String sendMails() {

		try {

			sendMail();

			return "Email Sent!";

		} catch (Exception ex) {

			return "Error in sending email: " + ex;

		}

	}

	public void sendMail() throws MessagingException {

		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("adarsh123cse@gmail.com");
		helper.setFrom("adarsh@oodleslab.com");
		helper.setText("hello ");
		helper.setSubject("completed");
		sender.send(message);

	}

}
