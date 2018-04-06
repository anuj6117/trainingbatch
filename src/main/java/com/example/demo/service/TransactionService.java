package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoinModel;
import com.example.demo.model.OrderModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.CoinRepository;
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
	private CoinRepository coinRepo;
	
	@Autowired
	private WalletRepository walletRepo;
	List<OrderModel> buyerList= new ArrayList<OrderModel>();
	TransactionModel transactionModel = new TransactionModel();
	
public Object createTransaction() {
		
		List<OrderModel> buyerOrder=orderRepo.findByOrderType("buy");;
		List<OrderModel> sellerOrder;
		CoinModel coinSeller;
	
		
		for(OrderModel buyorder : buyerOrder) {
			  
			  sellerOrder = orderRepo.findSeller(buyorder.getCoinName(),"sell",buyorder.getQuoteValue(),buyorder.getQuantity());
			  Collections.sort(sellerOrder,new QuoteValueComparator());
			  OrderModel userSeller = sellerOrder.get(0);
			  coinSeller = coinRepo.findByCurrency(buyorder.getCoinName(), buyorder.getQuantity());
			System.out.println(userSeller.getQuoteValue()+"\\\\\\\\\\\\\\\\\\\\\\\\");
				if(userSeller.getQuoteValue()>=coinSeller.getPrice()) {
					//seller will coinseller
					if(coinSeller.getInitialSupply()==buyorder.getQuantity()) {
						//madeTransaction(buyorder, coinSeller);
						updateOrderStatus(buyorder);//completed
						//update status of coin seller's initial supply--should be 0
					
						return "sellorder";
					}
					else if(coinSeller.getInitialSupply()>buyorder.getQuantity()) {
					//	madeTransaction(buyorder, coinSeller);
					   // updateOrderStatus(buyorder);//completed
					    //update status of coin seller's initial supply should be deducted
						return "success444";
						
					}
					else if(coinSeller.getInitialSupply()<buyorder.getQuantity()) {
						//madeTransaction(buyorder, coinSeller);
						//updateOrderAmount(buyorder,buyorder.getQuantity()-coinSeller.getInitialSupply());
						//update status of coin seller's initial supply
						return "success8888";
					}
				}
				else {
					//seller will userseller
				}
			
		}
		return "1234567898765432";
	}
	
	/*public Object createTransaction() {
		
		List<OrderModel> buyerOrder;
		List<OrderModel> sellerOrder=orderRepo.findByOrderType("sell");
		CoinModel coin = coinRepo.findByCurrency(, initialSupply)
		
		for(OrderModel sellorder : sellerOrder) {
			
			buyerOrder = orderRepo.findBuyer(sellorder.getCoinName(),"buy",sellorder.getQuoteValue());
			  Collections.sort(buyerOrder,new QuoteValueComparator());
			  OrderModel buyorder = buyerOrder.get(0);
			System.out.println(buyorder.getQuoteValue()+"\\\\\\\\\\\\\\\\\\\\\\\\");
				if(sellorder.getQuantity()==buyorder.getQuantity()) {
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
				else if(sellorder.getQuantity()>buyorder.getQuantity()) {
					madeTransaction(buyorder, sellorder);
				    updateOrderStatus(buyorder);
				    updateOrderAmount(sellorder, buyorder.getQuantity());
					return "success444";
					
				}
				else if(sellorder.getQuantity()<buyorder.getQuantity()) {
					madeTransaction(buyorder, sellorder);
					updateOrderAmount(buyorder, sellorder.getQuantity());
					updateOrderStatus(sellorder);
					return "success8888";
				}
			
		}
		return "1234567898765432";
	}*/
	
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
			walletModel.setBalance(orderModel.getQuantity());
			walletModel.setShadowBalance(orderModel.getQuantity());
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
        transactionModel.setTransactionFee(23);
		transactionModel.setGrossAmount((sellerModel.getQuoteValue()*buyerModel.getQuantity())+transactionModel.getTransactionFee());
		transactionModel.setNetAmount(buyerModel.getQuantity());
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
		orderModel.setQuantity(orderModel.getQuantity());
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}
	
	private void updateOrderAmount(OrderModel orderModel,Integer tradingamount) {
		orderModel.setStatus(orderModel.getStatus());
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setQuantity(orderModel.getQuantity()-tradingamount);
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}
	

}
