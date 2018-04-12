package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ApprovedTransactionDTO;
import com.example.demo.dto.UserRoledto;
import com.example.demo.model.UserModel;
import com.example.demo.service.TransactionService;
import com.example.demo.utils.ApiResponse;

@Controller
public class TransactionController {
    @Autowired
	TransactionService transactionService;
    
	// To add a new user and a default is created
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ResponseEntity<Object> addTransaction() throws Exception {
		Object response = "";
		try {
			response = transactionService.mainTransaction();
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "sucesscontroller", null);

	}
	@RequestMapping(value = "/showalltransaction", method = RequestMethod.GET)
	public ResponseEntity<Object> getalltransaction() throws Exception {
		Object response;
		try {
			response = transactionService.getalltransaction();
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "sucesscontroller", response);

	}
	@RequestMapping(value = "/approveinrrequest", method = RequestMethod.POST)
	public ResponseEntity<Object> approveINRRequest(@RequestBody ApprovedTransactionDTO approvedTransactionDTO) {
		 Object response="";
			try {
				response = transactionService.approveTransaction(approvedTransactionDTO.getTransactionId(),approvedTransactionDTO.getStatus());
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);
		
	}
	@RequestMapping(value = "/wallethistory", method = RequestMethod.POST)
	public ResponseEntity<Object> walletHistory(@RequestParam(value = "userId") Integer userId) {
		 Object response;
			try {
				response = transactionService.showAllHistory(userId);
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", response);
		
	}
}
