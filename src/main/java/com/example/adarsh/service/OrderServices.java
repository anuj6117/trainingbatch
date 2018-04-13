package com.example.adarsh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adarsh.domain.CoinManagement;
import com.example.adarsh.domain.OrderTabel;
import com.example.adarsh.domain.User;
import com.example.adarsh.domain.Wallet;
import com.example.adarsh.repository.CoinManagementRepository;
import com.example.adarsh.repository.OrderRepository;
import com.example.adarsh.repository.UserRepository;
import com.example.adarsh.repository.WalletRepository;

@Service
public class OrderServices {
	@Autowired
	OrderRepository orderdata;
	@Autowired
	UserRepository userData;
	@Autowired
	CoinManagementRepository coindata;
	@Autowired
	WalletRepository walletRepo;

	public OrderTabel buycurrency(OrderTabel ordermod, String type) throws Exception {
		User model = userData.findByUserId(ordermod.getUserId());

		OrderTabel orderModelObj = new OrderTabel();
		
		if(ordermod.getAmount()<=0) 
		{ 
		throw new Exception("Amount value should be greater than 0");
		}
		
		if(ordermod.getQuoteValue()<=0) 
		{ 
		throw new Exception("QuoteValue  should be greater than 0");
		}
		
		OrderTabel orderCreated = null;
		Double amount = ordermod.getAmount();
		Double quoteValue = ordermod.getQuoteValue();
		Double fee = 2.0;
		Wallet defaultWalletObj = null;
		Wallet currentWalletObj = null;
		// Walletmodel createwalletObj = new Walletmodel();
		Double totalAmount = ordermod.getAmount() * ordermod.getQuoteValue();
		Double transactionFee = totalAmount * (2.0 / 100);
		Double grossAmount = totalAmount + transactionFee;
		boolean walletFlag = true;
		boolean flag = true;
		List<CoinManagement> allCoinList = coindata.findAll();
		for (CoinManagement gettingAllCoins : allCoinList) {
			if (ordermod.getCoinName().equals(gettingAllCoins.getCoinName())) {
				List<Wallet> userWalletList = model.getWallet();
				defaultWalletObj = userWalletList.get(0);
				flag = false;
				for (Wallet gettingUserWallet : userWalletList) {
					if (ordermod.getCoinName().equals(gettingUserWallet.getWalletType())) {
						walletFlag = false;
						currentWalletObj = gettingUserWallet;
					}
				}
			}
		}
		if (flag) {
			throw new NullPointerException("Trading not possible on this coin");
		}
		if (type.equals("buyer")) {
			if (grossAmount < defaultWalletObj.getShadowBalance()) {
				if (walletFlag) {
					Wallet createwalletObj = new Wallet();
					createwalletObj.setBalance(createwalletObj.getBalance());
					createwalletObj.setShadowBalance(createwalletObj.getShadowBalance());
					createwalletObj.setWalletType(ordermod.getCoinName());
					createwalletObj.setUser(model);

					walletRepo.save(createwalletObj);
					currentWalletObj = createwalletObj;
				}
				 
				orderModelObj.setAmount(ordermod.getAmount());
				orderModelObj.setFee((double) 2);
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setGrossAmount(grossAmount);
				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setUserModelInOrderModel(model);
				// updating amount in default wallet;
				Double newDefaultShadowBalance = defaultWalletObj.getShadowBalance() - grossAmount;
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletRepo.save(defaultWalletObj); // updating amount in another wallet;
				Double newCurrentShadowBalance = currentWalletObj.getShadowBalance() + ordermod.getAmount();
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);
				walletRepo.save(currentWalletObj);
				orderCreated = orderdata.save(orderModelObj);
				return orderCreated;
			} else {
				throw new NullPointerException("Don't have enough money to buy coins");
			}
		} else {
			if (ordermod.getAmount() < currentWalletObj.getBalance()) {
				
				orderModelObj.setAmount(ordermod.getAmount());
				orderModelObj.setFee(ordermod.getFee());
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setUserModelInOrderModel(model);
				Double newDefaultShadowBalance = defaultWalletObj.getShadowBalance() + totalAmount;
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletRepo.save(defaultWalletObj);
				Double newCurrentShadowBalance = currentWalletObj.getShadowBalance() - amount;
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);
				walletRepo.save(currentWalletObj);
				return orderdata.save(orderModelObj);
			} else {
				throw new NullPointerException("Don't have enough coins to sell");
			}
		}

	}

	public List<OrderTabel> showalldata(Long userId) {
		User data = userData.findOneByUserId(userId);
		List<OrderTabel> result = data.getOrderTabel();
		return result;
	}

	public OrderTabel admincreateSellOrder(OrderTabel order) throws Exception{
		
		if(order.getAmount()<=0) 
		{ 
		throw new Exception("Amount value should be greater than 0");
		}
		
		if(order.getQuoteValue()<=0) 
		{ 
		throw new Exception("QuoteValue  should be greater than 0");
		}

		User model = userData.findByUserId(order.getUserId());
		CoinManagement coinDetails = coindata.findByCoinName(order.getCoinName());
		OrderTabel orderModelObj = new OrderTabel();
		if (order.getAmount() < coinDetails.getInitialSupply()) {
			orderModelObj.setAmount(order.getAmount());
			orderModelObj.setFee(2.0);
			orderModelObj.setQuoteValue((double) order.getQuoteValue());
			orderModelObj.setOrderType("sell");
			orderModelObj.setOrderCreatedOn(new Date());
			orderModelObj.setStatus("pending");
			orderModelObj.setCoinName(order.getCoinName());
			Double amount = order.getAmount();
			Double quoteValue = (double) order.getQuoteValue();
			Double totalAmount = amount * quoteValue;
			System.out.println(":::::::::" + amount);
			System.out.println(":::::::::" + quoteValue);
			System.out.println("::::::::::::::" + totalAmount);
			// Double transFee = totalAmount * (order.getFee() / 100);
			Double grossAmount = totalAmount;
			orderModelObj.setGrossAmount(grossAmount);
			orderModelObj.setUserModelInOrderModel(model);
			return orderdata.save(orderModelObj);
		} else {
			throw new NullPointerException("Don't have enough coins to sell");
		}

	}
}
