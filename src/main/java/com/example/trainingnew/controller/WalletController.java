package com.example.trainingnew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.DTO.UserWalletDTO;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.model.Walletmodel;
import com.example.trainingnew.services.WalletServices;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class WalletController {
	
	@Autowired
	WalletServices services;
	
	@RequestMapping(value="/addwallet",method=RequestMethod.POST)
	public ResponseEntity<Object> createWallet(@Validated @RequestBody UserWalletDTO userwalletdto) {
		
		Walletmodel obj=null;
    	try {
    	obj= services.createWallet(userwalletdto);
    	}
    	catch(Exception e) {

				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
	}
	
    @RequestMapping(value="/depositamount",method=RequestMethod.POST)
    public ResponseEntity<Object> deposit(@Validated @RequestBody  UserWalletDTO userwalletdto) {
    	
    	Walletmodel obj=null;
    	try {
    	obj= services.depositwallet(userwalletdto);
    	}
    	catch(Exception e) {

				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
    }
    
  //withdraw into wallet
    @RequestMapping(value="/withdrawamount",method=RequestMethod.POST)
    public ResponseEntity<Object> withdrw(@Validated @RequestBody  UserWalletDTO userwalletdto) {
    	Walletmodel obj=null;
    	try {
    	obj= services.withdrawallet(userwalletdto);
    	}
    	catch(Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
    	}
	
}
