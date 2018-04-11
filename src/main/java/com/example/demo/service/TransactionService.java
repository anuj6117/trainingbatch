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

import com.example.demo.constant.WalletEnum;
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
		List<OrderModel> sellerOrder1;
		List<OrderModel> sellerOrder = new ArrayList<OrderModel>();
		if(buyerOrder.isEmpty()) {
			throw new Exception("Buy order not available");
		}
		for(OrderModel type:buyerOrder) {
			logger.info("buyer Model------>>>>"+type.getQuoteValue()+"---------buyer user id----------"+type.getUserModel().getUserid());
		}
		CoinModel coin;
		for (OrderModel buyorder : buyerOrder) {
			UserModel user = buyorder.getUserModel();
			Integer buyerId = user.getUserid();
			System.out.println(buyerId+"------------------buyerId-----------");
			
			logger.info( buyorder.getQuantity()+"-----------------------buyer quantity----------------------------------------");
			logger.info(buyorder.getCoinName()+"-------------------buyer coin name--------------------------------------------"+buyorder.getQuoteValue());
			coin = getListByName(allCurrency, buyorder.getCoinName());
			logger.info(coin.getCoinName()+"-----------coin name---------");
			if(coin==null) {
				throw new Exception("No coin Exist");
			}
			
			sellerOrder1 = orderRepo.findSeller(buyorder.getCoinName(),"sell",buyorder.getQuoteValue(),"Pending"); 
			for(OrderModel type:sellerOrder1) {
				logger.info("seller Model----quote value-->>>>"+type.getQuoteValue()+"--------seller user id-----------"+type.getUserModel().getUserid());
			}
			for(OrderModel type: sellerOrder1) {
				if(type.getUserModel().getUserid()==buyorder.getUserModel().getUserid()) {
					continue;
				}
				else {
					sellerOrder.add(type);
				}
			}
			if(sellerOrder.isEmpty()) {
				logger.info(sellerOrder.isEmpty()+"-------------------------------- seller list is empty-------------------------------");
				createTransaction(buyerOrder);
				continue;
				}
			
			Collections.sort(sellerOrder,new QuoteValueComparator()); 
			OrderModel userSeller = sellerOrder.get(0);
			logger.info(sellerOrder.get(0).getQuoteValue()+"----------------------------------seller Quote Value------------------------------");
			logger.info(coin.getPrice()+"-----<<<--price-----------quoteValue-->>>---"+userSeller.getQuoteValue());
			if(coin.getPrice()<userSeller.getQuoteValue()) {
				//made transaction via coin
				logger.info("-----into coin if scope--------------");
			    createTransaction(buyerOrder);
			}
			else if(coin.getPrice()>=userSeller.getQuoteValue()) {
				//made transaction via user seller	
				logger.info("Enter into transaction Zone of user");		
				 if(userSeller.getQuantity()==buyorder.getQuantity()) {
					 logger.info("buyerlist-----via user seller 1--------------");
					  madeTransactionByUser(buyorder, userSeller);//transaction
					  updateOrderStatusAsCompleted(buyorder);//buyer order status update
					  updateOrderStatusAsCompleted(userSeller);//seller order status update
					  createBuyerUpdateWallet(buyorder, buyorder.getGrossAmount());//buyer wallet update
					  createSellerUpdateWallet(userSeller,buyorder.getGrossAmount()-buyorder.getFee());//seller wallet update
					  updateProfitCurrency(coin, buyorder.getFee());//add profit only
					  return "sellorder"; 
					  } 
				 else if(userSeller.getQuantity()>buyorder.getQuantity()) {
					 logger.info("buyerlist-----via user seller 2--------------");
					  madeTransactionByUser(buyorder, userSeller); //transaction
					  updateOrderStatusAsCompleted(buyorder);//buyer order status update
					  createBuyerUpdateWallet(buyorder, buyorder.getGrossAmount());//buyer wallet update
					  updateOrderQuantity(userSeller,buyorder.getQuantity()); //seller order quantity
					  createSellerUpdateWallet(userSeller,buyorder.getGrossAmount()-buyorder.getFee());//seller wallet update
					  updateProfitCurrency(coin, buyorder.getFee());//add profit only
					 
					  return "success444";
					  
					  } 
				 else if(userSeller.getQuantity()<buyorder.getQuantity()) {
					  logger.info("buyerlist-----via user seller 3--------------");
					  madeTransactionByUser(buyorder, userSeller); //transaction
					  updateOrderQuantity(buyorder,userSeller.getQuantity()); //buy order amount update
					  createBuyerUpdateWallet(buyorder, buyorder.getGrossAmount());//buyer wallet update
					  updateOrderStatusAsCompleted(userSeller); //seller order status update
					  createSellerUpdateWallet(userSeller,buyorder.getGrossAmount()-buyorder.getFee());//seller wallet update
					  updateProfitCurrency(coin, buyorder.getFee());//add profit only
					
					  return "success8888";
					  }
					 
			}
			logger.info("----loop running----------");
		}
		return "1234567898765432";
	}
	public Object createTransaction(List<OrderModel> orderModel) throws Exception {

		List<CoinModel> allCurrency = coinRepo.findAll();
		CoinModel coin;
		for (OrderModel buyorder : orderModel) {
			coin = getListByName(allCurrency, buyorder.getCoinName());
			 if (buyorder.getQuoteValue() >= coin.getPrice()) {
				logger.info("enter into loop for transaction--------------------------------------");
				if(coin.getInitialSupply()>buyorder.getQuantity()) {
					madeTransactionByCoin(buyorder, coin);
					updateCurrency(coin, buyorder);
					createBuyerUpdateWallet(buyorder, buyorder.getGrossAmount());
					updateOrderStatusAsCompleted(buyorder);
				}
				else {
					madeTransactionByCoin(buyorder, coin);
					updateCurrency(coin, buyorder);
					createBuyerUpdateWallet(buyorder, buyorder.getGrossAmount());
					updateOrderQuantity(buyorder, buyorder.getQuantity()-coin.getInitialSupply());
				}
				
			}
		}
		return "1234567898765432";
	}

	public CoinModel getListByName(List<CoinModel> list, String name)throws Exception {
		CoinModel coinModel = new CoinModel();
		if(list.isEmpty()) {
			throw new Exception("No Currency exists");
		}
		for (CoinModel type : list) {
			logger.info(type.getCoinName()+"---------------inside coin loop");
			if (type.getCoinName().equals(name)) {
				coinModel = type;
				logger.info(type.getCoinName()+"---------------inside co ifffin loop");
				break;

			}
		}
		return coinModel;
	}

	//for coin seller
	public void updateCurrency(CoinModel coinModel, OrderModel orderModel) {
		coinModel.setCoinName(coinModel.getCoinName());
		coinModel.setSymbol(coinModel.getSymbol());
		coinModel.setPrice(coinModel.getPrice());
		coinModel.setFees(coinModel.getFees());
		coinModel.setInitialSupply(coinModel.getInitialSupply() - orderModel.getQuantity());
		coinModel.setProfit(((orderModel.getQuantity() * coinModel.getPrice())*2)/100);
		coinModel.setINRConvergent(orderModel.getQuantity() * coinModel.getPrice()
				+ ((orderModel.getQuantity() * coinModel.getPrice()) *2)/100);
		coinRepo.save(coinModel);
	}
	
	//for Admin profit update
	public void updateProfitCurrency(CoinModel coinModel,Long profit) {
		coinModel.setCoinName(coinModel.getCoinName());
		coinModel.setSymbol(coinModel.getSymbol());
		coinModel.setPrice(coinModel.getPrice());
		coinModel.setFees(coinModel.getFees());
		coinModel.setInitialSupply(coinModel.getInitialSupply());
		coinModel.setProfit(coinModel.getProfit()+profit);
		coinModel.setINRConvergent(coinModel.getINRConvergent());
		coinRepo.save(coinModel);
	}

	private Object createBuyerUpdateWallet(OrderModel orderModel, Long amount) {
		String coinName = orderModel.getCoinName();
		UserModel userDetail = orderModel.getUserModel();
		Set<WalletModel> walletSet = userDetail.getUserWallet();
		Integer flag = 0;
		Integer walletId = 0;
		for (WalletModel type : walletSet) {
			if (coinName==type.getWalletType()) {
				flag = 1;
				walletId = type.getWalletId();
			}
		}
		if (flag == 1) {
			// update wallet
			Optional<WalletModel> walletOptionalObject = walletRepo.findById(walletId);
			WalletModel walletModel = walletOptionalObject.get();
			walletModel.setBalance(walletModel.getBalance() + orderModel.getQuantity());
			walletModel.setShadowBalance(walletModel.getBalance());
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
				type.setBalance(type.getShadowBalance());
				type.setShadowBalance(type.getBalance());// balance deducted
				logger.info(type.getBalance()+"------------------");
			}
		}
		return "success";

	}

	private Object createSellerUpdateWallet(OrderModel orderModel, Long amount) {
		String coinName = orderModel.getCoinName();
		UserModel userDetail = orderModel.getUserModel();
		Set<WalletModel> walletSet = userDetail.getUserWallet();
		WalletModel fiatewallet=new WalletModel();
		WalletModel currencywallet=new WalletModel();
		for(WalletModel type:walletSet) {
			if(type.getWalletType().equalsIgnoreCase("Fiate")) {
				fiatewallet = type;
				logger.info(type.getWalletType()+"-----seller wallet fiate update--");
			}
			else if(type.getWalletType().equals(coinName)){
				currencywallet = type;
				logger.info(type.getWalletType()+"-----seller wallet currency update--");
			}
		}
		Optional<WalletModel> fiatewalletOptionalObject = walletRepo.findById(fiatewallet.getWalletId());
		WalletModel fiatewalletModel = fiatewalletOptionalObject.get();
		fiatewalletModel.setBalance(fiatewalletModel.getBalance() + amount);
		fiatewalletModel.setShadowBalance(fiatewalletModel.getBalance());
		walletRepo.save(fiatewalletModel);
		
		Optional<WalletModel> currencywalletOptionalObject = walletRepo.findById(currencywallet.getWalletId());
		WalletModel currencywalletModel = currencywalletOptionalObject.get();
		currencywalletModel.setBalance(currencywalletModel.getBalance()-orderModel.getQuantity());
		currencywalletModel.setShadowBalance(currencywalletModel.getBalance());
		walletRepo.save(currencywalletModel);
		
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
		Long fee =2l;
		Long grossAmount = ((coinModel.getPrice() * buyerModel.getQuantity())*fee)/100;
		transactionModel.setTransactionFee(buyerModel.getFee());
		transactionModel.setGrossAmount(
				(coinModel.getPrice() * buyerModel.getQuantity()) + grossAmount);	
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
		transactionModel.setTransactionFee(buyerModel.getFee());
		transactionModel.setGrossAmount(
				(sellerModel.getQuoteValue() * buyerModel.getQuantity()) + grossAmount);
		transactionModel.setNetAmount(buyerModel.getQuantity());
		transactionModel.setSellerId(sellerModel.getOrderId());
		transactionModel.setStatus("Completed");
		transactionRepo.save(transactionModel);
	}

	private void updateOrderStatusAsCompleted(OrderModel orderModel) {
		orderModel.setStatus("Completed");
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setQuantity(orderModel.getQuantity());
		orderModel.setGrossAmount(orderModel.getGrossAmount());
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}

	private void updateOrderQuantity(OrderModel orderModel, Long tradingamount) {
		orderModel.setStatus("Pending");
		orderModel.setCoinName(orderModel.getCoinName());
		orderModel.setFee(orderModel.getFee());
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setQuantity(orderModel.getQuantity() - tradingamount);
		orderModel.setGrossAmount(orderModel.getGrossAmount());
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}
}
