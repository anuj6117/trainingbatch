package com.example.demo.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.INRDepositDTO;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.service.TransactionService;
import com.example.demo.service.WalletService;
import com.example.demo.utils.ApiResponse;
import com.example.demo.validation.PasswordValidation;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@RequestBody WalletModel walletModel) {
		walletService.addWallet(walletModel);
	}

	@RequestMapping(value = "/withdraw/{id}/{amount}", method = RequestMethod.POST)
	public void withdraw(@PathVariable(value = "id") int walletId, @PathVariable(value = "amount") float amount) {
		walletService.withdrawWallet(walletId, amount);
	}

	@RequestMapping(value = "/depositinranoumt", method = RequestMethod.POST)
	public ResponseEntity<Object> deposit(@RequestBody INRDepositDTO inrDepositDTO) {
	 Object response;
		try {
			response = transactionService.createTransactionForINRDeposit(inrDepositDTO);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);
	}
	@RequestMapping(value = "/approveinrrequest", method = RequestMethod.POST)
	public ResponseEntity<Object> approveINRRequest(@RequestBody INRDepositDTO inrDepositDTO) {
		 Object response;
			try {
				response = walletService.addAmountIntoWallet1(inrDepositDTO);
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);
		
	}

}
