package com.example.demo.utils;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	
	public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error,String message, Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("On response handler::::::::::"+responseObj);
		try {
			map.put("timestamp", new Date());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);
			map.put("data", responseObj);

			return new ResponseEntity<Object>(map,status);
		} catch (Exception e) {
			map.clear();
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map,status);
		}
	}

}


