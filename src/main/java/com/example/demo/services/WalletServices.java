package com.example.demo.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.WalletRepostiory;

@Service
public class WalletServices {

	@Autowired
	WalletRepostiory walletData;

	@Autowired
	UserRepository userData;

	public String addWalletToUser(WalletModel data) {
		long leftLimit = 1L;
		long rightLimit = 10000000000L;
		boolean check = true;
		long randemId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		UserModel userModel = userData.findOne(data.getId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		List<WalletModel> walletTypeOfuser = userModel.getWalletModel();
		for (WalletModel getwallrttype : walletTypeOfuser) {
			if (data.getWalletType().equals(getwallrttype.getWalletType())) {
				check = false;
				throw new RuntimeException("wallet allready exist");
				// break;
			}
		}
		if (check) {
			WalletModel walletModel = new WalletModel();
			walletModel.setBalance(data.getBalance());
			walletModel.setWalletType(data.getWalletType());
			walletModel.setShadoBalance(data.getBalance());
			walletModel.setUserdata(userModel);
			walletModel.setRandemId(randemId);
			userModel.getWalletModel().add(walletModel);
			UserModel result = userData.save(userModel);
			if (result != null)
				return "success";
		}
		return "error";
	}

	// ---------------------
	public String AddMoneyInWallet(WalletModel data) {
		WalletModel model = walletData.findOne(data.getId());
		if (model == null)
			throw new NullPointerException("id not found");
		int walletBalance = model.getBalance();
		walletBalance = walletBalance + data.getBalance();
		model.setBalance(walletBalance);
		model.setShadoBalance(walletBalance);
		WalletModel result = walletData.save(model);
		if (result != null)
			return "success";
		return "error";
	}

	// ---------------------
	public String withdrawMoneyInWallet(WalletModel data) {
		WalletModel model = walletData.findOne(data.getId());
		if (model == null)
			throw new NullPointerException("id not found");
		int walletBalance = model.getBalance();
		walletBalance = walletBalance - data.getBalance();
		model.setBalance(walletBalance);
		model.setShadoBalance(walletBalance);
		WalletModel result = walletData.save(model);
		if (result != null)
			return "success";
		return "error";
	}
}
