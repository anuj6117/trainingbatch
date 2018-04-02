package com.example.demo.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.UserModel;
import com.example.demo.utils.ResponseHandler;

@RestController
public class AccessDenie {

	@RequestMapping(value = "/403")	
	public ResponseEntity<Object> show()  {
		return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, false,"Login Please",null);
	}
}
