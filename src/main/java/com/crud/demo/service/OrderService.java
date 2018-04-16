package com.crud.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.dto.UserWalletDTO;
import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.jpaRepositories.OrderJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.Orders;
import com.crud.demo.model.User;
import com.crud.demo.model.UserWallet;

@Service
public class OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderJpaRepository orderJpaRepository;
	
	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Autowired 
	private  UserWalletJpaRepository  userWalletJpaRepository;
	
	@Autowired
	private UserWalletService userWalletService;
	
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;
	
	
	
	
	
	
	
	                        /*AllserviceFunctions*/
	/**
	 * @throws InterruptedException ***************************************************************************************/
	public Map<String, Object> createBuyingOrder(Orders order) throws InterruptedException {
		LOGGER.info("OrderService:::createBuyingOrder::::method hit");
		Map<String, Object> map=new HashMap<>();
		Boolean isSuccess=false;
		User user=userJpaRepository.findOne(order.getUserId());//user whose buying order will be saved
		Map<String,Integer> userAlreadyWallets=new HashMap<>();
		
		for(UserWallet userWallet:user.getUserWallet())
		{
			userAlreadyWallets.put(userWallet.getWalletType(),userWallet.getWalletId());
		}
		LOGGER.info("OrderService:::createBuyingOrder::::before checking user is null or not");
		Set<String> existingCurrency =coinManagementJpaRepository.findByCoinName();
		if((user!=null)&&(user.getStatus())
				&& userAlreadyWallets.containsKey(order.getCoinName())
				&&(existingCurrency.contains(order.getCoinName()))
				&&(order.getCoinQuantity()>0)&&order.getQuoteValue()>0)
		{   
			LOGGER.info("OrderService:::createBuyingOrder::::before calculation");
			Integer tradingAmount=order.getCoinQuantity()*order.getQuoteValue();
		    Integer fee=(tradingAmount*2)/100;
			order.setFee(fee);
			Integer grossAmount=tradingAmount+fee;
			order.setGrossAmount(grossAmount);
			UserWallet userWalletAutomaticUpdation=userWalletJpaRepository.findOne(userAlreadyWallets.get("fiate"));
			 LOGGER.info("OrderService:::before save::::{}{}{}{}",tradingAmount,fee,grossAmount,userWalletAutomaticUpdation.getBalance());
			if(userWalletAutomaticUpdation.getBalance()>grossAmount)
		    {
			order.setOrderType("buy");
		    user.getOrders().add(order);
		    order.setUser(user);
		   userJpaRepository.save(user);
		   LOGGER.info("OrderService:::createBuyingOrder::::order saved successfully");
		   isSuccess=true;
/******/   if(isSuccess)//automatic update in userWallet
		   {  
		  /*if(userWalletAutomaticUpdation.getBalance()>grossAmount)*/
		   Integer newShadowBalance=userWalletAutomaticUpdation.getShadowBalance()-grossAmount;
		   if(userWalletAutomaticUpdation.getShadowBalance()>=grossAmount) { 
		   userWalletAutomaticUpdation.setShadowBalance(newShadowBalance);
		    userJpaRepository.save(user);
		    map.put("Result","Buying Order placed successfully");
		    map.put("isSuccess", isSuccess);
		    LOGGER.info("OrderService:::createBuyingOrder::::Buying Order placed successfully");
/******/   }
		   }else
		   {
			   map.put("Result","Not sufficent balance");
				map.put("isSuccess", isSuccess);
				LOGGER.error("OrderService:::createBuyingOrder::::Unable to place Buying order");
		   }
		   }
		
		}
		else if((user!=null)&&(user.getStatus())
				&& !userAlreadyWallets.containsKey(order.getCoinName())
				&&(existingCurrency.contains(order.getCoinName()))
				&&(order.getCoinQuantity()>0)
				&&order.getQuoteValue()>0)
		{
			UserWalletDTO userWalletDTO=new UserWalletDTO();
			userWalletDTO.setUserId(order.getUserId());
			userWalletDTO.setWalletType(order.getCoinName());
			userWalletService.createWallet(userWalletDTO);
			LOGGER.info("OrderService:::createBuyingOrder::::before calculation");
			Integer tradingAmount=order.getCoinQuantity()*order.getQuoteValue();
		    Integer fee=(tradingAmount*2)/100;
			order.setFee(fee);
			Integer grossAmount=tradingAmount+fee;
			order.setGrossAmount(grossAmount);
			UserWallet userWalletAutomaticUpdation=userWalletJpaRepository.findOne(userAlreadyWallets.get("fiate"));
			 LOGGER.info("OrderService:::before save::::{}{}{}{}",tradingAmount,fee,grossAmount,userWalletAutomaticUpdation.getBalance());
			if(userWalletAutomaticUpdation.getBalance()>grossAmount)
		    {
			order.setOrderType("buy");
		    user.getOrders().add(order);
		    order.setUser(user);
		   userJpaRepository.save(user);
		   LOGGER.info("OrderService:::createBuyingOrder::::order saved successfully");
		   isSuccess=true;
/******/   if(isSuccess)//automatic update in userWallet
		   {  
		  /*if(userWalletAutomaticUpdation.getBalance()>grossAmount)*/
		    Integer newShadowBalance=userWalletAutomaticUpdation.getShadowBalance()-grossAmount;
		    if(userWalletAutomaticUpdation.getShadowBalance()>=grossAmount) {
		    userWalletAutomaticUpdation.setShadowBalance(newShadowBalance);
		    userJpaRepository.save(user);
		    map.put("Result","Buying Order placed successfully");
		    map.put("isSuccess", isSuccess);
		    LOGGER.info("OrderService:::createBuyingOrder::::Buying Order placed successfully");
/******/   }
		    else
		    {
		    	map.put("Result","Not sufficent balance");
				map.put("isSuccess", isSuccess);
				LOGGER.error("OrderService:::createBuyingOrder::::Unable to place Buying order");
		    }
		    }	
		}
		else
		{
			map.put("Result","Unable to place Buying order");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::createBuyingOrder::::Unable to place Buying order");
		}}
		else
		{
			map.put("Result","Unable to place Buying order");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::createBuyingOrder::::Unable to place Buying order");
		}
		return map;
	}
	
	/*****************************************************************************************/
	public Map<String, Object> createSellingOrder(Orders order) {
		LOGGER.info("OrderService:::createSellingOrder::::method hit");
		Map<String, Object> map=new HashMap<>();
		Boolean isSuccess=false;
		User user=userJpaRepository.findOne(order.getUserId());//user whose selling order will be saved
		Map<String,Integer> userAlreadyWallets=new HashMap<>();
		
		for(UserWallet userWallet:user.getUserWallet())
		{
			userAlreadyWallets.put(userWallet.getWalletType(),userWallet.getWalletId());
		}
		LOGGER.info("OrderService:::createSellingOrder::::before checking user is null or not");
		if((user!=null)&& userAlreadyWallets.containsKey(order.getCoinName())
				&&(order.getCoinQuantity()>0)
				&&order.getQuoteValue()>0)
		{   
			LOGGER.info("OrderService:::createSellingOrder::::before calculation");
			Integer tradingAmount=order.getCoinQuantity()*order.getQuoteValue();
			Integer grossAmount=tradingAmount;
			order.setGrossAmount(grossAmount);
			UserWallet userWalletAutomaticUpdation=userWalletJpaRepository.findOne(userAlreadyWallets.get(order.getCoinName()));
			 LOGGER.info("OrderService:::before save::::{}{}{}",tradingAmount,grossAmount,userWalletAutomaticUpdation.getBalance());
			if(userWalletAutomaticUpdation.getShadowBalance()>=order.getCoinQuantity()) {
			order.setOrderType("sell");
		    user.getOrders().add(order);
		   order.setUser(user);
		   userJpaRepository.save(user);
		   LOGGER.info("OrderService:::createSellingOrder::::order saved successfully");
		   isSuccess=true;
/******/   if(isSuccess)//automatic update in userWallet
		   {  
		  /*if(userWalletAutomaticUpdation.getBalance()>grossAmount)*/
		    Integer newShadowBalance=userWalletAutomaticUpdation.getShadowBalance()-order.getCoinQuantity();
		    userWalletAutomaticUpdation.setShadowBalance(newShadowBalance);
		    //fiate wallet automatic updation
		    
		    userJpaRepository.save(user);
		    map.put("Result","Selling Order placed successfully");
		    map.put("isSuccess", isSuccess);
		    LOGGER.info("OrderService:::createSellingOrder::::Selling Order placed successfully");
/******/   }		
		    }
			else
			{
				map.put("Result","Not Sufficient balance ");
				map.put("isSuccess", isSuccess);
				LOGGER.error("OrderService:::createSellingOrder::::Unable to place Selling order");
			}	
		}
		else
		{
			map.put("Result","Unable to place Selling order");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::createSellingOrder::::Unable to place Selling order");
		}
	
		return map;
	}
	/*****************************************************************************************/
	public Map<String, Object> getAllOrdersByUserId(Integer userId) {
		LOGGER.info("OrderService:::getAllOrdersByUserId::::method hit");
		Map<String, Object> map=new HashMap<>();
		Boolean isSuccess=false;
		User user=userJpaRepository.findOne(userId);
		if(user!=null)
		{	
		isSuccess=true;
		map.put("Result",user.getOrders());
		map.put("isSuccess", isSuccess);
		LOGGER.info("OrderService:::getAllOrdersByUserId::::Order details send successfully to {}",userId);
		}
		else
		{
			map.put("Result","Unable to place successfully");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::createBuyingOrder::::Order details send successfully to {}",userId);
		}
		return map;
	}
/*	
	
	/*****************************************************************************************/
	public Map<String, Object> getAllBuyingOrders() {
		LOGGER.info("OrderService:::getAllBuyingOrders::::method hit");
		Boolean isSuccess=false;
		Map<String, Object> map=new HashMap<>();
		List<Orders> listBuyOrders=orderJpaRepository.findAllByOrderType("buy");
				if(listBuyOrders!=null)
				{
					map.put("Result",listBuyOrders);
					map.put("isSuccess", true);
				}else
				{
					map.put("Result","Buy order list empty");
					map.put("isSuccess", isSuccess);
					LOGGER.error("OrderService:::getAllBuyingOrders::::Buy order list empty");
				}
		return map;
	}
	/*****************************************************************************************/

	public Map<String, Object> getAllSellingOrders() {
		LOGGER.info("OrderService:::getAllSellingOrders::::method hit");
		Boolean isSuccess=false;
		Map<String, Object> map=new HashMap<>();
		List<Orders> listSellOrders=orderJpaRepository.findAllByOrderType("sell");
		if(listSellOrders!=null)
		{
			map.put("Result",listSellOrders);
			map.put("isSuccess", true);
		}else
		{
			map.put("Result","Sell order list empty");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::getAllBuyingOrders::::Sell order list empty");
		}
		return map;
	}
/*******************************************************************************/
	public Map<String, Object> getAllOrders() {
		LOGGER.info("OrderService:::getAllOrders::::method hit");
		Boolean isSuccess=false;
		Map<String, Object> map=new HashMap<>();
		List<Orders> listOrders=orderJpaRepository.findAll();
		if(listOrders!=null)
		{
			map.put("Result",listOrders);
			map.put("isSuccess", true);
		}else
		{
			map.put("Result"," order list empty");
			map.put("isSuccess", isSuccess);
			LOGGER.error("OrderService:::getAllBuyingOrders::::Sell order list empty");
		}
		return map;
	}

	

}
