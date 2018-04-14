package com.example.traning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Transaction;
import com.example.traning.service.TransactionService;
import com.example.traning.utill.CustomExceptionhandler;

@RestController
public class TransectionController {
	
	@Autowired
	TransactionService transectionservice;
	
	@RequestMapping(value="/transaction",method=RequestMethod.GET)
	public ResponseEntity<Object> transection(){
		Transaction obj=null;
  		try {
  			obj= transectionservice.transaction();
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Transection done suceessfuly", obj);
    	
		
	}
	 @RequestMapping("/showalltransaction")
	 public List<Transaction> getAllTransaction() {
	
		 			
		 return transectionservice.showTransaction();
		 
	 }
	

}
