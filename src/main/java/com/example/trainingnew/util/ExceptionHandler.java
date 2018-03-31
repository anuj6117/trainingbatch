package com.example.trainingnew.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

	public static ResponseEntity<Object> generateResponse(HttpStatus httpstatus, boolean error,String message, Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("Data"     , responseObj);
			map.put("IsSuccess", error);
			map.put("Message"  , message);
			map.put("Status"   , httpstatus.value());
			map.put("TimeStamp", new Date());

			return new ResponseEntity<Object>(map,httpstatus);
			
		} catch (Exception e) {
			map.clear();
			map.put("Data"     , null);
			map.put("IsSuccess",false);
			map.put("Message"  , e.getMessage());
			map.put("Status"   , HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("TimeStamp", new Date());
			
			return new ResponseEntity<Object>(map,httpstatus);
		}
	}
}
