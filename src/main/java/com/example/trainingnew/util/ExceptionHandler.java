package com.example.trainingnew.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
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
	
	@org.springframework.web.bind.annotation.ExceptionHandler
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
	 
	        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .findFirst()
	                .orElse(exception.getMessage());
	 
	        return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, errorMsg, null);
	    }
}
