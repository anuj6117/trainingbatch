package com.example.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.userDTO.OrderDTO;
import com.example.demo.dto.userDTO.WalletDTO;
import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.model.userModel.OrderModel;
import com.example.demo.model.userModel.TransactionModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.CoinManagementRepository;
import com.example.demo.repoINterface.OrderRepository;
import com.example.demo.repoINterface.TransactionRepository;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.WalletRepostiory;

import javassist.expr.NewArray;

@Service
public class WalletServices {
@Autowired
TransactionRepository transdata;
	@Autowired
	WalletRepostiory walletData;
@Autowired 
CoinManagementRepository coinDate;
	@Autowired
	UserRepository userData;
	@Autowired
	OrderRepository orderRepository;	
	public String addWalletToUser(WalletDTO data) {
		long leftLimit = 1L;
		long rightLimit = 10000000000L;
		boolean check = true;
		long randemId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		CoinManagementModel model = coinDate.findByCoinName(data.getWalletType());
		if (model == null)
			throw new NullPointerException("coin not available");
		List<WalletModel> walletTypeOfuser = userModel.getWalletModel();
		for (WalletModel getwallrttype : walletTypeOfuser) {
			if (data.getWalletType().equals(getwallrttype.getWalletType())) {
				check = false;
				throw new RuntimeException("wallet allready exist");
				// break;
			}
		}
		if (check) {
			WalletModel walletModel = new WalletModel();
			walletModel.setAmount(data.getAmount());
			walletModel.setWalletType(data.getWalletType());
			walletModel.setShadoBalance(data.getAmount());
			walletModel.setUserdata(userModel);
			walletModel.setRandemId(randemId);
			userModel.getWalletModel().add(walletModel);
			UserModel result = userData.save(userModel);
			if (result != null)
				return "success";
		}
		return "error";
	}

	// ---------------------
	
	public OrderModel AddMoneyInWallet(WalletDTO data) {
		WalletModel walletmodel =giveWallettype(data);
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		if (walletmodel == null)
			throw new NullPointerException("id or wallet not correct");
		OrderModel orderModel=new OrderModel();
		orderModel.setCoinName(data.getWalletType());
		orderModel.setGrossAmount(data.getAmount());
		orderModel.setOrderType("deposit");
		orderModel.setOrderCreatedOn(new Date());
		orderModel.setStatus("pending");
		orderModel.setCoinQuantity(0);
		orderModel.setFee(0);
		orderModel.setPrice(0);
		orderModel.setUser(userModel);
		userModel.getOrderModel().add(orderModel);
		OrderModel result = orderRepository.save(orderModel);
		if (result != null)
			return orderModel;
		return null;
		
	}
//	-public WalletModel AddMoneyInWallet(WalletDTO data) {
//		WalletModel model =giveWallettype(data);
//		if (model == null)
//			throw new NullPointerException("id or wallet not correct");
//		int walletAmount = model.getAmount();
//		walletAmount = walletAmount + data.getAmount();
//		model.setAmount(model.getAmount()+data.getAmount());
//		model.setShadoBalance(model.getShadoBalance()+data.getAmount());
//		WalletModel result = walletData.save(model);
//		if (result != null)
//			return result;
//		return nlull;
//	}	

	// ---------------------
	public OrderModel withdrawMoneyInWallet(WalletDTO data) {
		WalletModel model = giveWallettype(data);
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		if (model == null)
			throw new NullPointerException("id or wallet type not found");
		int walletAmount = model.getAmount();
		walletAmount = walletAmount - data.getAmount();
		if(walletAmount<0)
			throw new RuntimeException("you dont hava enough amount");
		int fee=(data.getAmount()*2)/100;
		OrderModel orderModel=new OrderModel();
		orderModel.setCoinName(data.getWalletType());
		orderModel.setGrossAmount(data.getAmount()+fee);
		orderModel.setOrderType("withdraw");
		orderModel.setOrderCreatedOn(new Date());
		orderModel.setStatus("pending");
		orderModel.setCoinQuantity(0);
		orderModel.setFee(fee);
		orderModel.setPrice(0);
		orderModel.setUser(userModel);
		userModel.getOrderModel().add(orderModel);
		OrderModel result = orderRepository.save(orderModel);
		if (result != null)
			return orderModel;
		return null;
	}
	public WalletModel giveWallettype(WalletDTO data)
	{
		if(data.getAmount()==null||data.getAmount()<0)
			throw new RuntimeException("amount not be empty or less then 0");
		UserModel userModel = userData.findOne(data.getUserId());
		if (userModel == null)
			throw new NullPointerException("id not found");
		List<WalletModel> walletTypeOfuser = userModel.getWalletModel();
		for (WalletModel getwallrttype : walletTypeOfuser) {
			if (data.getWalletType().equals(getwallrttype.getWalletType())) 
				return getwallrttype;
		}
		return null;
	}
	
	
	public WalletModel withdrawAndDepositMoneyInWallet(OrderDTO data)
	{
		if(data.getOrderId()==null||data.getStatus()==null||data.getStatus().trim().length()==0)
			throw new RuntimeException("id or status not valid");
		OrderModel orderModel=orderRepository.findOne(data.getOrderId());
		TransactionModel transactionModel=new TransactionModel();
		if(orderModel==null)
			throw new RuntimeException("id not found");
		if(!orderModel.getStatus().equals("pending"))
			throw new RuntimeException("status alrady updated");
		if(data.getStatus().equals("approved"))
		{
				WalletModel walletModel=null;
			List<WalletModel> walletModel1=walletData.findAllByWalletType(orderModel.getCoinName());
			for(WalletModel model:walletModel1)
			{
				if(model.getWalletType().equals(orderModel.getCoinName())&&model.getUserdata().getUserId()==orderModel.getUser().getUserId())
			walletModel=model;
			}
			if(orderModel.getOrderType().equalsIgnoreCase("deposit")) {
			walletModel.setAmount(walletModel.getAmount()+orderModel.getGrossAmount());
			walletModel.setShadoBalance(walletModel.getShadoBalance()+orderModel.getGrossAmount());
			}
			else if(orderModel.getOrderType().equals("withdraw"))
			{
				if((walletModel.getAmount()-orderModel.getGrossAmount())<0)
					throw new RuntimeException("you dont have enough wallet");
				walletModel.setAmount(walletModel.getAmount()-orderModel.getGrossAmount());
				walletModel.setShadoBalance(walletModel.getShadoBalance()-orderModel.getGrossAmount());
			}
			
			transactionModel.setBuyerId(orderModel.getUser().getUserId());
			transactionModel.setCoinQuantity(0);
			transactionModel.setCointype(orderModel.getCoinName());
			transactionModel.setDescription("approved by admin");
			transactionModel.setGrossAmount(orderModel.getGrossAmount());
			transactionModel.setNetAmount(0);
			transactionModel.setSellerId(0l);
			transactionModel.setStatus("approved");
			transactionModel.setPrice(0);
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setTransationFee(orderModel.getFee());
			transdata.save(transactionModel);
			orderModel.setStatus(data.getStatus());
			orderRepository.save(orderModel);
			return walletModel;
			
		}
		else if(data.getStatus().equals("rejected"))
		{
			transactionModel.setBuyerId(orderModel.getUser().getUserId());
			transactionModel.setCoinQuantity(0);
			transactionModel.setCointype(orderModel.getCoinName());
			transactionModel.setDescription("reject by admin");
			transactionModel.setGrossAmount(orderModel.getGrossAmount());
			transactionModel.setNetAmount(0);
			transactionModel.setPrice(0);
			transactionModel.setSellerId(0l);
			transactionModel.setStatus("rejected");
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setTransationFee(orderModel.getFee());
			orderModel.setStatus(data.getStatus());
			orderRepository.save(orderModel);
			transdata.save(transactionModel);
		}
		return null;
		
	}



public List<OrderModel> getAllWalletHistory(Long id,String type)
{
	UserModel user=userData.findByUserId(id);
	//OrderModel orderData=orderRepository.findOneUserData(user.getUserId());
	List<WalletModel> data=user.getWalletModel();
	int j=0;
	for(int i=0;i<data.size();i++)
	{System.out.println(data.get(i).getWalletType());
		if(type.equals(data.get(i).getWalletType())) {
			j=i;
		System.out.println(j);}
		else;
			//throw new RuntimeException("Wallet not available");
	}
	List<OrderModel> result=orderRepository.findAllWalletHistory(user.getWalletModel().get(j).getWalletType(),user.getUserId());
	return result;
	
}
}

