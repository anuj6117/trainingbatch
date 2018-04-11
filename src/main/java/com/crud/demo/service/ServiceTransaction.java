package com.crud.demo.service;

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
public class ServiceTransaction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTransaction.class);
	@Autowired
	private TransactionJpaRepository transactionJpaRepository;
	@Autowired
	private OrderJpaRepository orderJpaRepository;
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;
	@Autowired
	private UserWalletJpaRepository userWalletJpaRepository;
	
	public Map<String,Object> approvetransaction() throws CloneNotSupportedException
	{   
		LOGGER.info("transaction approval method hit");
		Boolean isSuccess = false;
		Boolean buyordersflag = false;
	   Boolean sellordersflag = false;
		Map<String, Object> map=new HashMap<>();
		List<Orders> buyorders = orderJpaRepository.findAllByStatusAndOrderType("pending", "buy");
		List<Orders> sellorders = orderJpaRepository.findAllByStatusAndOrderType("pending", "sell");
/********* sorting buy order list based on its date***********************************************/
		if (!buyorders.isEmpty()) {
			buyordersflag = true;
			Collections.sort(sellorders, new Comparator<Orders>() {
				@Override
				public int compare(Orders o1, Orders o2) {
					return o1.getOrderCreatedOn().compareTo(o2.getOrderCreatedOn());
				}
			});

		}

/********* sorting sell order list based on its quotevalue****************************************/
		if (!sellorders.isEmpty()) {
			sellordersflag = true;
			Collections.sort(sellorders, new Comparator<Orders>() {
				@Override
				public int compare(Orders o1, Orders o2) {
					return Integer.compare(o1.getQuoteValue(), o2.getQuoteValue());
				}
			});//
		
		}
/******************************* Admin currency Object List****************************/
		/*Set<String> coinSetAvailableInAdmin = coinManagementJpaRepository.findByCoinName();*/
		
/******************************Now main logic******************************************/		
		if(buyordersflag)//if buyer list not empty
		{ 
			LOGGER.info("Buyers are available");
			for (Orders orderBuy : buyorders)// buyers loop
		     {  
			 Orders cloneBuyOrders=(Orders)orderBuy.clone();
			 if(sellordersflag)//if seller list not empty
			  {
				 LOGGER.info("Sellers are available");
				for (Orders orderSell : sellorders)// sellers loop
				{    User buyUser=orderBuy.getUser();
		    	     User sellUser=orderSell.getUser();
					
				     if(orderBuy.getCoinName().equals(orderSell.getCoinName())&&orderBuy.getQuoteValue()>=orderSell.getQuoteValue()
				    		 &&buyUser.getUserId()!=sellUser.getUserId())
				     {
				    	 LOGGER.info("Buyers and seller match found");
				    	 
				    	 CoinManagement coinManagement = coinManagementJpaRepository.findByCoinName(cloneBuyOrders.getCoinName());
				    	 if(coinManagement!=null&&orderSell.getQuoteValue()<coinManagement.getPrice())
				    	 {//transaction with normal user or seller
				    		 LOGGER.info("Buyers and seller match found and seller price is even lesser then admin");
				    		 String transactionWithAdminOrUser="user";
				    		 transactionWithSeller(cloneBuyOrders,transactionWithAdminOrUser,orderSell); 
				    	 }
				    	 else if(coinManagement!=null)
				    	 {
				    		 LOGGER.info("Buyers and seller match found but admin has low or equal price from seller");
				    		    String transactionWithAdminOrUser="admin";
								transactionWithAdmin(cloneBuyOrders,coinManagement,transactionWithAdminOrUser);
								
				    	 }
				     }
				}
			}
			else // if seller list empty
			{    
				LOGGER.info("Seller list is empty so transaction with admin initiating");
				CoinManagement coinManagement = coinManagementJpaRepository.findByCoinName(cloneBuyOrders.getCoinName());
				if(coinManagement!=null&&orderBuy.getQuoteValue()>=coinManagement.getPrice())
				{ //transaction need success
					LOGGER.info("Now transactions started with admin");
					String transactionWithAdminOrUser="admin";
				    transactionWithAdmin(cloneBuyOrders,coinManagement,transactionWithAdminOrUser);
				}
				else // admin price is more then buyer
				{   LOGGER.info("Unfortunately admin price is more then buyer required ");
					map.put("Result", "Not any matching transaction available");
					map.put("isSuccess", isSuccess);
				}
				
			}
		 }
		}
		else // no any buyer is available
		{
			map.put("Result", "Buyer is not available");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Buyer is not available");
		}
		return map;
		
	}
	/***********************common methods after all checks*******************************/
	public void transactionWithSeller(Orders cloneBuyOrders,String transactionWithAdminOrUser,Orders orderSell)
	{ 
	CoinManagement coinManagement= coinManagementJpaRepository.findByCoinName(cloneBuyOrders.getCoinName());	
	Integer pendingQuantity=0;
	if(cloneBuyOrders.getCoinQuantity()<=coinManagement.getInitialSupply())
	{
		pendingQuantity=0;
	}
	else
	{
		pendingQuantity=cloneBuyOrders.getCoinQuantity()-coinManagement.getInitialSupply();
	}
	autmaticUpdateInBuyerUserWallet(cloneBuyOrders,pendingQuantity);
	autmaticUpdateInSellerUserWallet(orderSell,pendingQuantity);
	autmaticUpdateInBuyerOrder(cloneBuyOrders,pendingQuantity);
	autmaticUpdateInCoinManagement(coinManagement,pendingQuantity,transactionWithAdminOrUser,cloneBuyOrders);
    autmaticUpdateInTransactionWithSeller(cloneBuyOrders,pendingQuantity,orderSell);
	}
	/**************************************************************************************/
	public void transactionWithAdmin(Orders cloneBuyOrders,CoinManagement coinManagement,String transactionWithAdminOrUser)
	{  Integer pendingQuantity=0;
		if(cloneBuyOrders.getCoinQuantity()<=coinManagement.getInitialSupply())
		{	pendingQuantity=0;}
		else
		{pendingQuantity=cloneBuyOrders.getCoinQuantity()-coinManagement.getInitialSupply();
		}
		autmaticUpdateInBuyerUserWallet(cloneBuyOrders,pendingQuantity);
	      autmaticUpdateInBuyerOrder(cloneBuyOrders,pendingQuantity);
	      autmaticUpdateInCoinManagement(coinManagement,pendingQuantity,transactionWithAdminOrUser,cloneBuyOrders);
        autmaticUpdateInTransactionWithAdmin(cloneBuyOrders,pendingQuantity);
	}
	/***********************************************common update functions*********************************************************/
      public void autmaticUpdateInBuyerUserWallet(Orders cloneBuyOrders,Integer pendingQuantity)
      {
    	  User user = cloneBuyOrders.getUser();
			Set<UserWallet> userWallets = user.getUserWallet();
			UserWallet userWalletFiate = null;
			UserWallet userWalletNew = null;
			for (UserWallet uw : userWallets) {
				if (("fiate").equals(uw.getWalletType())) {
					userWalletFiate = uw;
				} else if (cloneBuyOrders.getCoinName().equals(uw.getWalletType())) {
					userWalletNew = uw;
				}

			}
			if(pendingQuantity==0)
			{  Integer purchasedQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
				userWalletFiate.setBalance(userWalletFiate.getShadowBalance());
				userWalletNew.setBalance(userWalletNew.getBalance()+purchasedQuantity);
				userWalletNew.setShadowBalance(userWalletNew.getShadowBalance()+purchasedQuantity);
			}
			else //if pendingQuantity>0
			{
				Integer purchasedQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
				Integer fee=(purchasedQuantity*cloneBuyOrders.getQuoteValue()*2)/100;
				/*Integer newShadowBalance=userWalletFiate.getShadowBalance()-(purchasedQuantity*cloneBuyOrders.getQuoteValue());*/
				userWalletFiate.setBalance(userWalletFiate.getBalance()-(purchasedQuantity*cloneBuyOrders.getQuoteValue())-fee);
				/*userWalletFiate.setShadowBalance(newShadowBalance);*/
				userWalletNew.setBalance(userWalletNew.getBalance()+purchasedQuantity);
				userWalletNew.setShadowBalance(userWalletNew.getShadowBalance()+purchasedQuantity);
			}	
			userWalletJpaRepository.save(userWalletFiate);
			userWalletJpaRepository.save(userWalletNew);
    	  
      }
      /*************************************************************/
      public void autmaticUpdateInSellerUserWallet(Orders orderSell,Integer pendingQuantity)
      {
    	  User user = orderSell.getUser();
			Set<UserWallet> userWallets = user.getUserWallet();
			UserWallet userWalletFiate = null;
			UserWallet userWalletNew = null;
			for (UserWallet uw : userWallets) {
				if (("fiate").equals(uw.getWalletType())) {
					userWalletFiate = uw;
				} else if (orderSell.getCoinName().equals(uw.getWalletType())) {
					userWalletNew = uw;
				}

			}
			if(pendingQuantity==0)
			{  Integer selledQuantity=orderSell.getCoinQuantity()-pendingQuantity;
			   Integer inrConvergence=selledQuantity*orderSell.getQuoteValue();
				userWalletNew.setBalance(userWalletNew.getShadowBalance());
				userWalletFiate.setBalance(userWalletFiate.getBalance()+inrConvergence);
				userWalletFiate.setShadowBalance(userWalletFiate.getShadowBalance()+inrConvergence);
				
			}
			else //if pendingQuantity>0
			{
				Integer selledQuantity=orderSell.getCoinQuantity()-pendingQuantity;
				Integer inrConvergence=selledQuantity*orderSell.getQuoteValue();
				Integer newShadowBalance=userWalletNew.getShadowBalance()-(selledQuantity*orderSell.getQuoteValue());
				userWalletNew.setBalance(userWalletNew.getBalance()-selledQuantity);
				userWalletNew.setShadowBalance(newShadowBalance);
				userWalletFiate.setBalance(userWalletFiate.getBalance()+inrConvergence);
				userWalletFiate.setShadowBalance(userWalletFiate.getShadowBalance()+inrConvergence);
			}
			userWalletJpaRepository.save(userWalletNew);
			userWalletJpaRepository.save(userWalletFiate);
    	  
      }
      
      /*************************************************************/
      public void autmaticUpdateInBuyerOrder(Orders cloneBuyOrders,Integer pendingQuantity)
      {
    	  if(pendingQuantity==0)
    	  {
    		  cloneBuyOrders.setCoinQuantity(pendingQuantity);
    		  cloneBuyOrders.setFee(0);
    		  cloneBuyOrders.setGrossAmount(0);
    		  cloneBuyOrders.setStatus("done");  
    	  }
    	  else //if pendingQuantity>0
    	  {
    		  cloneBuyOrders.setCoinQuantity(pendingQuantity);
    		  Integer newFee=(pendingQuantity*2)/100;
    		  cloneBuyOrders.setFee(newFee);
    		  Integer newGrossAmount=pendingQuantity+newFee;
    		  cloneBuyOrders.setGrossAmount(newGrossAmount);
    	  }
    	  orderJpaRepository.save(cloneBuyOrders);
      }
      /*************************************************************/
      /*public void autmaticUpdateInSellerOrder(Orders sellOrders,Integer pendingQuantity)
      {
    	  if(pendingQuantity==0)
    	  {
    		  sellOrders.setCoinQuantity(pendingQuantity);
    		  sellOrders.setFee(0);
    		  sellOrders.setGrossAmount(0);
    		  sellOrders.setStatus("done");  
    	  }
    	  else //if pendingQuantity>0
    	  {
    		  sellOrders.setCoinQuantity(pendingQuantity);
    		  Integer newFee=(pendingQuantity*2)/100;
    		  sellOrders.setFee(newFee);
    		  Integer newGrossAmount=pendingQuantity+newFee;
    		  sellOrders.setGrossAmount(newGrossAmount);
    	  }
    	  orderJpaRepository.save(sellOrders);
      }*/
      /*************************************************************/
      public void autmaticUpdateInCoinManagement(CoinManagement coinManagement,Integer pendingQuantity,String transactionWithAdminOrUser,Orders cloneBuyOrders)
      {
    	  CoinManagement updatedCoinManagement=coinManagement;
    	if(("admin").equals(transactionWithAdminOrUser))//if admin
    	{ System.out.println("::::::::::admin updation hit"+coinManagement);
    		if(pendingQuantity==0)
    		{   System.out.println("::::::::::admin updation hit");
    		updatedCoinManagement.setINRConvergent(cloneBuyOrders.getCoinQuantity()*cloneBuyOrders.getQuoteValue());
    			Integer moreProfit=(cloneBuyOrders.getCoinQuantity()*cloneBuyOrders.getQuoteValue())*2/100;
    			updatedCoinManagement.setProfit(coinManagement.getProfit()+moreProfit);
    			updatedCoinManagement.setInitialSupply(coinManagement.getInitialSupply()-cloneBuyOrders.getCoinQuantity());
    			
    			System.out.println("::::::::::admin updation hit");
    		}
    		else //if pendingQuantity>0
    		{
    			Integer moreProfit=	(coinManagement.getInitialSupply()*cloneBuyOrders.getQuoteValue())*2/100;
    			updatedCoinManagement.setProfit(coinManagement.getProfit()+moreProfit);
    			updatedCoinManagement.setINRConvergent(coinManagement.getInitialSupply()*cloneBuyOrders.getQuoteValue());
    			updatedCoinManagement.setInitialSupply(0);
    			
    		}
    		System.out.println("::::::::::admin updation hit");
    		coinManagementJpaRepository.save(updatedCoinManagement);
    		System.out.println(":::::::::::::::::::::::::::::::::::::::admin updation hit");
    	}
    	else if(("user").equals(transactionWithAdminOrUser)) // if normal user
    	{
    		if(pendingQuantity==0)
    		{
    			Integer moreProfit=(cloneBuyOrders.getCoinQuantity()*cloneBuyOrders.getQuoteValue())*2/100;
    			coinManagement.setProfit(coinManagement.getProfit()+moreProfit);
    		}
    		else //if pendingQuantity>0
    		{
    			Integer moreProfit=	(coinManagement.getInitialSupply()*cloneBuyOrders.getQuoteValue())*2/100;
    			coinManagement.setProfit(coinManagement.getProfit()+moreProfit);
    		}
    		coinManagementJpaRepository.save(coinManagement);
    	}// no else required
    	
      }
      
      /*************************************************************/
      public void autmaticUpdateInTransactionWithAdmin(Orders cloneBuyOrders,Integer pendingQuantity)
      {
    	  Transaction transaction = new Transaction();
      		if(pendingQuantity==0)
      		{   Integer sellOutQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
      			transaction.setBuyerId(cloneBuyOrders.getOrderId());
      			transaction.setSellerId(0);
      			Integer inrAmount = sellOutQuantity * cloneBuyOrders.getQuoteValue();
      			Integer fee = (inrAmount * 2) / 100;
      			Integer grossAmount = inrAmount + fee;
      			transaction.setGrossAmount(grossAmount);
      			transaction.setStatus("Success");
      			transaction.setExchangeRate(cloneBuyOrders.getQuoteValue());
      			transaction.setCointype(cloneBuyOrders.getCoinName());
      			transaction.setDescription("Admin approval");
      			transaction.setTransactionCreatedOn(new Date());
      			transaction.setCoinQuantity(sellOutQuantity);
      		}
      		else //if pendingQuantity>0
      		{   Integer sellOutQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
      			transaction.setBuyerId(cloneBuyOrders.getOrderId());
      			transaction.setSellerId(0);
      			Integer inrAmount = sellOutQuantity * cloneBuyOrders.getQuoteValue();
      			Integer fee = (inrAmount * 2) / 100;
      			Integer grossAmount = inrAmount + fee;
      			transaction.setGrossAmount(grossAmount);
      			transaction.setStatus("Success");
      			transaction.setExchangeRate(cloneBuyOrders.getQuoteValue());
      			transaction.setCointype(cloneBuyOrders.getCoinName());
      			transaction.setDescription("Admin approval");
      			transaction.setTransactionCreatedOn(new Date());
      			transaction.setCoinQuantity(sellOutQuantity);
      		}
      		transactionJpaRepository.save(transaction);
      	}
      /*******************************************************************************/
      public void autmaticUpdateInTransactionWithSeller(Orders cloneBuyOrders,Integer pendingQuantity,Orders cloneSellOrders)
      { 
    	  Transaction transaction = new Transaction();
      		if(pendingQuantity==0)
      		{
      			Integer sellOutQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
      			transaction.setBuyerId(cloneBuyOrders.getOrderId());
      			transaction.setSellerId(cloneSellOrders.getOrderId());
      			transaction.setStatus("Success");
      			transaction.setExchangeRate(cloneBuyOrders.getQuoteValue());
      			transaction.setCointype(cloneBuyOrders.getCoinName());
      			transaction.setDescription("Admin approval");
      			transaction.setTransactionCreatedOn(new Date());
      			transaction.setCoinQuantity(sellOutQuantity);
      		}
      		else //if pendingQuantity>0
      		{
      			Integer sellOutQuantity=cloneBuyOrders.getCoinQuantity()-pendingQuantity;
      			transaction.setBuyerId(cloneBuyOrders.getOrderId());
      			transaction.setSellerId(cloneSellOrders.getOrderId());
      			transaction.setStatus("Success");
      			transaction.setExchangeRate(cloneBuyOrders.getQuoteValue());
      			transaction.setCointype(cloneBuyOrders.getCoinName());
      			transaction.setDescription("Admin approval");
      			transaction.setTransactionCreatedOn(new Date());
      			transaction.setCoinQuantity(sellOutQuantity);	
      		}	
      		transactionJpaRepository.save(transaction);
      	}
    	  
      
      
}
