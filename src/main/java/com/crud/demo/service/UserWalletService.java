package com.crud.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crud.demo.dto.UserWalletDTO;
import com.crud.demo.enums.WalletType;
import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.jpaRepositories.TransactionJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.Transaction;
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
	@Autowired
	private TransactionJpaRepository transactionJpaRepository;
	
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
	
	/***********************to fetch user wallet deposit history****************/
	public Map<String, Object> walletHistory(UserWalletDTO userWalletDTO) {
		LOGGER.info("Message on service ::::::::::::::::History service hit");
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		User user = userJpaRepository.findOne(userWalletDTO.getUserId());// to get all wallets of user
		Set<UserWallet> userExistingAllWallets=user.getUserWallet();
		Set<String> userExistingAllWalletTypes=new HashSet<>();
		for(UserWallet u:userExistingAllWallets)
		{
			userExistingAllWalletTypes.add(u.getWalletType());
		}
		LOGGER.info("Message on service ::::::::::::::::History service before if");
		if(userExistingAllWalletTypes.contains(userWalletDTO.getWalletType()))
		{  
			LOGGER.info("Message on service ::::::::::::::::History service if part");
			List<Transaction> listWalletTransaction=transactionJpaRepository.findByBuyerId(userWalletDTO.getUserId());
			/*List<Transaction> fileredListWalletTransaction=new ArrayList<>();
			for(Transaction t:listWalletTransaction)
			{
				Transaction t=new Transaction();
			}*/
			map.put("Result",listWalletTransaction);
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::History fetched successfully");
		}
		else
		{
			LOGGER.info("Message on service ::::::::::::::::History service else part");
			map.put("Result", "No list found or no wallet type exist");
			map.put("isSuccess", isSuccess);
			LOGGER.info("Message on service ::::::::::::::::No list found or no wallet type exist");
		}
		return map;
	}
	
	/*************************************to approve or not************************************************/
	public Map<String, Object> approveWalletTransactionOrNot(Transaction transaction) {
		LOGGER.info("Message on service ::::::::::::::::approveWalletTransactionOrNot hit");
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		/*User user = userJpaRepository.findOne(transactionId);*/// to get all wallets of user
		Transaction existingTransaction=transactionJpaRepository.findOne(transaction.getTransactionId());;
		User user=null;
		UserWallet userWallet=null;
		user=userJpaRepository.findOne(existingTransaction.getBuyerId());
		for(UserWallet usWallet:user.getUserWallet())
		{
			if(("fiate").equals(usWallet.getWalletType()))
			{
				userWallet=usWallet;
				break;
			}
		}
		LOGGER.info("Message on service ::::::::::::::::before if{}::::::",transaction.getStatus());
		if(("approved").equalsIgnoreCase(transaction.getStatus())&&("pending").equalsIgnoreCase(existingTransaction.getStatus()))
		{
			LOGGER.info("Message on service ::::::::::::::::if part");
			existingTransaction.setStatus(transaction.getStatus());
			userWallet.setBalance(userWallet.getBalance()+existingTransaction.getNetAmount());
			userWallet.setShadowBalance(userWallet.getShadowBalance()+existingTransaction.getNetAmount());
			map.put("Result","Amount saved successfully");
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::Amount saved successfully");
		}
		else if((("cancel").equalsIgnoreCase(transaction.getStatus())||("disapproved").equalsIgnoreCase(transaction.getStatus()))&&(!("approved").equalsIgnoreCase(existingTransaction.getStatus())))
		{
			LOGGER.info("Message on service ::::::::::::::::else part");
			existingTransaction.setStatus(transaction.getStatus());
			map.put("Result","transaction cancelled or disapproved");
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::transaction cancelled or disapproved");
		}
		else
		{
			map.put("Result","status value inappropriate");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service ::::::::::::::::status value inappropriate");
		}
		userWalletJpaRepository.save(userWallet);
		transactionJpaRepository.save(existingTransaction);
		return map;
	}
	/*******************************************************************************/
	public Map<String, Object> depositAmountRequest(UserWalletDTO userWalletDTO) {
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
		if ((existingUserWallet != null) && (flagWalletType == 1)&&userWalletDTO.getAmount()>0) {
			/*Integer newBalance = existingUserWallet.getBalance() + userWalletDTO.getAmount();*/
			Transaction transaction=new Transaction();
			transaction.setBuyerId(user.getUserId());
			transaction.setNetAmount(userWalletDTO.getAmount());
			transaction.setStatus("pending");
			transaction.setDescription("Deposit transaction");
			transaction.setTransactionCreatedOn(new Date());
			transactionJpaRepository.save(transaction);
			/*existingUserWallet.setBalance(newBalance);
			existingUserWallet.setShadowBalance(newBalance+existingUserWallet.getShadowBalance());
			userWalletJpaRepository.save(existingUserWallet);*/
			map.put("Result", "Pending transaction placed successfully");
			map.put("isSuccess", true);
			LOGGER.info("Message on service ::::::::::::::::Pending transaction placed successfully");
		} 
		else {
			map.put("Result", "Unable to place transaction");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service ::::::::::::::::Unable to place transaction");
		}
		return map;
	}
	
}
