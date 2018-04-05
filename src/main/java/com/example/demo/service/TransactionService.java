package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utils.QuoteValueComparator;
import com.example.demo.utils.Utility;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepo;
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private WalletRepository walletRepo;
	List<OrderModel> buyerList= new ArrayList<OrderModel>();
	TransactionModel transactionModel = new TransactionModel();
	public Object createTransaction() {
		
		List<OrderModel> buyerOrder;
		List<OrderModel> sellerOrder=orderRepo.findByOrderType("sell");
		
		for(OrderModel sellorder : sellerOrder) {
			buyerOrder = orderRepo.findBuyer(sellorder.getCoinName(),"buy",(Float)sellorder.getQuoteValue());
			  Collections.sort(buyerOrder,new QuoteValueComparator());
			  OrderModel buyorder = buyerOrder.get(0);
			System.out.println(buyorder.getQuoteValue()+"\\\\\\\\\\\\\\\\\\\\\\\\");
				if(sellorder.getTradingAmount()==buyorder.getTradingAmount()) {
					madeTransaction(buyorder, sellorder);
					updateOrderStatus(buyorder);
					updateOrderStatus(sellorder);
					//get bit coin name of buyer
						//check its wallet availability
					       //if not present create a wallet 
					              // update wallet with currency value
					              //deduct that amount form the fiate wallet
					return "sellorder";
				}
				else if(sellorder.getTradingAmount()>buyorder.getTradingAmount()) {
					madeTransaction(buyorder, sellorder);
				    updateOrderStatus(buyorder);
				    updateOrderAmount(sellorder, buyorder.getTradingAmount());
					return "success444";
					
				}
				else if(sellorder.getTradingAmount()<buyorder.getTradingAmount()) {
					madeTransaction(buyorder, sellorder);
					updateOrderAmount(buyorder, sellorder.getTradingAmount());
					updateOrderStatus(sellorder);
					return "success8888";
				}
			
		}
		return "1234567898765432";
	}
	
	//get bit coin name of buyer
	//check its wallet availability
       //if not present create a wallet 
              // update wallet with currency value
              //deduct that amount form the fiat wallet
	
	private Object createUpdateWallet(OrderModel orderModel,Float amount) {
		String coinName = orderModel.getCoinName();
		UserModel userDetail = orderModel.getUserModel();
		Set<WalletModel> walletSet = userDetail.getUserWallet();
		Integer flag=0;
		for(WalletModel type : walletSet) {
			if(coinName.equalsIgnoreCase(type.getWalletType())) {
				flag=1;
			}
		}
		if(flag==1) {
			return "Wallet Already exist";
		}
		else {
			//add wallet
			WalletModel walletModel = new WalletModel();
			walletModel.setUserModel(userDetail);
			walletModel.setWalletHash(Utility.generateId(100));
			walletModel.setWalletType(coinName);
			walletModel.setBalance(orderModel.getTradingAmount());
			walletModel.setShadowBalance(orderModel.getTradingAmount());
			walletRepo.save(walletModel);
			for(WalletModel type : walletSet) {
				if("fiate".equalsIgnoreCase(type.getWalletType())) {
					type.setBalance(amount);//balance deducted
				}
			}
			
		}
		return "success";
		
	}
	private  void madeTransaction(OrderModel buyerModel,OrderModel sellerModel) {
		//create transaction
		transactionModel.setBuyerId(buyerModel.getOrderId());
		transactionModel.setCurrencyType(buyerModel.getCoinName());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction Made");
		transactionModel.setExchangeRate(sellerModel.getQuoteValue());
        transactionModel.setTransactionFee(23.0f);
		transactionModel.setGrossAmount((sellerModel.getQuoteValue()*buyerModel.getTradingAmount())+transactionModel.getTransactionFee());
		transactionModel.setNetAmount(buyerModel.getTradingAmount());
		transactionModel.setSellerId(sellerModel.getOrderId());
		transactionModel.setStatus("Pending");
		transactionRepo.save(transactionModel);
	}
	private void updateOrderStatus(OrderModel orderModel) {
		orderModel.setStatus("Completed");
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setTradingAmount(orderModel.getTradingAmount());
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}
	
	private void updateOrderAmount(OrderModel orderModel,Float tradingamount) {
		orderModel.setStatus(orderModel.getStatus());
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setTradingAmount(orderModel.getTradingAmount()-tradingamount);
		orderModel.setUserModel(orderModel.getUserModel());
	}
	

}
