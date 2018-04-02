package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.WalletModel;
import com.example.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepo;
	private static int id;

	public static int generateId() {
		Random randomNumber = new Random();
		id = randomNumber.nextInt(100);
		return id;
	}

	public void addWallet(WalletModel walletModel) {
		walletModel.setWalletId(generateId());
		walletRepo.save(walletModel);
	}

	public List<WalletModel> withdrawWallet(int walletId, float amount) {
		Optional<WalletModel> walletOp = walletRepo.findById(walletId);
		float balance = walletOp.get().getBalance();
		float newBal = balance - amount;
		walletOp.get().setBalance(newBal);
		walletRepo.save(walletOp.get());
		List<WalletModel> walletDetails = new ArrayList<WalletModel>();
		walletRepo.findAll().forEach(walletDetails::add);
		return walletDetails;
	}

	public List<WalletModel> depositWallet(int walletId, float amount) {
		Optional<WalletModel> walletOp = walletRepo.findById(walletId);
		float balance = walletOp.get().getBalance();
		float newBal = balance + amount;
		walletOp.get().setBalance(newBal);
		walletRepo.save(walletOp.get());
		List<WalletModel> walletDetails = new ArrayList<WalletModel>();
		walletRepo.findAll().forEach(walletDetails::add);
		return walletDetails;
	}

}
