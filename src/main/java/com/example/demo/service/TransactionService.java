package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	List<OrderModel> buyerList = new ArrayList<OrderModel>();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * public Object createTransaction() {
	 * 
	 * List<OrderModel> buyerOrder=orderRepo.findByOrderType("buy");
	 * List<OrderModel> sellerOrder; CoinModel coinSeller;
	 * 
	 * 
	 * for(OrderModel buyorder : buyerOrder) {
	 * 
	 * sellerOrder =
	 * orderRepo.findSeller(buyorder.getCoinName(),"sell",buyorder.getQuoteValue(),
	 * buyorder.getQuantity()); Collections.sort(sellerOrder,new
	 * QuoteValueComparator()); OrderModel userSeller = sellerOrder.get(0);
	 * logger.info(sellerOrder.+"------------4444----------");
	 * logger.info("--asdfgbhnmnb----------4444----------"); coinSeller =
	 * coinRepo.findByCurrency(buyorder.getCoinName(), buyorder.getQuantity());
	 * System.out.println(userSeller.getQuoteValue()+"\\\\\\\\\\\\\\\\\\\\\\\\");
	 * 
	 * if(userSeller.getQuoteValue()>=coinSeller.getPrice()) { //seller will
	 * coinseller if(coinSeller.getInitialSupply()==buyorder.getQuantity()) {
	 * logger.info(coinSeller.getInitialSupply()+"------------4444----------");
	 * //madeTransaction(buyorder, coinSeller);
	 * updateOrderStatus(buyorder);//completed //update status of coin seller's
	 * initial supply--should be 0
	 * 
	 * return "sellorder"; } else
	 * if(coinSeller.getInitialSupply()>buyorder.getQuantity()) { //
	 * madeTransaction(buyorder, coinSeller); //
	 * updateOrderStatus(buyorder);//completed //update status of coin seller's
	 * initial supply should be deducted
	 * logger.info(coinSeller.getInitialSupply()+"-----------3333-----------");
	 * return "success444";
	 * 
	 * } else if(coinSeller.getInitialSupply()<buyorder.getQuantity()) {
	 * //madeTransaction(buyorder, coinSeller);
	 * //updateOrderAmount(buyorder,buyorder.getQuantity()-coinSeller.
	 * getInitialSupply()); //update status of coin seller's initial supply
	 * logger.info(coinSeller.getInitialSupply()+"--------2222--------------");
	 * return "success8888"; } } else { //seller will userseller }
	 * 
	 * } return "1234567898765432"; }
	 */

	/*
	 * public Object createTransaction() {
	 * 
	 * List<OrderModel> buyerOrder; List<OrderModel>
	 * sellerOrder=orderRepo.findByOrderType("sell"); CoinModel coin =
	 * coinRepo.findByCurrency(, initialSupply)
	 * 
	 * for(OrderModel sellorder : sellerOrder) {
	 * 
	 * buyerOrder =
	 * orderRepo.findBuyer(sellorder.getCoinName(),"buy",sellorder.getQuoteValue());
	 * Collections.sort(buyerOrder,new QuoteValueComparator()); OrderModel buyorder
	 * = buyerOrder.get(0);
	 * System.out.println(buyorder.getQuoteValue()+"\\\\\\\\\\\\\\\\\\\\\\\\");
	 * if(sellorder.getQuantity()==buyorder.getQuantity()) {
	 * madeTransaction(buyorder, sellorder); updateOrderStatus(buyorder);
	 * updateOrderStatus(sellorder); //get bit coin name of buyer //check its wallet
	 * availability //if not present create a wallet // update wallet with currency
	 * value //deduct that amount form the fiate wallet return "sellorder"; } else
	 * if(sellorder.getQuantity()>buyorder.getQuantity()) {
	 * madeTransaction(buyorder, sellorder); updateOrderStatus(buyorder);
	 * updateOrderAmount(sellorder, buyorder.getQuantity()); return "success444";
	 * 
	 * } else if(sellorder.getQuantity()<buyorder.getQuantity()) {
	 * madeTransaction(buyorder, sellorder); updateOrderAmount(buyorder,
	 * sellorder.getQuantity()); updateOrderStatus(sellorder); return "success8888";
	 * }
	 * 
	 * } return "1234567898765432"; }
	 */

	// get bit coin name of buyer
	// check its wallet availability
	// if not present create a wallet
	// update wallet with currency value
	// deduct that amount form the fiat wallet
	
	public Object getalltransaction() {
		return transactionRepo.findAll();
	}

	public Object mainTransaction() throws Exception {

		List<OrderModel> buyerOrder = orderRepo.findBuyer("buy","Pending");
		List<CoinModel> allCurrency = coinRepo.findAll();
		List<OrderModel> sellerOrder;
		if(buyerOrder.isEmpty()) {
			throw new Exception("Buy order not available");
		}
		
		CoinModel coin;
		for (OrderModel buyorder : buyerOrder) {
			UserModel user = buyorder.getUserModel();
			Integer buyerId = user.getUserid();
			/*if(user.getStatus()== false) {
				throw new Exception("Buyer First Complete the Verification Process");
			}*/
			logger.info( buyorder.getQuantity()+"---------------------------------------------------------------");
			logger.info(buyorder.getCoinName()+"---------------------------------------------------------------"+buyorder.getQuoteValue());
			coin = getListByName(allCurrency, buyorder.getCoinName());
			logger.info(coin.getCoinName()+"--------------------");
			
			sellerOrder = orderRepo.findSeller(buyorder.getCoinName(),"sell",buyorder.getQuoteValue(),"Pending"); 
			if(sellerOrder.isEmpty()) {
				logger.info(sellerOrder.isEmpty()+"--------------------------------after sell-------------------------------");
				createTransaction();
				continue;
				}
			
			Collections.sort(sellerOrder,new QuoteValueComparator()); 
			OrderModel userSeller = sellerOrder.get(0);
			logger.info(sellerOrder.get(0)+"----------------------------------12345567890------------------------------");
			if(sellerOrder.isEmpty()||coin.getPrice()>userSeller.getQuoteValue()) {
				//made transaction via coin
				logger.info("buyerlist-----into coin --------------");
				madeTransactionByCoin(buyorder, coin);
				updateCurrency(coin, buyorder);
				createUpdateWallet(buyorder, buyorder.getGrossAmount());
				updateOrderStatus(buyorder);
			}
			else if(coin.getPrice()<=userSeller.getQuoteValue()||coin.getInitialSupply()<buyorder.getQuantity()) {
				//made transaction via user seller	
				UserModel userModel = userSeller.getUserModel();
				if(userModel.getStatus()==false) {
					throw new Exception("Seller First Complete the Verification Process");
				}
				Integer sellerId = userModel.getUserid();
				if(buyerId==sellerId) {
					throw new Exception("Buyer and seller can't be same");
				}
				 if(userSeller.getQuantity()==buyorder.getQuantity()) {
					  madeTransactionByUser(buyorder, userSeller);
					  updateOrderStatus(buyorder);
					  createUpdateWallet(buyorder, buyorder.getGrossAmount());
					  updateOrderStatus(userSeller);
						logger.info("buyerlist-----via user seller 1--------------");
					  return "sellorder"; 
					  } 
				 else if(userSeller.getQuantity()>buyorder.getQuantity()) {
					  madeTransactionByUser(buyorder, userSeller); 
					  updateOrderStatus(buyorder);
					  createUpdateWallet(buyorder, buyorder.getGrossAmount());
					  updateOrderAmount(userSeller,buyorder.getQuantity()); 
					  logger.info("buyerlist-----via user seller 2--------------");
					  return "success444";
					  
					  } 
				 else if(userSeller.getQuantity()<buyorder.getQuantity()) {
					  madeTransactionByUser(buyorder, userSeller); 
					  updateOrderAmount(buyorder,userSeller.getQuantity()); 
					  createUpdateWallet(buyorder, buyorder.getGrossAmount());
					  updateOrderStatus(userSeller); 
					  logger.info("buyerlist-----via user seller 3--------------");
					  return "success8888";
					  }
					 
			}
			logger.info("----loop running----------");
		}
		return "1234567898765432";
	}
	public Object createTransaction() throws Exception {

		List<OrderModel> buyerOrder = orderRepo.findBuyer("buy","Pending");
		List<CoinModel> allCurrency = coinRepo.findAll();
		CoinModel coin;
		for (OrderModel buyorder : buyerOrder) {
			coin = getListByName(allCurrency, buyorder.getCoinName());
			if (buyorder.getQuoteValue() < coin.getPrice()) {
				logger.info(buyorder.getOrderId()+"---------------");
				logger.info("Your quote is too low!!");
			} else if (buyorder.getQuoteValue() >= coin.getPrice()) {
				logger.info("enter into loop--------------------------------------");
				madeTransactionByCoin(buyorder, coin);
				updateCurrency(coin, buyorder);
				createUpdateWallet(buyorder, buyorder.getGrossAmount());
				updateOrderStatus(buyorder);
			}
		}
		return "1234567898765432";
	}

	public CoinModel getListByName(List<CoinModel> list, String name) {
		CoinModel coinModel = new CoinModel();
		for (CoinModel type : list) {
			if (type.getCoinName().equals(name)) {
				coinModel = type;
				break;

			}
		}
		return coinModel;
	}

	public void updateCurrency(CoinModel coinModel, OrderModel orderModel) {
		coinModel.setInitialSupply(coinModel.getInitialSupply() - orderModel.getQuantity());
		coinModel.setProfit(((orderModel.getQuantity() * coinModel.getPrice()) * coinModel.getFees()) / 100);
		coinModel.setINRConvergent(orderModel.getQuantity() * coinModel.getPrice()
				+ ((orderModel.getQuantity() * coinModel.getPrice()) * coinModel.getFees()) / 100);
		coinRepo.save(coinModel);
	}

	private Object createUpdateWallet(OrderModel orderModel, Long amount) {
		String coinName = orderModel.getCoinName();
		UserModel userDetail = orderModel.getUserModel();
		Set<WalletModel> walletSet = userDetail.getUserWallet();
		Integer flag = 0;
		Integer walletId = 0;
		for (WalletModel type : walletSet) {
			if (coinName.equalsIgnoreCase(type.getWalletType())) {
				flag = 1;
				walletId = type.getWalletId();
			}
		}
		if (flag == 1) {
			// update wallet
			Optional<WalletModel> walletOptionalObject = walletRepo.findById(walletId);
			WalletModel walletModel = walletOptionalObject.get();
			walletModel.setBalance(walletModel.getBalance() + orderModel.getQuantity());
			walletModel.setShadowBalance(walletModel.getBalance() + orderModel.getQuantity());
			walletRepo.save(walletModel);
		} else {
			// add wallet
			WalletModel walletModel = new WalletModel();
			walletModel.setUserModel(userDetail);
			walletModel.setWalletHash(Utility.generateId(100));
			walletModel.setWalletType(coinName);
			walletModel.setBalance(orderModel.getQuantity());
			walletModel.setShadowBalance(orderModel.getQuantity());
			walletRepo.save(walletModel);

		}
		for (WalletModel type : walletSet) {
			if ("fiate".equalsIgnoreCase(type.getWalletType())) {
				type.setBalance(type.getShadowBalance());// balance deducted
			}
		}
		return "success";

	}

	private void madeTransactionByCoin(OrderModel buyerModel, CoinModel coinModel) {
		// create transaction
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setBuyerId(buyerModel.getOrderId());
		transactionModel.setCurrencyType(buyerModel.getCoinName());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction Made by coin Seller");
		transactionModel.setExchangeRate(coinModel.getPrice());
		transactionModel.setTransactionFee(23l);
		Long feeAmount = ((coinModel.getPrice() * buyerModel.getQuantity()) * 23) / 100;
		transactionModel.setGrossAmount((coinModel.getPrice() * buyerModel.getQuantity()) + feeAmount);
		transactionModel.setNetAmount(buyerModel.getQuantity());
		transactionModel.setSellerId(coinModel.getCoinId());
		transactionModel.setStatus("Completed");
		transactionRepo.save(transactionModel);
	}

	private void madeTransactionByUser(OrderModel buyerModel, OrderModel sellerModel) {
		// create transaction
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setBuyerId(buyerModel.getOrderId());
		transactionModel.setCurrencyType(buyerModel.getCoinName());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction Made by userSeller");
		transactionModel.setExchangeRate(sellerModel.getQuoteValue());
		Long fee =2l;
		Long grossAmount = ((sellerModel.getQuoteValue() * buyerModel.getQuantity())*fee)/100;
		transactionModel.setTransactionFee(23l);
		transactionModel.setGrossAmount(
				(sellerModel.getQuoteValue() * buyerModel.getQuantity()) + grossAmount);
		transactionModel.setNetAmount(buyerModel.getQuantity());
		transactionModel.setSellerId(sellerModel.getOrderId());
		transactionModel.setStatus("Completed");
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

	private void updateOrderAmount(OrderModel orderModel, Long tradingamount) {
		orderModel.setStatus("Pending");
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setQuantity(orderModel.getQuantity() - tradingamount);
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}

}
