package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.WalletModel;
import com.example.demo.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@RequestBody WalletModel walletModel) {
		walletService.addWallet(walletModel);
	}

	@RequestMapping(value = "/withdraw/{id}/{amount}", method = RequestMethod.POST)
	public void withdraw(@PathVariable(value = "id") int walletId, @PathVariable(value = "amount") float amount) {
		walletService.withdrawWallet(walletId, amount);
	}

	@RequestMapping(value = "/deposit/{id}/{amount}", method = RequestMethod.POST)
	public void deposit(@PathVariable(value = "id") int walletId, @PathVariable(value = "amount") float amount) {
		walletService.depositWallet(walletId, amount);
	}

}
