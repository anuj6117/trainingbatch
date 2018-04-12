package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.INRDepositDTO;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.validation.PasswordValidation;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepo;
	@Autowired
	private UserRepository userRepo;

	private static int id;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	
	public Object addAmountIntoWallet1(INRDepositDTO inrdeposit)throws Exception {
		WalletModel wallet=new WalletModel();
		Integer flag=0;
		Optional<UserModel> userModel = userRepo.findById(inrdeposit.getUserId());
		logger.info(userModel.get().getUserName()+"-------");
		Set<WalletModel> walletModel =  userModel.get().getUserWallet();
		if(walletModel!=null) {
			logger.info(walletModel+"-----------empty---------------------");
		}
		for(WalletModel type:walletModel) {
			logger.info(type.getWalletType()+"wallet------------------");
			if(type.getWalletType().equalsIgnoreCase(inrdeposit.getWalletType())) {
				flag=1;
				logger.info(type.getWalletType()+"wallet------------------");
				wallet = type;
				break;
			}
		}
		if(flag==1) {
			wallet.setBalance(wallet.getBalance() + inrdeposit.getAmount());
			wallet.setShadowBalance(wallet.getBalance());
			walletRepo.save(wallet);
			
		}
		else {
			throw new Exception("Wallet does not exist---");
		}
		
		return true;
	}
	

}
