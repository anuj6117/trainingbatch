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
import com.example.demo.dto.INRDepositDTO;
import com.example.demo.dto.WalletHistory;
import com.example.demo.model.CoinModel;
import com.example.demo.model.OrderModel;
import com.example.demo.model.TransactionModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.CoinRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
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
	private UserRepository userRepo;
	@Autowired
	private WalletService walletService;
	@Autowired
	private WalletRepository walletRepo;
	List<OrderModel> buyerList = new ArrayList<OrderModel>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public Object createTransactionForINRDeposit(INRDepositDTO inrDepositDTO) throws Exception {
	
		if(inrDepositDTO.getAmount()==null) {
			throw new Exception("Amount cannot be null!!");
		}
        if(inrDepositDTO.getAmount()<=0) {
			throw new Exception("Cannot Add Negative Amount!!");
		}
		if(inrDepositDTO.getUserId()==null) {
			throw new Exception("User Id cannot be null");
		}
		if(inrDepositDTO.getUserId()<=0) {
			throw new Exception("User Id cannot be greater than zero");
		}
		if(inrDepositDTO.getWalletType().equals(null)||inrDepositDTO.getWalletType().equals("")) {
			throw new Exception("wallet Type cannot be null");
		}
		Optional<UserModel> user = userRepo.findById(inrDepositDTO.getUserId());
		if(user.get()==null) {
			throw new Exception("User Does not Exist");
		}
		if(user.get().getStatus()==false) {
			throw new Exception("First verfiy your account");
		}
		Integer flag=0,temp=0;
		Set<WalletModel> wallet = user.get().getUserWallet();
		for(WalletModel type: wallet) {
			if(type.getWalletType().equalsIgnoreCase("fiat")) {
				flag=1;
				if(inrDepositDTO.getWalletType().equalsIgnoreCase("fiat")) {
					temp=1;
					break;
				}
				
			}
		}
		if(flag==0) {
			throw new Exception("This wallet Does not exist");
		}
		if(temp==0) {
			throw new Exception("You cannot deposit into this wallet");
		}
		
		// create transaction
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setBuyerId(inrDepositDTO.getUserId());
		transactionModel.setCurrencyType(inrDepositDTO.getWalletType());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction for INR Deposit");
		transactionModel.setExchangeRate(0l);
		transactionModel.setTransactionFee(0l);
		transactionModel.setGrossAmount(0l);
		transactionModel.setNetAmount(inrDepositDTO.getAmount());
		transactionModel.setSellerId(0);
		transactionModel.setStatus("Pending");
		transactionRepo.save(transactionModel);
		return "success";
	}

	public Object approveTransaction(Integer transactionId,String status) throws Exception{
		logger.info(transactionId+status+"-----------transaction id---------");
		if(transactionId==null) {
			throw new Exception("Id cannot be null");
		}
		if(status.equalsIgnoreCase(null)||status.equalsIgnoreCase("")) {
			throw new Exception("Status cannot be null");
		}
		
	    TransactionModel userTransaction = transactionRepo.findByTransactionIdAndStatus(transactionId,"Pending");
	    Optional<OrderModel> buyer = orderRepo.findById(userTransaction.getBuyerId());
	    Optional<OrderModel> seller = orderRepo.findById(userTransaction.getSellerId());
	    if(userTransaction==null) {
	    	throw new Exception("No transaction Exists");
	    }
	    if(status.equalsIgnoreCase("approved")) {
	    	logger.info("-----------approved1----------");
	    	userTransaction.setStatus("approved");
	    	userTransaction.setDescription("Transaction successful");
		    transactionRepo.save(userTransaction);
		    updateAprovedTransactionDetail(transactionId, status);
		    return "Transaction approved";
	    }
	    else if(status.equalsIgnoreCase("failed")) {
	    	//shadow balance updated
	    	//order updated
	    	userTransaction.setStatus("failed");
	    	userTransaction.setDescription("Transaction failed due to some network issue");
	    	transactionRepo.save(userTransaction);
	    	updateOrderStatusAsCancle(buyer.get());
	    	if(userTransaction.getSellerId()!=0) {
	    		updateOrderStatusAsCancle(seller.get());
	    	}
	    	throw new Exception("Your transaction is failed");
	    }
	    else if(status.equalsIgnoreCase("rejected")) {
	    	//shadow balance updated
	    	//order updated
	    	userTransaction.setStatus("rejected");
	    	userTransaction.setDescription("Transaction rejected bad data");
	    	 transactionRepo.save(userTransaction);
	    	 updateOrderStatusAsCancle(buyer.get());
	    	 if(userTransaction.getSellerId()!=0) {
		    		updateOrderStatusAsCancle(seller.get());
		    	}
	    	 throw new Exception("Your transaction is rejected");
	    }
	    else {
	    	throw new Exception("Enter a valid Type");
	    }
  
	    
	}
	
	
	
	public Object getalltransaction() {
		return transactionRepo.findAll();
	}

	public Object mainTransaction() throws Exception {

		List<OrderModel> buyerOrder = orderRepo.findBuyer("buy", "Pending");
		List<CoinModel> allCurrency = coinRepo.findAll();
		List<OrderModel> sellerOrder1;
		List<OrderModel> sellerOrder = new ArrayList<OrderModel>();
		if (buyerOrder.isEmpty()) {
			throw new Exception("Buy order not available");
		}
		for (OrderModel type : buyerOrder) {
			logger.info("buyer Model------>>>>" + type.getQuoteValue() + "---------buyer user id----------"
					+ type.getUserModel().getUserid());
		}
		CoinModel coin;
		for (OrderModel buyorder : buyerOrder) {
			UserModel user = buyorder.getUserModel();
			Integer buyerId = user.getUserid();
			System.out.println(buyerId + "------------------buyerId-----------");

			logger.info(buyorder.getQuantity()
					+ "-----------------------buyer quantity----------------------------------------");
			logger.info(buyorder.getCoinName()
					+ "-------------------buyer coin name--------------------------------------------"
					+ buyorder.getQuoteValue());
			coin = getListByName(allCurrency, buyorder.getCoinName());
			logger.info(coin.getCoinName() + "-----------coin name---------");
			if (coin == null) {
				throw new Exception("No coin Exist");
			}

			sellerOrder1 = orderRepo.findSeller(buyorder.getCoinName(), "sell", buyorder.getQuoteValue(), "Pending");
			for (OrderModel type : sellerOrder1) {
				logger.info("seller Model----quote value-->>>>" + type.getQuoteValue()
						+ "--------seller user id-----------" + type.getUserModel().getUserid());
			}
			for (OrderModel type : sellerOrder1) {
				if (type.getUserModel().getUserid() == buyorder.getUserModel().getUserid()) {
					continue;
				} else {
					sellerOrder.add(type);
				}
			}
			if (sellerOrder.isEmpty()) {
				logger.info(sellerOrder.isEmpty()
						+ "-------------------------------- seller list is empty-------------------------------");
				createTransaction(buyorder);
				continue;
			}

			Collections.sort(sellerOrder, new QuoteValueComparator());
			OrderModel userSeller = sellerOrder.get(0);
			logger.info(sellerOrder.get(0).getQuoteValue()
					+ "----------------------------------seller Quote Value------------------------------");
			logger.info(coin.getPrice() + "-----<<<--price-----------quoteValue-->>>---" + userSeller.getQuoteValue());
			if (coin.getPrice() < userSeller.getQuoteValue()) {
				// made transaction via coin
				logger.info("-----into coin if scope--------------");
				createTransaction(buyorder);
			} else if (coin.getPrice() >= userSeller.getQuoteValue()) {
				// made transaction via user seller
				logger.info("Enter into transaction Zone of user");
				madeTransactionByUser(buyorder, userSeller);// transaction
			}
			logger.info("----loop running----------");
		}
		return "1234567898765432";
	}
	
	public Object updateAprovedTransactionDetail(Integer transactionId,String status) throws Exception {
		logger.info("-----------approved2----------");
    Optional<TransactionModel> transOp = transactionRepo.findById(transactionId);
    TransactionModel transaction = transOp.get();
    CoinModel coin = coinRepo.findByCoinName(transaction.getCurrencyType());
    if(transaction.getCurrencyType().equalsIgnoreCase("fiat")) {
    	//update wallet of user with INR deposit
    	walletService.addAmountIntoWallet1(transaction.getBuyerId(),transaction.getCurrencyType(),transaction.getNetAmount());
    }
    else {
    	logger.info("-----------approved3----------");
    	//fetch the buyer and seller/coin based on transaction
    	Optional<OrderModel> buyerOp = orderRepo.findById(transaction.getBuyerId());
    	OrderModel buyer = buyerOp.get();
    	if(transaction.getSellerId()==0) {
    		// fetch the currency type
    		//update coin initial Supply and stuff
    		logger.info("coin----updation");
    		updateCurrency(coin, buyer);
    		updateOrderStatusAsCompleted(buyer);
			createBuyerUpdateWallet(buyer, buyer.getGrossAmount());
			
    	}
    	else {
    		Long coinQuote = coin.getPrice();
    		
    		logger.info("-----------approved4----------");
    		Optional<OrderModel> sellerOp = orderRepo.findById(transaction.getSellerId());
    		
    		OrderModel seller = sellerOp.get();
    		Long sellerQuote = seller.getQuoteValue();
    	//	Long mainProfit = (sellerQuote-coinQuote);
    		Long mainProfit = (buyer.getQuoteValue()-sellerQuote);
				if (seller.getQuantity() == buyer.getQuantity()) {
					logger.info("-----------approved------1----");
					
					logger.info(sellerQuote+"------values----"+mainProfit);
					//updateProfitCurrency(coin, buyer.getFee());
					logger.info(buyer.getFee()+(mainProfit*buyer.getQuantity())+"-------profit calculation");
					updateProfitCurrency(coin, buyer.getFee()+(mainProfit*buyer.getQuantity()));// add profit only
					updateOrderStatusAsCompleted(buyer);// buyer order status update
					updateOrderStatusAsCompleted(seller);// seller order status update
					createBuyerUpdateWallet(buyer, buyer.getGrossAmount());// buyer wallet update
					createSellerUpdateWallet(seller, buyer.getGrossAmount()-buyer.getFee());// seller wallet update																		// update
					
					
					return "sellorder";
				} else if (seller.getQuantity() > buyer.getQuantity()) {
					logger.info("-----------approved------22----");
					logger.info(sellerQuote+"------values----"+mainProfit);
					logger.info(buyer.getFee()+(mainProfit*buyer.getQuantity())+"-------profit calculation");
					
					updateProfitCurrency(coin, buyer.getFee());
					//updateProfitCurrency(coin, buyer.getFee()+(mainProfit*buyer.getQuantity()));// add profit only
					updateOrderStatusAsCompleted(buyer);// buyer order status update
					createBuyerUpdateWallet(buyer, buyer.getGrossAmount());// buyer wallet update
					createSellerUpdateWallet(seller,buyer.getGrossAmount()-buyer.getFee());// seller wallet update
					updateOrderQuantity(seller, buyer.getQuantity()); // seller order quantity
					logger.info("-----------approved1-----2-----");
					return "success444";

				} else if (seller.getQuantity() < buyer.getQuantity()) {
					logger.info("-----------approved------33----");
					logger.info(sellerQuote+"------values----"+mainProfit);
					logger.info(buyer.getFee()+(mainProfit*buyer.getQuantity())+"-------profit calculation");
					
					updateProfitCurrency(coin, buyer.getFee());
					//updateProfitCurrency(coin, buyer.getFee()+(mainProfit*buyer.getQuantity()));// add profit only
					updateOrderQuantity(buyer, seller.getQuantity()); // buy order amount update
					updateOrderStatusAsCompleted(seller); // seller order status update
					createBuyerUpdateWallet(buyer, buyer.getGrossAmount());// buyer wallet update
					createSellerUpdateWallet(seller,seller.getGrossAmount()-seller.getFee());// seller wallet
					
					
					logger.info("-----------approved1------3----");
					return "success8888";
				}
			}
    
			logger.info("----loop running----------");
		}
		return "1234567898765432";
	}

	
	public Object createTransaction(OrderModel orderModel) throws Exception {

		List<CoinModel> allCurrency = coinRepo.findAll();
		CoinModel coin;

		coin = getListByName(allCurrency, orderModel.getCoinName());
		if (orderModel.getQuoteValue() >= coin.getPrice()) {
			logger.info("enter into loop for transaction--------------------------------------");
			if (coin.getInitialSupply() > orderModel.getQuantity()) {
				madeTransactionByCoin(orderModel, coin);
			} else {
				madeTransactionByCoin(orderModel, coin);
			}

		}

		return "1234567898765432";
	}

	public CoinModel getListByName(List<CoinModel> list, String name) throws Exception {
		CoinModel coinModel = new CoinModel();
		if (list.isEmpty()) {
			throw new Exception("No Currency exists");
		}
		for (CoinModel type : list) {
			logger.info(type.getCoinName() + "---------------inside coin loop");
			if (type.getCoinName().equalsIgnoreCase(name)) {
				coinModel = type;
				logger.info(type.getCoinName() + "---------------inside co ifffin loop");
				break;

			}
		}
		return coinModel;
	}

	// for coin seller
	public void updateCurrency(CoinModel coinModel, OrderModel orderModel) {
		coinModel.setCoinName(coinModel.getCoinName());
		coinModel.setSymbol(coinModel.getSymbol());
		coinModel.setPrice(coinModel.getPrice());
		coinModel.setFees(coinModel.getFees());
		coinModel.setInitialSupply(coinModel.getInitialSupply() - orderModel.getQuantity());
		Long inrconvergent=(orderModel.getQuantity() * orderModel.getQuoteValue());
		Long profit =((orderModel.getQuantity() * orderModel.getQuoteValue())*2)/100;
		logger.info(inrconvergent+"-----inside currency update-----"+profit);
		coinModel.setProfit(coinModel.getProfit()+profit);
		
		coinModel.setINRConvergent(coinModel.getINRConvergent()+inrconvergent);
		coinRepo.save(coinModel);
	}

	// for Admin profit update
	public void updateProfitCurrency(CoinModel coinModel, Long profit) {
		coinModel.setCoinName(coinModel.getCoinName());
		coinModel.setSymbol(coinModel.getSymbol());
		coinModel.setPrice(coinModel.getPrice());
		coinModel.setFees(coinModel.getFees());
		coinModel.setInitialSupply(coinModel.getInitialSupply());
		logger.info( profit+"---profit----");
		logger.info(coinModel.getProfit()+"---coinprofit----");
		coinModel.setProfit(coinModel.getProfit() + profit);
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
			if (type.getWalletType().equalsIgnoreCase(coinName)) {
				flag = 1;
				logger.info("wallet type exist");
				walletId = type.getWalletId();
			}
		}
		if (flag == 1) {
			// update wallet
			logger.info("wallet type exist--------------S");
			Optional<WalletModel> walletOptionalObject = walletRepo.findById(walletId);
			WalletModel walletModel = walletOptionalObject.get();
			logger.info(walletModel.getWalletType()+"---------------");
			walletModel.setBalance(walletModel.getBalance() + orderModel.getQuantity());
			walletModel.setShadowBalance(walletModel.getBalance());
			walletRepo.save(walletModel);
		} else {
			// add wallet
			logger.info("wallet type does not exists create a new wallet");
			WalletModel walletModel = new WalletModel();
			walletModel.setUserModel(userDetail);
			walletModel.setWalletHash(Utility.generateId(100));
			walletModel.setWalletType(coinName);
			walletModel.setBalance(orderModel.getQuantity());
			walletModel.setShadowBalance(orderModel.getQuantity());
			walletRepo.save(walletModel);

		}
		
	
		for (WalletModel type : walletSet) {
			if ("fiat".equalsIgnoreCase(type.getWalletType())) {
				Optional<WalletModel> wallet = walletRepo.findById(type.getWalletId());
				if(orderModel.getStatus().equalsIgnoreCase("Pending")) {
					Long bal = type.getShadowBalance()+(orderModel.getQuantity()*orderModel.getQuoteValue());
					wallet.get().setBalance(bal);
					walletRepo.save(wallet.get());
					logger.info("pending-----entry"+bal);
					 break;
					
				}else if(orderModel.getStatus().equalsIgnoreCase("Completed")) {
					wallet.get().setBalance(type.getBalance()-amount);
					//type.setShadowBalance(type.getBalance());
					logger.info("comppleted-----entry"+amount);
					walletRepo.save(wallet.get());
				    break;
				}
				// balance deducted
				logger.info(type.getBalance() + "-----------buyer update wallet-------");
			}
		}
		logger.info("-----------buyer update wallet-------");
		return "success";

	}

	private Object createSellerUpdateWallet(OrderModel orderModel, Long amount) {
		String coinName = orderModel.getCoinName();
		logger.info(coinName+"-----coin?name-----");
		UserModel userDetail = orderModel.getUserModel();
		Set<WalletModel> walletSet = userDetail.getUserWallet();
		WalletModel fiatewallet = new WalletModel();
		WalletModel currencywallet = new WalletModel();
		for (WalletModel type : walletSet) {
			if (type.getWalletType().equalsIgnoreCase("Fiat")) {
				fiatewallet = type;
				logger.info(type.getWalletType() + "-----seller wallet fiate update--");
			} else if (type.getWalletType().equalsIgnoreCase(coinName)) {
				currencywallet = type;
				logger.info(type.getWalletType() + "-----seller wallet currency update--");
			}
		}
		if(orderModel.getStatus().equalsIgnoreCase("Pending")) {
			logger.info( "-----------seller update wallet Pending entry-------");
			Optional<WalletModel> fiatewalletOptionalObject = walletRepo.findById(fiatewallet.getWalletId());
			WalletModel fiatewalletModel = fiatewalletOptionalObject.get();
			logger.info(amount+"---amount-----");
			logger.info(fiatewalletModel.getBalance()+"----------fiateamount------");
			fiatewalletModel.setBalance(fiatewalletModel.getBalance() + amount);
			
			//fiatewalletModel.setShadowBalance(fiatewalletModel.getBalance());
			walletRepo.save(fiatewalletModel);

			Optional<WalletModel> currencywalletOptionalObject = walletRepo.findById(currencywallet.getWalletId());
			WalletModel currencywalletModel = currencywalletOptionalObject.get();
			currencywalletModel.setBalance(currencywalletModel.getBalance() - orderModel.getQuantity());
			walletRepo.save(currencywalletModel);

		}
		else if(orderModel.getStatus().equalsIgnoreCase("Completed")) {
			logger.info( "-----------seller update wallet completed entry-------");
			Optional<WalletModel> fiatewalletOptionalObject = walletRepo.findById(fiatewallet.getWalletId());
			WalletModel fiatewalletModel = fiatewalletOptionalObject.get();
			logger.info(amount+"---amount-----");
			logger.info(fiatewalletModel.getBalance()+"----------fiateamount------");

			fiatewalletModel.setBalance(fiatewalletModel.getBalance() + amount);
			//fiatewalletModel.setShadowBalance(fiatewalletModel.getBalance());
			walletRepo.save(fiatewalletModel);

			logger.info( "-----------seller fiat update wallet completed entry-------");
			Optional<WalletModel> currencywalletOptionalObject = walletRepo.findById(currencywallet.getWalletId());
			WalletModel currencywalletModel = currencywalletOptionalObject.get();
			currencywalletModel.setBalance(currencywalletModel.getBalance() - orderModel.getQuantity());
			currencywalletModel.setShadowBalance(currencywalletModel.getBalance());
			walletRepo.save(currencywalletModel);

			logger.info( "-----------seller crypto update wallet completed entry-------");
		}
		logger.info( "-----------seller update wallet-------");
	
		return "success";

	}
	
	private void madeTransactionByCoin(OrderModel buyerModel, CoinModel coinModel) {
		// create transaction
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setBuyerId(buyerModel.getOrderId());
		transactionModel.setCurrencyType(buyerModel.getCoinName());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction Made by coin Seller");
		transactionModel.setExchangeRate(buyerModel.getQuoteValue());
		Long fee = 2l;
		Long grossAmount = ((buyerModel.getQuoteValue() * buyerModel.getQuantity()) * fee) / 100;
		transactionModel.setTransactionFee(buyerModel.getFee());
		transactionModel.setGrossAmount((buyerModel.getQuoteValue() * buyerModel.getQuantity()) + grossAmount);
		transactionModel.setNetAmount(buyerModel.getQuantity());
		transactionModel.setSellerId(0);
		transactionModel.setStatus("Pending");
		
		transactionRepo.save(transactionModel);
	}

	private void madeTransactionByUser(OrderModel buyerModel, OrderModel sellerModel) {
		// create transaction
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setBuyerId(buyerModel.getOrderId());
		transactionModel.setCurrencyType(buyerModel.getCoinName());
		transactionModel.setTransactionCreatedOn(new Date());
		transactionModel.setDescription("transaction Made by userSeller");
		transactionModel.setExchangeRate(buyerModel.getQuoteValue());
		Long fee = 2l;
		Long grossAmount = ((sellerModel.getQuoteValue() * buyerModel.getQuantity()) * fee) / 100;
		transactionModel.setTransactionFee(buyerModel.getFee());
		transactionModel.setGrossAmount((buyerModel.getQuoteValue() * buyerModel.getQuantity()) + buyerModel.getFee());
		transactionModel.setNetAmount(buyerModel.getQuantity());
		transactionModel.setSellerId(sellerModel.getOrderId());
		transactionModel.setStatus("Pending");
	
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
	
	private void updateOrderStatusAsCancle(OrderModel orderModel) {
		orderModel.setStatus("cancle");
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
		orderModel.setOrderType(orderModel.getOrderType());
		orderModel.setQuoteValue(orderModel.getQuoteValue());
		orderModel.setQuantity(orderModel.getQuantity() - tradingamount);
		Long amount =orderModel.getQuantity()*orderModel.getQuoteValue();
		Long fee= (amount*2)/100;
		orderModel.setGrossAmount(amount+fee);
		orderModel.setFee(fee);
		orderModel.setUserModel(orderModel.getUserModel());
		orderRepo.save(orderModel);
	}
	
	public  Object showAllHistory(Integer userId) {
		logger.info(userId+"------userid-----------");
		List<TransactionModel>  transactionData1= transactionRepo.findByBuyerIdAndCurrencyType(userId,"fiate");
	
		logger.info(transactionData1.size()+"-----------------");
		List<WalletHistory> wallet = new ArrayList<>();
		for(TransactionModel transactionData:transactionData1) {
			WalletHistory walletHistory = new WalletHistory();
			walletHistory.setDescription(transactionData.getDescription());
			walletHistory.setNetAmount(transactionData.getNetAmount());
			walletHistory.setStatus(transactionData.getStatus());
			walletHistory.setUserId(transactionData.getBuyerId());
			wallet.add(walletHistory);
		}
		
		return wallet;
	}
}
