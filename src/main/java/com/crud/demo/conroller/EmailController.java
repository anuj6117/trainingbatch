/*package com.crud.demo.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.model.Email;
import com.crud.demo.service.EmailService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/send-email",method=RequestMethod.POST)
	public String sendEmailToGmail(@RequestBody Email email) {
		System.out.println("hitting to email controller");
		 return emailService.sendEmailToGmail(email);
		
		

     }
}
*/