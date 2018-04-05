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
	
	public OrderModel buycurrency(OrderModel data,String type)
	{
		boolean flag=true;
		UserModel userModel = userData.findOne(data.getUserId());
		if(userModel==null)
			throw new RuntimeException("user not exist");
		List<WalletModel> walletdata = userModel.getWalletModel();
		CoinManagementModel coinresult=coindata.findByCoinName(data.getCoinName());
			if(coinresult==null)
				throw new RuntimeException("coin not exist");
		OrderModel order=new OrderModel();
		order.setAmount(data.getAmount());
		order.setCoinName(data.getCoinName());
		order.setFee(data.getFee());
		order.setOrderCreatedOn(new Date());
		order.setOrderType(type);
		order.setStatus("pending");
		order.setQuote(data.getQuote());
		order.setUserId(data.getUserId());
		order.setUser(userModel);
		order.setCoinName(data.getCoinName());
		//orderdata.save(order);
		userModel.getOrderModel().add(order);
		
		for(WalletModel walletype:walletdata)
		{
			if(walletype.getWalletType().equals(data.getCoinName()))
			{
				flag=false;
			if(walletype.getAmount()>=data.getAmount())
			{
				userData.save(userModel);
				return order;
			}
			else
			{
				throw new RuntimeException("amount not valid");
			}
			}
			
		}
		if(flag)
			throw new RuntimeException("you dont hava wallet");
		return order;
	}
	public  List<OrderModel> showhistory(Long id)
	{
		UserModel data=userData.findOne(id);
		List<OrderModel> result=data.getOrderModel();
		return result;
	}
}
