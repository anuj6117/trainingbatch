package com.example.adarsh.mailService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class MailServieces {

	@Autowired
	private JavaMailSender sender;

	public void sendMail(String email) throws MessagingException {

		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText("hello ");
		helper.setSubject("completed");
		sender.send(message);

	}

}
