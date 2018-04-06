package com.example.demo.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.userDTO.WalletDTO;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.services.WalletServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class WalletController {

	@Autowired
	WalletServices walletData;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	private ResponseEntity<Object> addWallet(@RequestBody WalletDTO data) {
		String result = null;
		try {
			result = walletData.addWalletToUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public ResponseEntity<Object> addMoneyInWallet(@RequestBody WalletDTO data) {
		WalletModel result = null;
		try {
			result = walletData.AddMoneyInWallet(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/withdrawamount", method = RequestMethod.POST)
	public ResponseEntity<Object> withdrawMoneyInWallet(@RequestBody WalletDTO data) {
		WalletModel result = null;
		try {
			result = walletData.withdrawMoneyInWallet(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}
	
	
}
