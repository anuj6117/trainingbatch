package com.example.adarsh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Dto.WalletDto;
import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.service.WalletService;

@RestController

public class WalletController {

	@Autowired
	private WalletService walletService;
	/*
	 * @RequestMapping(value = "/createWallet", method = RequestMethod.POST) public
	 * Wallet addWallet(@RequestBody Wallet wallet) { return
	 * walletService.addWallet(wallet);
	 * 
	 * }
	 */

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	private ResponseEntity<Object> addWallet(@RequestBody WalletDto walletDto) {
		String result = null;
		try {
			result = walletService.addWalletToUser(walletDto);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public ResponseEntity<Object> depositAmount(@RequestBody WalletDto walletDto) {
		String result = null;
		try {
			result = walletService.depositMoney(walletDto);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/withdrawamount", method = RequestMethod.POST)
	public ResponseEntity<Object> withdrawMoney(@RequestBody WalletDto walletDto) {
		String result = null;
		try {
			result = walletService.withdrawMoney(walletDto);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);

	}

}
