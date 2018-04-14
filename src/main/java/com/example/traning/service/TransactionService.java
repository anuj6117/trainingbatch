package com.example.traning.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Coin;
import com.example.traning.domain.Order;
import com.example.traning.domain.Register;
import com.example.traning.domain.Transaction;
import com.example.traning.domain.Wallet;
import com.example.traning.repository.CoinRepository;
import com.example.traning.repository.OrderRepository;
import com.example.traning.repository.TransectionRepository;
import com.example.traning.repository.WalletRepository;

@Service
public class TransactionService {

	private final static Logger logger=LoggerFactory.getLogger(TransactionService.class);
		@Autowired
		OrderRepository orderRepo;
		
		@Autowired
		WalletRepository walletRepo;
		
		@Autowired
		CoinRepository coinRepo ;
		
		@Autowired
		TransectionRepository transectionrepository;
		
	
	
	public Transaction transaction() {
		
		List<Order> buyersList =orderRepo.findByOrderTypeAndStatus("buyer","pending");
		
		System.out.println(" "+buyersList.size());
		List<Order> sellersList =orderRepo.findByOrderTypeAndStatus("seller","pending");
		
		if(buyersList.isEmpty())
			throw new NullPointerException("No Buyer Found");
		if(sellersList.isEmpty())
			throw new NullPointerException("No seller found");
		Transaction trans=null;
		Order buyer=null;
		Order seller=null;
		Boolean flag=true;
		
		 for(Order allBuyers:buyersList)
		{
			 System.out.println(" sahil");

			for(Order allSellers:sellersList) {
//				System.out.println(" "+allBuyers.getQuoteValue()+" "+allSellers.getQuoteValue() );
//				
//				System.out.println(allBuyers.getCoinName()+" "+allSellers.getCoinName());
//			System.out.println(allSellers.getCoinName()+"----"+allBuyers.getCoinName());
//			System.out.println(allBuyers.getCoinName().equalsIgnoreCase(allSellers.getCoinName()));
//			System.out.println(allBuyers.getTradingAmount()<=allSellers.getTradingAmount());
//			System.out.println(allSellers.getStatus().equals("pending"));
				if(allBuyers.getQuoteValue()>=allSellers.getQuoteValue() 
						&& allBuyers.getCoinName().equalsIgnoreCase(allSellers.getCoinName()) 
						&& allBuyers.getTradingAmount()<=allSellers.getTradingAmount() 
						&& allSellers.getStatus().equals("pending")) {
					
						flag=false;
						buyer=allBuyers;
						seller=allSellers;
										
				}
			}

		}	
		 if(flag) {
				throw new NullPointerException("No any seller found for buyer ");
		 }
		 
		if(buyer.getQuoteValue()==seller.getQuoteValue())
		{
			buyer.setStatus("completed");
			orderRepo.save(buyer);
			System.err.println("this is buyers transection"+buyer.getTradingAmount());
			seller.setStatus("Completed");
			orderRepo.save(seller);
		}
		
		if(buyer.getQuoteValue()<seller.getQuoteValue()) {
			buyer.setStatus("pending");
			seller.setTradingAmount(seller.getTradingAmount()-buyer.getTradingAmount());
			seller.setStatus("Completed");
		}
		
		seller.setTradingAmount(seller.getTradingAmount()-buyer.getTradingAmount());
		seller.setStatus("pending");
		orderRepo.save(seller);
		
		buyer.setStatus("completed");
		orderRepo.save(buyer);
		Register buyeruserData=buyer.getRegister();
		Register selleruserData=seller.getRegister();
		
		
		Wallet buyerDefaultWallet=null;
		Wallet buyerCurrentWallet=null;
		
		if(buyer.getStatus().equals("completed"))
		{
			List<Wallet> buyerWallets=buyeruserData.getWall();
			
			for(Wallet buyerWall : buyerWallets)
			{
				buyerDefaultWallet=buyerWallets.get(0);
				if(buyerWall.getWalletType().equals(buyer.getCoinName()))
				{
					buyerCurrentWallet=buyerWall;
				}
			}
			buyerCurrentWallet.setAmount(buyerCurrentWallet.getShadowBalance());
			buyerDefaultWallet.setAmount(buyerDefaultWallet.getShadowBalance());	
			walletRepo.save(buyerDefaultWallet);
			walletRepo.save(buyerCurrentWallet);
			logger.error("this is buyers transection"+buyer.getTradingAmount());

			Coin coin=coinRepo.findByCoinName(seller.getCoinName());
			
			float newIntialSupply=coin.getInitialSupply()-buyer.getTradingAmount();
			coin.setInitialSupply(newIntialSupply);
			
			
			float profyy=newIntialSupply * (float) (2/100);
			coin.setProfit(profyy);
			
			logger.info("initialSuplly="+newIntialSupply+"  Profit="+profyy);
			
			float newCoinInInR=coin.getPrice()*newIntialSupply+profyy;
			coin.setCoinInINR(newCoinInInR);
			System.out.println("this is buyers********************transection  "+buyer.getTradingAmount());
			coinRepo.save(coin);
			Transaction transactionModel=new Transaction();
			transactionModel.setCointype(buyer.getCoinName());
			System.out.println("this is buyers*************njhgfd*******transection  "+buyer.getTradingAmount());
			transactionModel.setInQuantity(buyer.getTradingAmount());
			transactionModel.setStatus("complete");
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setNetAmount(newIntialSupply*coin.getPrice());
			transactionModel.setTransationFee(2);
			transactionModel.setExchangeRate(5);
			
			float grossAmount=(buyer.getGrossAmount()+seller.getGrossAmount())/2;
			transactionModel.setGrossAmount(grossAmount);
			
			transactionModel.setBuyerId(buyeruserData.getuserId());
			transactionModel.setSellerId(selleruserData.getuserId());
			transactionModel.setDescription("None");
			System.out.println(transactionModel+"**********");
			trans =transectionrepository.save(transactionModel);
		
		
		}
		return trans;
	}
   
	public List<Transaction> showTransaction() {
		
		List<Transaction> allTrans=transectionrepository.findAll();
		
		return allTrans;
	}
}