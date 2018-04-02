package com.example.adarsh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.domain.User;
import com.example.adarsh.repository.UserRepository;

@Service

public class LoginServices {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<Object> userLogin(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return ResponseHandler.invlidResponse(HttpStatus.OK, false, "Wrong email or password");
		} else if (user.getPassword().equals(password)) {
			return ResponseHandler.generateResponse(HttpStatus.OK, true, "Login successfully", user);
		} else {
			return ResponseHandler.invlidResponse(HttpStatus.OK, false, "Wrong email or password");
		}
	}
}
