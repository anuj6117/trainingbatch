/*package com.crud.demo.service;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.jpaRepositories.OrderJpaRepository;
import com.crud.demo.jpaRepositories.TransactionJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.CoinManagement;
import com.crud.demo.model.Orders;
import com.crud.demo.model.Transaction;
import com.crud.demo.model.User;
import com.crud.demo.model.UserWallet;

@Service
public class NewTransactionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NewTransactionService.class);
	@Autowired
	private TransactionJpaRepository transactionJpaRepository;
	@Autowired
	private OrderJpaRepository orderJpaRepository;
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;
	@Autowired
	private UserWalletJpaRepository userWalletJpaRepository;
	
	
	
	
	
	public Map<String, Object> approvetransaction() throws CloneNotSupportedException {
		Boolean isSuccess = false;
		Map<String, Object> map = new HashMap<>();
		Boolean buyordersflag = false;
		Boolean sellordersflag = false;
		List<Orders> buyorders = orderJpaRepository.findAllByStatusAndOrderType("pending", "buy");
		List<Orders> sellorders = orderJpaRepository.findAllByStatusAndOrderType("pending", "sell");
*//********* sorting buy order list based on its date***********************************************//*
		if (!buyorders.isEmpty()) {
			buyordersflag = true;
			Collections.sort(sellorders, new Comparator<Orders>() {
				@Override
				public int compare(Orders o1, Orders o2) {
					return o1.getOrderCreatedOn().compareTo(o2.getOrderCreatedOn());
				}
			});

		}

*//********* sorting sell order list based on its quotevalue****************************************//*
		if (!sellorders.isEmpty()) {
			sellordersflag = true;
			Collections.sort(sellorders, new Comparator<Orders>() {
				@Override
				public int compare(Orders o1, Orders o2) {
					return Integer.compare(o1.getQuoteValue(), o2.getQuoteValue());
				}
			});//
		
		}
*//******************************* Admin currency Object List****************************//*
		Set<String> coinSetAvailableInAdmin = coinManagementJpaRepository.findByCoinName();
*//*****case1*****//*		
		if(buyordersflag && sellordersflag )
		{ 
			for (Orders orderBuy : buyorders)// buyers loop
			{
				for (Orders orderSell : sellorders)// sellers loop
				{  Orders cloneBuyOrders=(Orders)orderBuy.clone();
				   Orders cloneSellOrders=(Orders)orderSell.clone();
				   Integer sellOutQuantity = 0;
					Integer buyOrderQuantity = cloneBuyOrders.getCoinQuantity();
				   if (cloneBuyOrders.getCoinName().equals(cloneSellOrders.getCoinName())) 
					{
					   CoinManagement coinManagement = coinManagementJpaRepository.findByCoinName(cloneBuyOrders.getCoinName());
					   CoinManagement cloneCoinManagement=(CoinManagement)coinManagement.clone();
					   if ((cloneBuyOrders.getQuoteValue() >= cloneSellOrders.getQuoteValue())&& (cloneSellOrders.getQuoteValue() < coinManagement.getPrice())) 
						{   
						   if(cloneBuyOrders.getCoinQuantity()<=cloneSellOrders.getCoinQuantity())
						   {
							   Integer fee=(cloneBuyOrders.getCoinQuantity()*cloneBuyOrders.getQuoteValue())*2/100;
						   UpdateInBuyerUserWallet();
						   UpdateInSellerUserWallet();
						   UpdateInOrder();
						   UpdateInCoinManagement(coinManagement,fee);
						   UpdateInTransaction();
						   }
						   else
						   {
							   
						   }
						   
						}
					   else
					   {
						      autmaticUpdateInBuyerUserWallet(cloneBuyOrders,cloneCoinManagement);
						      autmaticUpdateInOrder(orderBuy,coinManagement);
						      autmaticUpdateInCoinManagement(coinManagement,sellOutQuantity,buyOrderQuantity);
		                      Transaction newTransaction=autmaticUpdateInTransaction(cloneBuyOrders,cloneCoinManagement);
		                      map.put("Result", newTransaction);
		   					  map.put("isSuccess", isSuccess);
		   					  LOGGER.info("Transaction placed successfully");
					   }
					}	
				}
			}
		}
*//*****case2*****//*		
		else if(buyordersflag)
		{ System.out.println(":::::::::::::::::::::else-if:::::::::::::::::::::::::::::::");
			for (Orders orderBuy: buyorders)// buyers loop
			{
				Integer sellOutQuantity = 0;
				Integer buyOrderQuantity = orderBuy.getCoinQuantity();
				  if (coinSetAvailableInAdmin.contains(orderBuy.getCoinName())) 
				  { 
					  CoinManagement coinManagement = coinManagementJpaRepository.findByCoinName(orderBuy.getCoinName());
					  Object cloneCoinManagement=(Object)coinManagement.clone();
					  Orders cloneOrders=(Orders)orderBuy.clone();
					  autmaticUpdateInBuyerUserWallet(cloneOrders,cloneCoinManagement);
				      autmaticUpdateInOrder(orderBuy,cloneCoinManagement);
				      autmaticUpdateInCoinManagement(coinManagement,sellOutQuantity,buyOrderQuantity);
                      Transaction newTransaction=autmaticUpdateInTransaction(cloneOrders,cloneCoinManagement);
                      map.put("Result", newTransaction);
   					  map.put("isSuccess", isSuccess);
   					  LOGGER.info("Transaction placed successfully");
				  }
			}	
		}
		else
		{
			map.put("Result", "Buyer is not available or seller not available even admin");
			map.put("isSuccess", isSuccess);
		}
		
	
		return map;}

	
*//**********************************Common functions***********************************************//*	
	*//********************************order update************************************//*
	public Orders autmaticUpdateInOrder(Orders orderBuy,Object  coinManagementObject)
	{   
		CoinManagement coinManagement=(CoinManagement)coinManagementObject;
		Orders updatedOrders=new Orders();
	*//******* for order if-else ********//*
	LOGGER.info("Order table entry updation starts at last");
	if (coinManagement.getInitialSupply() >= orderBuy.getCoinQuantity()) {

		Integer remainingQuantityNeed = 0;
		orderBuy.setCoinQuantity(remainingQuantityNeed);
		orderBuy.setStatus("done");
		orderBuy.setFee(0);
		orderBuy.setGrossAmount(0);
		orderJpaRepository.save(orderBuy);
	} else {
		Integer remainingQuantityNeed = orderBuy.getCoinQuantity()-coinManagement.getInitialSupply();
		
		System.out.println(
				"remainingQuantityNeed::::::::in orders else part:::::::" + remainingQuantityNeed);
		orderBuy.setCoinQuantity(remainingQuantityNeed);
		Integer updatedTradingAmount = remainingQuantityNeed * orderBuy.getQuoteValue();
		Integer updatedFee = (updatedTradingAmount * 2) / 100;
		orderBuy.setFee(updatedFee);
		Integer updatedGrossAmount = updatedTradingAmount + updatedFee;
		orderBuy.setGrossAmount(updatedGrossAmount);
		orderJpaRepository.save(orderBuy);
	}

	
		return updatedOrders;
	}
	*//*******************************Coin or Admin Update*************************************//*
	public CoinManagement autmaticUpdateInCoinManagement(CoinManagement coinManagement,Integer sellOutQuantity,Integer buyOrderQuantity)
	{   CoinManagement updatedCoinManagement=coinManagement;
	    LOGGER.info("Coin management updation starts");
	   System.out.println(":::coinManagement.getInitialSupply()::" + updatedCoinManagement.getInitialSupply());
	   System.out.println(":::orderbuy.getCoinQuantity()::" + buyOrderQuantity);
	if (updatedCoinManagement.getInitialSupply() >= buyOrderQuantity)
	   {
		sellOutQuantity = buyOrderQuantity;
		System.out.println("::::::if-part::::coinmanagement::" + sellOutQuantity);
		Integer initialSupply = updatedCoinManagement.getInitialSupply() - buyOrderQuantity;
		updatedCoinManagement.setInitialSupply(initialSupply);
		Integer fee = (buyOrderQuantity * updatedCoinManagement.getPrice() * 2) / 100;
		Integer updatedProfit = updatedCoinManagement.getProfit() + fee;
		updatedCoinManagement.setProfit(updatedProfit);
		Integer inrConvergentOfDemandingCoin = buyOrderQuantity * updatedCoinManagement.getPrice();
		updatedCoinManagement.setINRConvergent(inrConvergentOfDemandingCoin+updatedCoinManagement.getINRConvergent());
		coinManagementJpaRepository.save(updatedCoinManagement);
		LOGGER.info("Coin management updated or end");

	} else {
		sellOutQuantity = updatedCoinManagement.getInitialSupply();
		System.out.println(
				"sellOutQuantity::::::::in coinmanagement else part:::::::" + sellOutQuantity);
		Integer remainingCoinNeed = buyOrderQuantity - updatedCoinManagement.getInitialSupply();
		Integer updatedFee = (((buyOrderQuantity - remainingCoinNeed) * updatedCoinManagement.getPrice())
				* 2) / 100;
		updatedCoinManagement.setInitialSupply(0);
		Integer updatedProfit = updatedCoinManagement.getProfit() + updatedFee;
		updatedCoinManagement.setProfit(updatedProfit);
		Integer inrConvergentOfDemandingCoin = (buyOrderQuantity - remainingCoinNeed)
				* updatedCoinManagement.getPrice();
		updatedCoinManagement.setINRConvergent(inrConvergentOfDemandingCoin+updatedCoinManagement.getINRConvergent());
		coinManagementJpaRepository.save(updatedCoinManagement);
		LOGGER.info("Coin management updated or end");

	}
		return updatedCoinManagement;
	}
	*//*******************************Buyer Wallet Update*************************************//*
	public UserWallet autmaticUpdateInBuyerUserWallet(Orders orderBuy,Object  coinManagementObject)
	{   
		
		CoinManagement coinManagement=(CoinManagement)coinManagementObject;
	
		UserWallet updatedBuyerUserWallet=new UserWallet();
		if (orderBuy.getQuoteValue() >= coinManagement.getPrice()
				&& (coinManagement.getInitialSupply() > 0)) {
			LOGGER.info("user-wallet entry updation starts ");
			User user = orderBuy.getUser();
			Set<UserWallet> userWallets = user.getUserWallet();
			UserWallet userWalletFiate = null;
			UserWallet userWalletNew = null;
			for (UserWallet uw : userWallets) {
				if (("fiate").equals(uw.getWalletType())) {
					userWalletFiate = uw;
				} else if (orderBuy.getCoinName().equals(uw.getWalletType())) {
					userWalletNew = uw;
				}

			}
			*//******* for user-wallet if-else ********//*

		LOGGER.info("user-wallet entry updation starts{} ", userWalletFiate);
		if (coinManagement.getInitialSupply() >= orderBuy.getCoinQuantity()
				&& (coinManagement.getInitialSupply() > 0)) {

			userWalletFiate.setBalance(userWalletFiate.getShadowBalance());
			userWalletNew.setBalance(orderBuy.getCoinQuantity()+userWalletNew.getBalance());
			userWalletNew.setShadowBalance(userWalletNew.getShadowBalance() + orderBuy.getCoinQuantity());
			userWalletJpaRepository.save(userWalletFiate);
			userWalletJpaRepository.save(userWalletNew);

		} else {  sellOutQuantity=coinManagement.getInitialSupply(); 
			Integer remainingQuantityNeed = orderBuy.getCoinQuantity()
					- coinManagement.getInitialSupply();
			System.out.println(":::::::coinManagement.getInitialSupply()::::::::::::"
					+ coinManagement.getInitialSupply());
			Integer updatedtransactionAmount = coinManagement.getInitialSupply()
					* orderBuy.getQuoteValue();
			Integer fee = (updatedtransactionAmount * 2) / 100;

			Integer updatedBalance = userWalletFiate.getBalance() - (updatedtransactionAmount + fee);
			userWalletFiate.setBalance(updatedBalance);
			Integer shadowBalance = remainingQuantityNeed * orderBuy.getQuoteValue();
			Integer updatedFee = (shadowBalance * 2) / 100;
			Integer updatedShadowBalance = updatedBalance - (shadowBalance + updatedFee);
			userWalletFiate.setShadowBalance(updatedShadowBalance);
			
			userWalletNew.setBalance(coinManagement.getInitialSupply()+userWalletNew.getBalance());
			System.out.println(":::::else part:::::"+coinManagement.getInitialSupply());
			userWalletNew.setShadowBalance(userWalletNew.getShadowBalance() + coinManagement.getInitialSupply());
			userWalletJpaRepository.save(userWalletFiate);
			userWalletJpaRepository.save(userWalletNew);
		}}
	
		return updatedBuyerUserWallet;
	}
	*//*******************************Seller Wallet Update*************************************//*
	public UserWallet autmaticUpdateInSellerUserWallet(Orders orderBuy,Orders orderSell)
	{   UserWallet updatedSellerUserWallet=new UserWallet();
	
	User sellerUser = orderSell.getUser();
	Set<UserWallet> sellerUserWallets = sellerUser.getUserWallet();
	UserWallet userSellerWalletFiate = null;
	UserWallet userSellerWalletNew = null;
	for (UserWallet userSellerWallet : sellerUserWallets) {
		if (("fiate").equals(userSellerWallet.getWalletType())) {
			userSellerWalletFiate = userSellerWallet;
		} else if (orderSell.getCoinName().equals(userSellerWallet.getWalletType())) {
			userSellerWalletNew = userSellerWallet;
		}

	}
	
	
		return updatedSellerUserWallet;
	}
	*//*******************************Seller Wallet Update*************************************//*
	public Transaction autmaticUpdateInTransaction(Orders orderBuy,Object  coinManagementObject)
	{  CoinManagement coinManagement=(CoinManagement)coinManagementObject;
	    
	Integer sellOutQuantity=0;
	if(orderBuy.getCoinQuantity()>=coinManagement.getInitialSupply())
	{ System.out.println(":::::::::::::::::"+orderBuy.getCoinQuantity());
	System.out.println(":::::::::::::::::"+coinManagement.getInitialSupply());
		sellOutQuantity=coinManagement.getInitialSupply();
	}
	else
	{
		sellOutQuantity=orderBuy.getCoinQuantity();	
	}
	Transaction transaction = new Transaction();
	transaction.setBuyerId(orderBuy.getOrderId());
	transaction.setCoinQuantity(sellOutQuantity);
	System.out.println("transaction::::sellOutQuantity:::::" + sellOutQuantity);
	transaction.setCointype(orderBuy.getCoinName());
	transaction.setDescription("success fully done");
	transaction.setExchangeRate(200);
	Integer tradingAmount = sellOutQuantity * orderBuy.getQuoteValue();
	Integer fee = (tradingAmount * 2) / 100;
	Integer grossAmount = tradingAmount + fee;
	transaction.setGrossAmount(grossAmount);
	transaction.setNetAmount(grossAmount + 200);
	transaction.setStatus("Executed");
	transaction.setTransactionCreatedOn(new Date());
	Transaction updatedTransaction = transactionJpaRepository.save(transaction);
	
	
		return updatedTransaction;
	}
	*//**********************************user vs buyer*******************************************************//*

}
*/