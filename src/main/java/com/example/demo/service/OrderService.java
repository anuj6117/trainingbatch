package com.example.demo.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private UserRepository userRepo;
	
	
	
	
	public Object createOrder(Integer userId,OrderModel orderModel) {
		Optional<UserModel> userDetail = userRepo.findById(userId);
		Set<WalletModel> walletType = userDetail.get().getUserWallet();
		for(WalletModel type : walletType) {
			if(type.getWalletType().equals(orderModel.getCoinName())){
				
				if(orderModel.getAmount()<=type.getBalance()) {
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					orderRepo.save(orderModel);
					
				}else {
					return "Amount is big , make a order less than amount";
				}
			}
		}
		return "success";
	}
	
	public Object getallorders() {
		return orderRepo.findAll();
	}
	public Object getOrdersByType(String orderType) {
		return orderRepo.findByOrderType(orderType);
	}

}
