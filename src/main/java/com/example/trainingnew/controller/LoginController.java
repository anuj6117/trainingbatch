package com.example.trainingnew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.services.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	 LoginService loginservice;
	
	@RequestMapping(value="/mylogin", method = RequestMethod.POST)
	public String login(@RequestBody UserModel model) {
		
		return loginservice.doLogin(model.getEmail(),model.getPassword());
	}

}
