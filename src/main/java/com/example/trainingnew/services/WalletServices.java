package com.example.trainingnew.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.trainingnew.DTO.UserWalletDTO;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.model.Walletmodel;
import com.example.trainingnew.reprository.CoinRepo;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.reprository.WalletRepo;


@Service
public class WalletServices {
	
	public static final Logger logger = LoggerFactory.getLogger(WalletServices.class);
	
	@Autowired
	WalletRepo walletrepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CoinRepo coinRepo; 
	
	Boolean flag=true;
	
	public Walletmodel createWallet(UserWalletDTO wmodel) {
		
		
		
		UserModel umodel=userRepo.findByUserId(wmodel.getUserId());
		if(umodel==null)
		{
			throw new NullPointerException("Invalid User Id!");
		}
		String newWalletType=wmodel.getWalletType();
		
		List<Walletmodel> listmodel=umodel.getWallets();
		
		Walletmodel updatedWalletModel = new Walletmodel();
		
		for(Walletmodel walletModel:listmodel) {
			logger.error(newWalletType+"  Getting Type from user");
			logger.error(walletModel.getWalletType()+"  Getting type from DB");
			
			if(newWalletType.equals(walletModel.getWalletType())) {
				throw new UsernameNotFoundException("This Wallet type Already Exist");
			}
			
				updatedWalletModel.setWalletType(wmodel.getWalletType());
				updatedWalletModel.setUserModel(umodel);
		}
		
		List<Coinmodel> allCoinsList=coinRepo.findAll();
		
		for(Coinmodel allCoins:allCoinsList) {
			if(allCoins.getCoinName().equals(wmodel.getWalletType())) {
				flag=false;
				break;
			}
		}
		if(flag)
			throw new NullPointerException("This walletType Doesn't exist;");
		

		
		return  walletrepo.save(updatedWalletModel);
	}
	
	//---------------------------------------------------------depositamount
	public Walletmodel depositwallet(UserWalletDTO wallet){
		
		String newWalletType=wallet.getWalletType();
		UserModel userData=userRepo.findOneByUserId(wallet.getUserId());
		Walletmodel mod =null;
		List<Walletmodel> listmodel=userData.getWallets();
		boolean flag=false;
		for(Walletmodel model:listmodel) {
			
			logger.error("Coming in model "+model.getWalletType());
			if(model.getWalletType().equals(newWalletType)) {
				flag=true;
				Double newBalance=wallet.getBalance()+ model.getBalance();
				Double newShadowbal=newBalance;
				model.setBalance(newBalance);
				model.setShadowBalance(newShadowbal);
//				mod=walletrepo.save(model);
				return walletrepo.save(model);
			}
		}
			if(flag==false) {
				 throw new NullPointerException("This Wallet Type Doesn't exist");
			}
		return null;
	}

	//---------------------------------------------------------
	public Walletmodel withdrawallet(UserWalletDTO wallet) {
		
		String newWalletType=wallet.getWalletType();
		UserModel userData=userRepo.findOneByUserId(wallet.getUserId());
		Walletmodel mod =null;
		List<Walletmodel> listmodel=userData.getWallets();
		boolean flag=false;
		
		Double withdrawAmount=wallet.getBalance();
		Double totalAmount;
		for(Walletmodel model:listmodel) {
			totalAmount=model.getBalance();
			logger.error("Cursor is in model "+model.getWalletType());
			if(model.getWalletType().equals(newWalletType)) {
				
				if(totalAmount>withdrawAmount) 
				{
				flag=true;
				Double newBalnce=totalAmount-withdrawAmount;
				Double newShadowBalance=newBalnce;
				model.setBalance(newBalnce);
				model.setShadowBalance(newShadowBalance);
				mod= walletrepo.save(model);
				}
				else {
					 throw new NullPointerException("Oops! Don't have enought money");
				}
			}
		}
			if(flag==false) {
				 throw new NullPointerException("This Wallet Type Doesn't exist");
			}
		return mod;
	}


}
