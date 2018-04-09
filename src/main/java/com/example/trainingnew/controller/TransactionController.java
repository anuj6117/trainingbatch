package com.example.trainingnew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.model.TransactionModel;
import com.example.trainingnew.services.TransactionService;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class TransactionController {
	
	
	@Autowired
	TransactionService transactionService;
	
	 @RequestMapping(value="/transaction",method=RequestMethod.GET)
	 public ResponseEntity<Object> transaction() {
		 TransactionModel obj = null;

			try {
				obj = transactionService.transaction();
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Sucessfull", obj);
	 }
	

}
