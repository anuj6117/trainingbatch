package com.example.adarsh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.domain.Transaction;
import com.example.adarsh.service.TransactionService;

@RestController

public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/gettransaction", method = RequestMethod.GET)
	public ResponseEntity<Object> transaction() {
		Transaction result = null;
		try {

			result = transactionService.gettransaction();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);

	}

	@GetMapping(value = "showalltransaction")
	public ResponseEntity<Object> getalltrancation() {
		List<Transaction> result = null;
		try {
			result = transactionService.getAllTransaction();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}
}
