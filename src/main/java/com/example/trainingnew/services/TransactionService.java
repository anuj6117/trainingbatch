package com.example.trainingnew.services;

import java.util.Date;
import java.util.List;

import org.hibernate.internal.CoordinatingEntityNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.model.OrderModel;
import com.example.trainingnew.model.TransactionModel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.model.Walletmodel;
import com.example.trainingnew.reprository.CoinRepo;
import com.example.trainingnew.reprository.OrderReprository;
import com.example.trainingnew.reprository.TransactionRepo;
import com.example.trainingnew.reprository.WalletRepo;

@Service
public class TransactionService {

	private final static Logger logger=LoggerFactory.getLogger(TransactionService.class);
		@Autowired
		OrderReprository orderRepo;
		
		@Autowired
		WalletRepo walletRepo;
		
		@Autowired
		CoinRepo coinRepo ;
		
		@Autowired
		TransactionRepo transactionRepo;
		
	
	
	public TransactionModel transaction() {
		
		List<OrderModel> buyersList =orderRepo.findByOrderTypeAndStatus("buy","pending");
		
		List<OrderModel> sellersList =orderRepo.findByOrderTypeAndStatus("sell","pending");
		
		if(buyersList.isEmpty())
			throw new NullPointerException("No Buyer Found");
		if(sellersList.isEmpty())
			throw new NullPointerException("No seller found");
		TransactionModel trans=null;
		OrderModel buyer=null;
		OrderModel seller=null;
		Boolean flag=true;
		for(OrderModel allBuyers:buyersList)
		{

			for(OrderModel allSellers:sellersList) {
				
				if(allBuyers.getQuoteValue()>=allSellers.getQuoteValue() && allBuyers.getCoinName().equals(allSellers.getCoinName()) && allBuyers.getAmount()<=allSellers.getAmount() && allSellers.getStatus().equals("pending")) {
					{
						flag=false;
						buyer=allBuyers;
						seller=allSellers;
					}					
				}
			}
			
			if(flag)
				throw new NullPointerException("No any seller found for buyer ");

		}	

		if(buyer.getQuoteValue()==seller.getQuoteValue())
		{
			buyer.setStatus("completed");
			orderRepo.save(buyer);
			
			seller.setStatus("Completed");
			orderRepo.save(seller);
		}
		
		if(buyer.getQuoteValue()<seller.getQuoteValue()) {
			buyer.setStatus("pending");
			seller.setAmount(seller.getAmount()-buyer.getAmount());
			seller.setStatus("Completed");
		}
		
		seller.setAmount(seller.getAmount()-buyer.getAmount());
		seller.setStatus("pending");
		orderRepo.save(seller);
		
		buyer.setStatus("completed");
		orderRepo.save(buyer);
		UserModel buyeruserData=buyer.getUserModelInOrderModel();
		UserModel selleruserData=seller.getUserModelInOrderModel();
		
		
		Walletmodel buyerDefaultWallet=null;
		Walletmodel buyerCurrentWallet=null;
		
		if(buyer.getStatus().equals("completed"))
		{
			List<Walletmodel> buyerWallets=buyeruserData.getWallets();
			
			for(Walletmodel buyerWall : buyerWallets)
			{
				buyerDefaultWallet=buyerWallets.get(0);
				if(buyerWall.getWalletType().equals(buyer.getCoinName()))
				{
					buyerCurrentWallet=buyerWall;
				}
			}
			buyerCurrentWallet.setBalance(buyerCurrentWallet.getShadowBalance());
			buyerDefaultWallet.setBalance(buyerDefaultWallet.getShadowBalance());	
			walletRepo.save(buyerDefaultWallet);
			walletRepo.save(buyerCurrentWallet);
			

			Coinmodel coin=coinRepo.findByCoinName(seller.getCoinName());
			
			Double newIntialSupply=coin.getInitialSupply()-buyer.getAmount();
			coin.setInitialSupply(newIntialSupply);
			
			logger.info("fee"+coin.getFee());
			
			Double profyy=coin.getInitialSupply()*(coin.getFee()/100);
			coin.setProfit(profyy);
			
			logger.info("initialSuplly="+newIntialSupply+"  Profit="+profyy);
			
			Double newCoinInInR=coin.getPrice()*newIntialSupply+profyy;
			coin.setCoinInINR(newCoinInInR);
			
			coinRepo.save(coin);
			
			TransactionModel transactionModel=new TransactionModel();
			transactionModel.setCointype(buyer.getCoinName());
			transactionModel.setInQuantity(buyer.getAmount());
			transactionModel.setStatus("complete");
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setNetAmount(newIntialSupply*coin.getPrice());
			transactionModel.setTransationFee(coin.getFee());
			transactionModel.setExchangeRate(5.0);
			
			Double grossAmount=(buyer.getGrossAmount()+seller.getGrossAmount())/2;
			transactionModel.setGrossAmount(grossAmount);
			
			transactionModel.setBuyerId(buyeruserData.getUserId());
			transactionModel.setSellerId(selleruserData.getUserId());
			transactionModel.setDescription("None");
			
			trans= transactionRepo.save(transactionModel);
		
		}
		return trans;
	}

}
