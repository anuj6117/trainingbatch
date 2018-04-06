package com.example.demo.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Utility;



@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private UserRepository userRepo;
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Object createbuyOrder(OrderModel orderModel) throws Exception {
		Optional<UserModel> userDetail = userRepo.findById(orderModel.getUserId());
		logger.info(userDetail.isPresent()+"--------");
		if(userDetail.isPresent()) {//user does not exists
			if((Utility.isStringNull(orderModel.getCoinName()))) {
				if((orderModel.getQuantity()>0)) {
					orderModel.setOrderType("buy");
					orderModel.setStatus("Pending");
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					orderModel.setFee(orderModel.getFee());
					orderModel.setQuantity(orderModel.getQuantity());
					orderModel.setQuoteValue(orderModel.getQuoteValue());
					Long amount = orderModel.getQuantity()*orderModel.getQuoteValue();
					Long grossAmount =(Long)(amount*orderModel.getFee())/100;
					orderModel.setGrossAmount(grossAmount);
					logger.info(orderModel.getGrossAmount()+"---------------");
					orderRepo.save(orderModel);
					//update this users default wallets shadow balance
					
				}else {
					throw new Exception("Quantity cannot be null");
				}
			}
			else {
				throw new Exception("Coin Name cannot be null");
			}	
		}
		else {
			throw new Exception("User Does Not exist");
		}
		return "success";
	}
	
	
	
	
	public Object createsellOrder(OrderModel orderModel) {
		Optional<UserModel> userDetail = userRepo.findById(orderModel.getUserId());
		if(userDetail!=null) {
			if(!(orderModel.getCoinName().equals(""))) {
				if(!(orderModel.getQuantity()>0)) {
					orderModel.setOrderType("sell");
					orderModel.setStatus("Pending");
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					orderRepo.save(orderModel);	
				}else {
					return "Quantity cannot be null";
				}
			}
			else {
				return "Coin Name cannot be null";
			}	
		}
		else {
			return "User Does Not exist";
		}
		return "success";
	}
	
	public Object getallordersbyId(Integer userId) {
		Optional<UserModel> user = userRepo.findById(userId);
		Set <OrderModel> orderSet = user.get().getUserOrder();
		if(orderSet.isEmpty()) {
			return "No Data Found";
		}
		return orderSet;
	}
	/*public Object createsellOrder(OrderModel orderModel) {
		Optional<UserModel> userDetail = userRepo.findById(orderModel.getUserId());
		Set<WalletModel> walletType = userDetail.get().getUserWallet();
		for(WalletModel type : walletType) {
			if(type.getWalletType().equals(orderModel.getCoinName())){
				
				if(orderModel.getTradingAmount()<=type.getBalance()) {
					orderModel.setOrderType("sell");
					orderModel.setStatus("Pending");
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					orderRepo.save(orderModel);
					
				}else {
					return "Amount is big , make a order less than amount";
				}
			}
		}
		return "success";
	}*/
	
	
	public Object getallorders() {
		return orderRepo.findAll();
	}
	public Object getOrdersByType(String orderType) {
		return orderRepo.findByOrderType(orderType);
	}

}
