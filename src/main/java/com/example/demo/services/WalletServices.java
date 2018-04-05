package com.example.demo.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.userDTO.WalletDTO;
import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.CoinManagementRepository;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.WalletRepostiory;

@Service
public class WalletServices {

	@Autowired
	WalletRepostiory walletData;
@Autowired 
CoinManagementRepository coinDate;
	@Autowired
	UserRepository userData;

	public String addWalletToUser(WalletDTO data) {
		long leftLimit = 1L;
		long rightLimit = 10000000000L;
		boolean check = true;
		long randemId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		CoinManagementModel model = coinDate.findByCoinName(data.getWalletType());
		if (model == null)
			throw new NullPointerException("coin not available");
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
			walletModel.setAmount(data.getAmount());
			walletModel.setWalletType(data.getWalletType());
			walletModel.setShadoBalance(data.getAmount());
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
	public String AddMoneyInWallet(WalletDTO data) {
		WalletModel model =giveWallettype(data);
		if (model == null)
			throw new NullPointerException("id or wallet not correct");
		int walletAmount = model.getAmount();
		walletAmount = walletAmount + data.getAmount();
		model.setAmount(walletAmount);
		model.setShadoBalance(walletAmount);
		WalletModel result = walletData.save(model);
		if (result != null)
			return "success";
		return "error";
	}

	// ---------------------
	public String withdrawMoneyInWallet(WalletDTO data) {
		WalletModel model = giveWallettype(data);
		if (model == null)
			throw new NullPointerException("id or wallet type not found");
		int walletAmount = model.getAmount();
		walletAmount = walletAmount - data.getAmount();
		if(walletAmount<=0)
			throw new RuntimeException("you dont hava enough amount");
		model.setAmount(walletAmount);
		model.setShadoBalance(walletAmount);
		WalletModel result = walletData.save(model);
		if (result != null)
			return "success";
		return "error";
	}
	public WalletModel giveWallettype(WalletDTO data)
	{
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		List<WalletModel> walletTypeOfuser = userModel.getWalletModel();
		for (WalletModel getwallrttype : walletTypeOfuser) {
			if (data.getWalletType().equals(getwallrttype.getWalletType())) 
				return getwallrttype;
		}
		return null;
	}
}



//public String AddMoneyInWallet(WalletDTO data) {
//	WalletModel model = walletData.findOne(data.getWalletId());
//	if (model == null)
//		throw new NullPointerException("id not found");
//	int walletAmount = model.getAmount();
//	walletAmount = walletAmount + data.getAmount();
//	model.setAmount(walletAmount);
//	model.setShadoBalance(walletAmount);
//	WalletModel result = walletData.save(model);
//	if (result != null)
//		return "success";
//	return "error";
//}
