package com.example.demo.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.model.userModel.OrderModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.CoinManagementRepository;
import com.example.demo.repoINterface.OrderRepository;
import com.example.demo.repoINterface.UserRepository;

@Service
public class OrderServices {

	@Autowired
	OrderRepository orderdata;
	@Autowired
	UserRepository userData;
	@Autowired
	CoinManagementRepository coindata;

	public OrderModel buycurrency(OrderModel data, String type) {
		int fee = 0;
		int grossamount = 0;
		boolean flag = true;
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new RuntimeException("user not exist");
		if(!userModel.isStatus())
			throw new RuntimeException("user not active");
		List<WalletModel> walletdata = userModel.getWalletModel();
		CoinManagementModel coinresult = coindata.findByCoinName(data.getCoinName());
		if (coinresult == null)
			throw new RuntimeException("coin not exist");
		
		OrderModel order = new OrderModel();
		order.setCoinName(data.getCoinName());
		if (type.equals("buyer")) {
			fee = 2 * (data.getCoinQuantity() * data.getPrice()) / 100;
			order.setFee(fee);
		} else {
			order.setFee(0);
		}
		order.setOrderCreatedOn(new Date());
		order.setOrderType(type);
		order.setStatus("pending");
		order.setUserId(data.getUserId());
		order.setCoinQuantity(data.getCoinQuantity());
		order.setPrice(data.getPrice());
		order.setSellettype("user");
		grossamount = fee + (data.getCoinQuantity() * data.getPrice());
		order.setGrossAmount(grossamount);
		userModel.getOrderModel().add(order);
		order.setUser(userModel);
		if(type.equals("buyer")) {
		for (WalletModel walletype : walletdata) {
			if (walletype.getWalletType().equals(data.getCoinName())) {
				flag = false;
				WalletModel fiatwallet=walletdata.get(0);
				if (fiatwallet.getShadoBalance()>= order.getGrossAmount()) {
					System.out.println(order.getGrossAmount());
					fiatwallet.setShadoBalance(fiatwallet.getShadoBalance()-order.getGrossAmount());
					walletype.setShadoBalance(walletype.getShadoBalance()+order.getCoinQuantity());
					userModel.getWalletModel().add(walletype);
					userModel.getWalletModel().add(fiatwallet);
					userData.save(userModel);
					return order;
				} else {
					throw new RuntimeException("you dont hava enough amount");
				}
			}
			
		}
		}
		if(type.equals("seller")) {
			for (WalletModel walletype : walletdata) {
				if (walletype.getWalletType().equals(data.getCoinName())) {
					flag = false;
					WalletModel fiatwallet=walletdata.get(0);
					if (walletype.getShadoBalance() >= order.getCoinQuantity()) {
						System.out.println(order.getGrossAmount());
						fiatwallet.setShadoBalance(fiatwallet.getShadoBalance()+order.getGrossAmount());
						walletype.setShadoBalance(walletype.getShadoBalance()-order.getCoinQuantity());
						userModel.getWalletModel().add(walletype);
						userModel.getWalletModel().add(fiatwallet);
						userData.save(userModel);
						return order;
					} else {
						throw new RuntimeException("you dont hava enough amount");
					}
				}
				
			}
		}
		
		if (flag) {
			if(type.equals("buyer")) {
			WalletModel walletModel = new WalletModel();
		walletModel.setAmount(0);
		walletModel.setWalletType(data.getCoinName());
		walletModel.setShadoBalance(data.getCoinQuantity());
		walletModel.setUserdata(userModel);
		userModel.getWalletModel().add(walletModel);
		UserModel result = userData.save(userModel);
		}
			else
				throw new RuntimeException("you dont hava wallet");
		}
		return order;
	}
//show history
	public List<OrderModel> showhistory(Long id) {
		UserModel data = userData.findOne(id);
		List<OrderModel> result = data.getOrderModel();
		return result;
	}
//sellorderbyadmin....
	
	public OrderModel sellbyadmin(OrderModel data)
	{
		CoinManagementModel coinresult = coindata.findByCoinIdAndCoinName(data.getCoinId(), data.getCoinName());
		if (coinresult == null)
			throw new RuntimeException("coin not exist");
		if(!(coinresult.getInitialSupply()>=data.getCoinQuantity()))
			throw new RuntimeException("you dont have enougn amount");
			
		OrderModel order = new OrderModel();
		order.setCoinName(data.getCoinName());
		int fee = 2 * (data.getCoinQuantity() * data.getPrice()) / 100;
		order.setFee(fee);
		order.setOrderCreatedOn(new Date());
		order.setOrderType("seller");
		order.setStatus("pending");
		order.setSellettype("admin");
		order.setUserId(data.getCoinId());
		order.setCoinQuantity(data.getCoinQuantity());
		order.setPrice(data.getPrice());
		int grossamount = fee + (data.getCoinQuantity() * data.getPrice());
		order.setGrossAmount(grossamount);
		orderdata.save(order);
		return data;
		
	}
	
	
}
