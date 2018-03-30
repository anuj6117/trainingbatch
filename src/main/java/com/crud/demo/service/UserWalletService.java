package com.crud.demo.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.enums.WalletType;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.User;
import com.crud.demo.model.UserWallet;

@Service
public class UserWalletService {
	int counter=0;
	@Autowired
	private UserWalletJpaRepository userWalletJpaRepository;
	@Autowired
	private UserJpaRepository userJpaRepository;
	
	
	
	public String createWallet(User user) 
	{
		User existingUser=userJpaRepository.findOne(user.getUserId());
		
		Set<UserWallet> setOfUserWallet=existingUser.getUserWallet();
		String msg=null;
		
		for (UserWallet userWalletObject:user.getUserWallet())
		{
			
			//wallet existing or not
			for (WalletType wall : WalletType.values()) 
			{  String wallstr=wall.toString();
				 if(wallstr.equalsIgnoreCase(userWalletObject.getWalletType())){
			     setOfUserWallet.add(userWalletObject);
				  counter++;
				  
				 }
				 
				}	
		}
		if(counter>0)
		{
		    existingUser.setUserWallet(setOfUserWallet);
		    existingUser.getUserWallet().forEach(wallet->wallet.setUser(user));//important
		    userJpaRepository.save(existingUser);
		    msg="Your wallet created successfully";
		}
		return msg;
	}

	public String withdrawAmount(UserWallet userWallet) {
UserWallet existingUserWallet=userWalletJpaRepository.findByWalletIdAndWalletType(userWallet.getWalletId(), userWallet.getWalletType());
		if((existingUserWallet!=null) && ( existingUserWallet.getBalance()>=userWallet.getBalance()))
		{
		float remainingBalance=existingUserWallet.getBalance()-userWallet.getBalance();
		existingUserWallet.setBalance(remainingBalance);
		userWalletJpaRepository.save(existingUserWallet);
		return "You have withdrawl successfully"+"Your remaing balance is"+remainingBalance;
		}
		else
		{ return "You don't have sufficient balance to withdraw or this type of wallet doesn't exist";}
	

	}
	public String depositAmount(UserWallet userWallet) {
		UserWallet existingUserWallet=userWalletJpaRepository.findByWalletIdAndWalletType(userWallet.getWalletId(), userWallet.getWalletType());
		System.out.println("::::::::::"+existingUserWallet);
		if(existingUserWallet!=null)
		{
		float newBalance=existingUserWallet.getBalance()+userWallet.getBalance();
		existingUserWallet.setBalance(newBalance);
		userWalletJpaRepository.save(existingUserWallet);
		return "Your amount is successfully added"+"Your updated amount is"+newBalance;
		}
		else
		{return "This wallet does not exist";}	
	}
	}
