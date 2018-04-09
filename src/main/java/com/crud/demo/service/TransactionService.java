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
public class TransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);	
	@Autowired
	private TransactionJpaRepository transactionJpaRepository;
	@Autowired
	private OrderJpaRepository orderJpaRepository;
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;
	@Autowired
	private UserWalletJpaRepository userWalletJpaRepository;

	public Map<String, Object> approvetransaction() {
		Boolean isSuccess = false;
		Map<String, Object> map = new HashMap<>();
		Boolean buyordersflag=false;
		Boolean sellordersflag=false;
		List<Orders> buyorders=orderJpaRepository.findAllByStatusAndOrderType("pending","buy");
		List<Orders> sellorders=orderJpaRepository.findAllByStatusAndOrderType("pending","sell");
/********sorting buy order list based on its date***********************************************/
		if(!buyorders.isEmpty()) 
		{
			buyordersflag=true;
		Collections.sort(sellorders, new Comparator<Orders>() {
	        @Override
	        public int compare(Orders o1, Orders o2) {
	            return o1.getOrderCreatedOn().compareTo(o2.getOrderCreatedOn());
	        }
	    });
		
		}
		
/********sorting sell order list based on its quotevalue****************************************/
		if(!sellorders.isEmpty()) 
		{ 
			sellordersflag=true;
		   Collections.sort(sellorders, new Comparator<Orders>() {
	        @Override
	        public int compare(Orders o1, Orders o2) {
	            return Integer.compare(o1.getQuoteValue(), o2.getQuoteValue());
	        }
	    });//
		}
/******************************Admin currency Object List****************************/		
	Set<String> coinSetAvailableInAdmin=coinManagementJpaRepository.findByCoinName();
		
/******************************Matching transactions operations*********************************************/
	/****case 1*********when buyer and seller and admin all available*********/	
	if(buyordersflag&&sellordersflag)//main if 
		{
		   for(Orders orderbuy:buyorders)// buyers loop
		    {
			   for(Orders ordersell:sellorders)//sellers loop
			     {
				   String orderbuyCoinName=orderbuy.getCoinName();
				   String ordersellCoinName=ordersell.getCoinName();
				   Integer orderbuyQuoteValue=orderbuy.getQuoteValue();
				   Integer ordersellQuoteValue=ordersell.getQuoteValue();
				if(orderbuyCoinName.equals(ordersellCoinName ))
				{  CoinManagement coinManagement=coinManagementJpaRepository.findByCoinName(orderbuyCoinName);
					if(orderbuyQuoteValue>=ordersellQuoteValue && ordersellQuoteValue<coinManagement.getPrice())
					{
						
					}
				}
			}//sellers loop
		}// buyers loop
		}//main if
	/*******case 2**********when buyer and admin available only******************/	
		else if(buyordersflag)
		{  
			for(Orders orderbuy:buyorders)// buyers loop
		    {  Integer sellOutQuantity=0;
		       Integer buyOrderQuantity=orderbuy.getCoinQuantity();
		       
			   if(coinSetAvailableInAdmin.contains(orderbuy.getCoinName()))
			   { 
				   CoinManagement coinManagement=coinManagementJpaRepository.findByCoinName(orderbuy.getCoinName());
				   if(orderbuy.getQuoteValue()>=coinManagement.getPrice() && (coinManagement.getInitialSupply()>0))
					 {    
				   LOGGER.info("user-wallet entry updation starts ");
				    User user=orderbuy.getUser();
				    Set<UserWallet> userWallets=user.getUserWallet();
				    UserWallet userWalletFiate=null;
				    UserWallet userWalletNew=null;
				    for(UserWallet uw:userWallets)
				    {  if(("fiate").equals(uw.getWalletType()) )
				      {
				    	userWalletFiate=uw;
				      }
				    else if(orderbuy.getCoinName().equals(uw.getWalletType())){
				    	userWalletNew=uw;
				    }
				    
				    }
                   /*******for user-wallet if-else********/
				    
				    LOGGER.info("user-wallet entry updation starts{} ",userWalletFiate);
				    if(coinManagement.getInitialSupply()>=orderbuy.getCoinQuantity()&& (coinManagement.getInitialSupply()>0))
				    {
				    	
				    	userWalletFiate.setBalance(userWalletFiate.getShadowBalance());
				    	userWalletNew.setBalance(orderbuy.getCoinQuantity());
				    	userWalletNew.setShadowBalance(userWalletNew.getShadowBalance()+orderbuy.getCoinQuantity());
				    userWalletJpaRepository.save(userWalletFiate);
				    userWalletJpaRepository.save(userWalletNew);
				   
				    }
				    else
				    {     /*sellOutQuantity=coinManagement.getInitialSupply();*/
				    	Integer remainingQuantityNeed=orderbuy.getCoinQuantity()-coinManagement.getInitialSupply();	
				    	System.out.println(":::::::coinManagement.getInitialSupply()::::::::::::"+coinManagement.getInitialSupply());
				    	Integer updatedtransactionAmount=coinManagement.getInitialSupply()*orderbuy.getQuoteValue();
				    	Integer fee=(updatedtransactionAmount*2)/100;
				    	
				    	Integer updatedBalance=userWalletFiate.getBalance()-(updatedtransactionAmount+fee);
				    	userWalletFiate.setBalance(updatedBalance);
				    	Integer shadowBalance=remainingQuantityNeed*orderbuy.getQuoteValue();
				    	Integer updatedFee=(shadowBalance*2)/100;
				    	Integer updatedShadowBalance=updatedBalance-(shadowBalance+updatedFee);
				    	userWalletFiate.setShadowBalance(updatedShadowBalance);
				    	//
				    	userWalletNew.setBalance(coinManagement.getInitialSupply());
				    	userWalletNew.setShadowBalance(userWalletNew.getShadowBalance()+coinManagement.getInitialSupply());
				    	 userWalletJpaRepository.save(userWalletFiate);
				    	 userWalletJpaRepository.save(userWalletNew);
				    }
				    
				    /*******for order if-else********/
					LOGGER.info("Order table entry updation starts at last");
					if(coinManagement.getInitialSupply()>=orderbuy.getCoinQuantity())
					{
					
					Integer remainingQuantityNeed=0;
					orderbuy.setCoinQuantity(remainingQuantityNeed);
					orderbuy.setStatus("done");
					orderbuy.setFee(0);
					orderbuy.setGrossAmount(0);
					orderJpaRepository.save(orderbuy);
					}
					else
					{     
						Integer remainingQuantityNeed=orderbuy.getCoinQuantity()-coinManagement.getInitialSupply();
						System.out.println("sellOutQuantity::::::::in orders else part:::::::"+sellOutQuantity);
						System.out.println("remainingQuantityNeed::::::::in orders else part:::::::"+remainingQuantityNeed);
						orderbuy.setCoinQuantity(remainingQuantityNeed);
						Integer updatedTradingAmount=remainingQuantityNeed*orderbuy.getQuoteValue();
						Integer updatedFee=(updatedTradingAmount*2)/100;
						orderbuy.setFee(updatedFee);
						Integer updatedGrossAmount=updatedTradingAmount+updatedFee;
						orderbuy.setGrossAmount(updatedGrossAmount);
						orderJpaRepository.save(orderbuy);
					}
				    
					  /*******for admin if-else********/
					 
					LOGGER.info("Coin management updation starts");
					System.out.println(":::coinManagement.getInitialSupply()::"+coinManagement.getInitialSupply());
					System.out.println(":::orderbuy.getCoinQuantity()::"+buyOrderQuantity);
					if(coinManagement.getInitialSupply()>=buyOrderQuantity) 
				    {
				    	 sellOutQuantity=buyOrderQuantity;
				    	 System.out.println("::::::if-part::::coinmanagement::"+sellOutQuantity);
					 Integer initialSupply=coinManagement.getInitialSupply()-buyOrderQuantity;
					coinManagement.setInitialSupply(initialSupply); 
					Integer fee=(buyOrderQuantity*coinManagement.getPrice()*2)/100;
					Integer updatedProfit=coinManagement.getProfit()+fee;
					coinManagement.setProfit(updatedProfit);
					Integer inrConvergentOfDemandingCoin=buyOrderQuantity*coinManagement.getPrice();
					coinManagement.setINRConvergent(inrConvergentOfDemandingCoin);
					coinManagementJpaRepository.save(coinManagement);
					LOGGER.info("Coin management updated or end");
					
				    }
				    else
				    {   
				    	sellOutQuantity=coinManagement.getInitialSupply();
				    System.out.println("sellOutQuantity::::::::in coinmanagement else part:::::::"+sellOutQuantity);
				    	Integer remainingCoinNeed=buyOrderQuantity-coinManagement.getInitialSupply();
				    	Integer updatedFee=(((buyOrderQuantity-remainingCoinNeed)*coinManagement.getPrice())*2)/100;
				    	coinManagement.setInitialSupply(0); 
						Integer updatedProfit=coinManagement.getProfit()+updatedFee;
						coinManagement.setProfit(updatedProfit);
						Integer inrConvergentOfDemandingCoin=(buyOrderQuantity-remainingCoinNeed)*coinManagement.getPrice();
						coinManagement.setINRConvergent(inrConvergentOfDemandingCoin);
						coinManagementJpaRepository.save(coinManagement);
						LOGGER.info("Coin management updated or end");
						
				    }
				    
					
					
				 } 
				   Transaction  transaction=new Transaction();
				   transaction.setBuyerId(orderbuy.getOrderId());
				   transaction.setCoinQuantity(sellOutQuantity);
				   System.out.println("transaction::::sellOutQuantity:::::"+sellOutQuantity);
				   transaction.setCointype(orderbuy.getCoinName());
				   transaction.setDescription("success fully done");
				   transaction.setExchangeRate(200);
				   Integer tradingAmount=sellOutQuantity*orderbuy.getQuoteValue();
				   Integer fee=(tradingAmount*2)/100;
				   Integer grossAmount=tradingAmount+fee;
				   transaction.setGrossAmount(grossAmount);
				   transaction.setNetAmount(grossAmount+200);
				   transaction.setStatus("Executed");
				   transaction.setTransactionCreatedOn(new Date());
				   Transaction resultObject=transactionJpaRepository.save(transaction);
				   isSuccess=true;
				   map.put("Result", resultObject);
				map.put("isSuccess",isSuccess);
					LOGGER.info("Transaction placed successfully");   
				  
				   
			}
			   
			   
			   
		}// buyers loop		
		}
	/************case 3**************when no any Buyer is available *******************/
		else
		{
			map.put("Result", "Buyer is not available or seller not avalable even admin");
			map.put("isSuccess",isSuccess);
		}
	return map;
	}
	}


