/*package com.example.traning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Register;
import com.example.traning.service.LoginService;

@RestController
public class LoginController {
	@Autowired
	LoginService loginservice;
	@RequestMapping(value="/lol",method=RequestMethod.POST)
	public String Log(@RequestBody Register register) {
		System.out.println("<----helloo---->_");
		return loginservice.Login(register.getUserName(),register.getPassword());
	}
}
*/