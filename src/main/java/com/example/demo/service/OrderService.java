package com.example.demo.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	public Object createOrder(OrderModel orderModel) {
		UserModel user = orderModel.getUserModel();
		Set<WalletModel> walletType = user.getUserWallet();
		for(WalletModel type:walletType) {
			if(type.getWalletType().equals(orderModel.getCoinName())){
				if(orderModel.getAmount()<=type.getBalance()) {
					orderModel.setOrderCreatedOn(new Date());
					orderRepo.save(orderModel);
					
				}else {
					return "false";
				}
	
			}
			else {
				return "false";
			}
		}
		return "success";
	}

}
