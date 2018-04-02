package com.example.adarsh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Dto.LoginDto;
import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.service.LoginServices;

@RestController

public class LoginController {

	@Autowired
	private LoginServices loginData;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> userLogin(@RequestBody LoginDto loginDto) {
		if (loginDto.getEmail().equals("") || loginDto.getPassword().equals("") || loginDto.getEmail() == null
				|| loginDto.getPassword() == null) {
			return ResponseHandler.invlidResponse(HttpStatus.OK, false, "Email or password can't be null");
		} else {
			return loginData.userLogin(loginDto.getEmail(), loginDto.getPassword());
		}
	}
}
