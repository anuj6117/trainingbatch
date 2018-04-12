package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constant.Constant;
import com.example.demo.model.CoinModel;
import com.example.demo.model.OrderModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.CoinRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utils.Utility;



@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private WalletRepository walletRepo;
	@Autowired
	private CoinRepository coinRepo;
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Object createbuyOrder(OrderModel orderModel) throws Exception {
		Optional<UserModel> userDetail = userRepo.findById(orderModel.getUserId());
		if(userDetail.get().getStatus()==false) {
			throw new Exception("First!! verify your account");
		}
		logger.info(userDetail.isPresent()+"--------");
		if(orderModel.getQuoteValue()==null) {
			throw new Exception("Quote value cannot be null");
		}
		if(orderModel.getQuantity()==null){
			throw new Exception("Quantity cannot be null");
		}
		if(orderModel.getCoinName()==null||orderModel.getCoinName()=="") {
			throw new Exception("Invalid!! coin name field cannot be null");
		}
		if(orderModel.getQuoteValue()<=0) {
			throw new Exception("Quote value should be greater than 0");
		}
		if(orderModel.getQuantity()<=0){
			throw new Exception("Quantity cannot be null");
		}
		
		logger.info(orderModel.getQuoteValue()+"---------");
		logger.info(orderModel.getQuantity()+"----------");
		CoinModel coin = coinRepo.findByCoinName(orderModel.getCoinName());
		if(coin==null) {
			throw new Exception("Coin Does Not Exists");
		}
		
		Integer flag=0;
		Optional<UserModel> userOp= userRepo.findById(orderModel.getUserId());
	
		Set<WalletModel> walletModel =userOp.get().getUserWallet();
		
		for(WalletModel type:walletModel) {
			if(type.getWalletType().equalsIgnoreCase("fiate")&&type.getShadowBalance()>=orderModel.getQuantity()) {
				flag=1;
			}
		}
		if(flag==0) {
			throw new Exception("You cannot place this order either wallet does not exists or you dont have much quantity to place a buy order");
		}
		if(userDetail.isPresent()) {//user does not exists
			if((Utility.isStringNull(orderModel.getCoinName()))) {
				
					orderModel.setOrderType("buy");
					orderModel.setStatus("Pending");
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					Long fee =(2*(orderModel.getQuantity()*orderModel.getQuoteValue()))/100;
					orderModel.setFee(fee);
					orderModel.setQuantity(orderModel.getQuantity());
					orderModel.setGrossAmount(orderModel.getQuantity()*orderModel.getQuoteValue()+fee);
					orderModel.setQuoteValue(orderModel.getQuoteValue());
					
					logger.info(orderModel.getGrossAmount()+"---------------");
					
					//update this users default wallets shadow balance
					Set<WalletModel> walletSet = userDetail.get().getUserWallet();
					for(WalletModel type:walletSet) {
						if(type.getWalletType().equals(Constant.FIATE)) {
							if(type.getBalance()<orderModel.getGrossAmount()) {
								throw new Exception(Constant.LOW_BALANCE);
							}
							Float shadowUpdate =type.getBalance()-orderModel.getGrossAmount();
							orderRepo.save(orderModel);
							type.setShadowBalance(shadowUpdate);
							walletRepo.save(type);
						}
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
	
	
	
	
	public Object createsellOrder(OrderModel orderModel)throws Exception {
		Optional<UserModel> userDetail = userRepo.findById(orderModel.getUserId());
		
		if(userDetail.get().getStatus()==false) {
			throw new Exception("First!! verify your account");
		}
		if(orderModel.getQuoteValue()==null) {
			throw new Exception("Quote value cannot be null");
		}
		if(orderModel.getQuantity()==null){
			throw new Exception("Quantity cannot be null");
		}
		if(orderModel.getCoinName()==null||orderModel.getCoinName()=="") {
			throw new Exception("Invalid!! coin name field cannot be null");
		}
		if(orderModel.getQuoteValue()<=0) {
			throw new Exception("Quote value should be greater than 0");
		}
		if(orderModel.getQuantity()<=0){
			throw new Exception("Quantity should be greater than 0");
		}
		CoinModel coin = coinRepo.findByCoinName(orderModel.getCoinName());
		if(coin==null) {
			throw new Exception("Coin Does Not Exists");
		} 
		Integer flag=0;
		Optional<UserModel> userOp= userRepo.findById(orderModel.getUserId());
		logger.info(userOp+"---------gg---------till here-------------------");
		Set<WalletModel> walletModel =userOp.get().getUserWallet();
		logger.info(walletModel+"------------------till here--rrr-----------------");
		for(WalletModel type:walletModel) {
			if(type.getWalletType().equalsIgnoreCase(orderModel.getCoinName())&&type.getShadowBalance()>=orderModel.getQuantity()) {
				flag=1;
			}
		}logger.info("------------------till here-------vv------------");
		if(flag==0) {
			throw new Exception("You cannot place this order either wallet does not exists or you dont have much quantity to place a sell order");
		}
		logger.info("------------------till here-------------fff------");
		if(userDetail.isPresent()) {//user does not exists
			if((Utility.isStringNull(orderModel.getCoinName()))) {
				if((orderModel.getQuantity()>0)) {
					orderModel.setOrderType("sell");
					orderModel.setStatus("Pending");
					orderModel.setUserModel(userDetail.get());
					orderModel.setOrderCreatedOn(new Date());
					Long fee =(2*(orderModel.getQuantity()*orderModel.getQuoteValue()))/100;
					orderModel.setFee(fee);
					orderModel.setQuantity(orderModel.getQuantity());
					orderModel.setGrossAmount(orderModel.getQuantity()*orderModel.getQuoteValue()+fee);
					orderModel.setQuoteValue(orderModel.getQuoteValue());
					orderModel.setCoinName(orderModel.getCoinName());
					logger.info(orderModel.getGrossAmount()+"---------------");
					
					//update this users default wallets shadow balance
					Set<WalletModel> walletSet = userDetail.get().getUserWallet();
					for(WalletModel type:walletSet) {
						if(type.getWalletType().equals(Constant.FIATE)) {
							orderRepo.save(orderModel);
						}
					}
					
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
