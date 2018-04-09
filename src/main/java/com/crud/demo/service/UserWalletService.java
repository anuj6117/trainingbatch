package com.crud.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crud.demo.dto.UserWalletDTO;
import com.crud.demo.enums.WalletType;
import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.User;
import com.crud.demo.model.UserWallet;

@Service
public class UserWalletService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWalletService.class);
	
	@Autowired
	private UserWalletJpaRepository userWalletJpaRepository;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;
	
	 Boolean walletTypeExistInEnumOrNot;//default
	  Boolean walletTypeAlreadyAddedOrNot;//default

	public Map<String, Object> createWallet(UserWalletDTO userWalletDTO) throws InterruptedException {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		User user=userJpaRepository.findOne(userWalletDTO.getUserId());
		
		Runnable runnableThread1=new Runnable(){
			@Override
			public void run() {
				 walletTypeExistInEnumOrNot=isPresentAdminDatabase(userWalletDTO.getWalletType());
				
			}};
			Runnable runnableThread2=new Runnable(){
				@Override
				public void run() {
					 walletTypeAlreadyAddedOrNot=walletTypeAlreadyAddedOrNot(userWalletDTO.getWalletType(),user.getUserWallet());
					
				}};
				
				Thread t1=new Thread(runnableThread1);
				Thread t2=new Thread(runnableThread2);
				t1.start();
				t1.join();
				t2.start();
			    t2.join();
		if((walletTypeExistInEnumOrNot) && (!walletTypeAlreadyAddedOrNot))
		{
			Set<UserWallet> setOfUserWallets=new HashSet<>();
			UserWallet userWallet=new UserWallet();
			userWallet.setWalletType(userWalletDTO.getWalletType());
			setOfUserWallets.add(userWallet);
			user.setUserWallet(setOfUserWallets);
			userWallet.setUser(user);//reverse association
			userJpaRepository.save(user);
			
			map.put("Result", "wallet successfully assigned or added");
			map.put("isSuccess", true);
		}
		else
		{
			map.put("Result", "wallet already assigned or user doesnot exist or wallettype not present");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service ::::::::::::::::wallet already assigned or user doesnot exist");	
		}
		
			
		return map;
	}
	/*******************************************************************************************/
	private Boolean walletTypeAlreadyAddedOrNot(String walletType, Set<UserWallet> userWallet) {
		Boolean walletTypeAlreadyAddedOrNot=false;
		
		for(UserWallet existingUserWallet:userWallet)
		{	LOGGER.info("walletTypeAlreadyAddedOrNot:::::::::"+existingUserWallet.getWalletType());
		  if(walletType.equalsIgnoreCase(existingUserWallet.getWalletType()))
		   { walletTypeAlreadyAddedOrNot=true;
			     break;
		   }
		  else
		  {
			  walletTypeAlreadyAddedOrNot=false;
		  }
		
		}
		LOGGER.info("inside wallet thread db type::::::::{}",walletTypeAlreadyAddedOrNot);
		return walletTypeAlreadyAddedOrNot;
	}

	
	/*******************************************************************************************/
	public Boolean isPresentAdminDatabase(String walletType)
	{ Boolean flagExistOrNot=false;
	Set<String> coinNamesList= coinManagementJpaRepository.findByCoinName();
		for (String walletTypeInEnum:coinNamesList)
		{ LOGGER.info("walletTypeInEnum:::::::::;{}"+walletTypeInEnum.toString());
			if(walletType.equals(walletTypeInEnum))
			{
				flagExistOrNot=true;
				break;
			}
			
		}
		LOGGER.info("inside wallet thread enum type{}",flagExistOrNot);
		return flagExistOrNot;
	}
	
	/*public Boolean isPresentInEnum(String walletType)
	{ Boolean flagExistOrNot=false;
		for (WalletType walletTypeInEnum:WalletType.values())
		{ LOGGER.info("walletTypeInEnum:::::::::;{}"+walletTypeInEnum.toString());
			if(walletType.equals(walletTypeInEnum.toString()))
			{
				flagExistOrNot=true;
				break;
			}
			
		}
		LOGGER.info("inside wallet thread enum type",flagExistOrNot);
		return flagExistOrNot;
	}*/

	/****************************************************************************************************/
	public Map<String, Object> withdrawAmount(UserWalletDTO userWalletDTO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		Integer flagWalletType = 0;
		UserWallet existingUserWallet = null;
		User user = userJpaRepository.findOne(userWalletDTO.getUserId());// to get all wallets of user
		LOGGER.info("Message on service ::::::::::::::::before flag=1.{}",user);
		for (UserWallet testUserWallet : user.getUserWallet())// to check coming wallet exist or not
		{
			if (userWalletDTO.getWalletType().equals(testUserWallet.getWalletType())) {
				flagWalletType = 1;
				existingUserWallet = userWalletJpaRepository.findByWalletIdAndWalletType(testUserWallet.getWalletId(),testUserWallet.getWalletType());
			}
		}
		LOGGER.info("Message on service ::::::::::::::::after flag=1.{}",existingUserWallet);
		
		  if ((flagWalletType == 1) && (existingUserWallet.getBalance() >= userWalletDTO.getAmount())) 
		  {
			LOGGER.info("Message on service ::::::::::::::::inside if");
			Integer remainingBalance = existingUserWallet.getBalance() - userWalletDTO.getAmount();
			existingUserWallet.setBalance(remainingBalance);
			existingUserWallet.setShadowBalance(remainingBalance);
			userWalletJpaRepository.save(existingUserWallet);
			map.put("Result", "Amount wihdraw successfully");
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::Amount wihdraw successfully");
		} 
		  else {
			map.put("Result", "Unsuccessfull to wihdraw amount");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service ::::::::::::::::Unsuccessfull to wihdraw amount");
		}

		return map;
	}

	/****************************************************************************************************/
	public Map<String, Object> depositAmount(UserWalletDTO userWalletDTO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		Integer flagWalletType = 0;
		UserWallet existingUserWallet = null;
		User user = userJpaRepository.findOne(userWalletDTO.getUserId());// to get all wallets of user
		for (UserWallet testUserWallet : user.getUserWallet())// to check coming wallet exist or not
		{
			if (userWalletDTO.getWalletType().equals(testUserWallet.getWalletType())) {
				flagWalletType = 1;
				existingUserWallet = userWalletJpaRepository.findByWalletIdAndWalletType(testUserWallet.getWalletId(),testUserWallet.getWalletType());
			}
		}
		System.out.println("::::::::::" + existingUserWallet);
		if ((existingUserWallet != null) && (flagWalletType == 1)) {
			Integer newBalance = existingUserWallet.getBalance() + userWalletDTO.getAmount();
			existingUserWallet.setBalance(newBalance);
			existingUserWallet.setShadowBalance(newBalance+existingUserWallet.getShadowBalance());
			userWalletJpaRepository.save(existingUserWallet);
			map.put("Result", "Amount saved successfully");
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::Amount saved successfully");
		} else {
			map.put("Result", "Unsuccessfull to deposit amount");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service ::::::::::::::::Unsuccessfull to deposit amount");
		}
		return map;
	}
}
