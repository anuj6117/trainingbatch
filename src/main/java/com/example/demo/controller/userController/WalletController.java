package com.example.demo.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.WalletModel;
import com.example.demo.services.WalletServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class WalletController {

	@Autowired
	WalletServices walletData;

	@RequestMapping(value = "/addWallet", method = RequestMethod.POST)
	private ResponseEntity<Object> addWallet(@RequestBody WalletModel data) {
		String result = null;
		try {
			result = walletData.addWalletToUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/addMoneyInWallet", method = RequestMethod.POST)
	public ResponseEntity<Object> addMoneyInWallet(@RequestBody WalletModel data) {
		String result = null;
		try {
			result = walletData.AddMoneyInWallet(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/withdrawMoneyInWallet", method = RequestMethod.POST)
	public ResponseEntity<Object> withdrawMoneyInWallet(@RequestBody WalletModel data) {
		String result = null;
		try {
			result = walletData.withdrawMoneyInWallet(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}
}
