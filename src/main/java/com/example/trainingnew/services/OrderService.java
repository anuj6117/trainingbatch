package com.example.trainingnew.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.model.OrderModel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.model.Walletmodel;
import com.example.trainingnew.reprository.CoinRepo;
import com.example.trainingnew.reprository.OrderReprository;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.reprository.WalletRepo;
import com.example.trainingnew.util.CustomErrorType;

@Service
public class OrderService {

	@Autowired
	OrderReprository orderRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	WalletRepo walletRepo;

	@Autowired
	CoinRepo coinRepo;

	public static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public ResponseEntity<List<OrderModel>> getAllData() {

		List<OrderModel> find = orderRepo.findAll();
		if (find.isEmpty()) {
			return (new ResponseEntity(new CustomErrorType("No any data exist"), HttpStatus.NOT_FOUND));
		}

		else {
			return new ResponseEntity<List<OrderModel>>(find, HttpStatus.OK);
		}
	}

	// ------------------------------------------------------------------------------------------CreateBuyOrder
	public OrderModel createBuyOrder(OrderModel ordermod, String type) throws UserNotFoundException {

		UserModel model = userRepo.findByUserId(ordermod.getUserId());
		List<Coinmodel> allCoinList = coinRepo.findAll();
		
		logger.info("hello");
		OrderModel orderModelObj = new OrderModel();
		OrderModel orderCreated = null;

		Double amount = ordermod.getAmount();
		Double quoteValue = ordermod.getQuoteValue();
		Double fee = ordermod.getFee();

		Walletmodel defaultWalletObj = null;
		Walletmodel currentWalletObj = null;

		// Walletmodel createwalletObj = new Walletmodel();
		//
		
		logger.info("hello123");
		boolean walletFlag = true;
		boolean flag = true;

		Double coinsFee=null;

		logger.info(" Ispe trading karni hai " + ordermod.getCoinName());
		for (Coinmodel gettingAllCoins : allCoinList) {
			logger.info("All coins in coin management " + gettingAllCoins.getCoinName());
			
			if (ordermod.getCoinName().equals(gettingAllCoins.getCoinName())) {
				coinsFee=ordermod.getFee();
				List<Walletmodel> userWalletList = model.getWallets();
				defaultWalletObj = userWalletList.get(0);
				logger.info(""+defaultWalletObj.getWalletType());
				flag = false;

				for (Walletmodel gettingUserWallet : userWalletList) {
					logger.info("checking coinName aype is true");
					if (ordermod.getCoinName().equals(gettingUserWallet.getWalletType())) {
logger.info("checking coinName and Wallet type is true");
						walletFlag = false;
						currentWalletObj = gettingUserWallet;

						logger.info("ye hai currect wallet inside loop " + currentWalletObj.getWalletType());
						// break;
						// throw new RuntimeException("wallet ha hi nhi khase du");
						//
					}
				}

			}
		}

		if (flag) {
			throw new NullPointerException("Trading not possible on this coin");
		}
logger.info("HIT MAN");
		Double totalAmount = ordermod.getAmount() * ordermod.getQuoteValue();

		Double transactionFee = totalAmount * (2 / 100);
		Double grossAmount = totalAmount + transactionFee;
//		logger.info(arg0);
		if (type.equals("buy")) {

			if (grossAmount < defaultWalletObj.getShadowBalance()) {

				if (walletFlag) {
					Walletmodel createwalletObj = new Walletmodel();
					createwalletObj.setBalance(createwalletObj.getBalance());
					createwalletObj.setShadowBalance(createwalletObj.getShadowBalance());
					createwalletObj.setWalletType(ordermod.getCoinName());
					createwalletObj.setUserModel(model);
					logger.error("wallet is creating");
					walletRepo.save(createwalletObj);
					currentWalletObj=createwalletObj;
					
				}
				logger.error("Condition true user has enough money in wallet to buy coins");
				orderModelObj.setAmount(ordermod.getAmount());
				orderModelObj.setFee(transactionFee);
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setGrossAmount(grossAmount);

				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setUserModelInOrderModel(model);

				// updating amount in default wallet;

				Double newDefaultShadowBalance = defaultWalletObj.getShadowBalance() - grossAmount;
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletRepo.save(defaultWalletObj);

				// updating amount in another wallet;
				Double newCurrentShadowBalance = currentWalletObj.getShadowBalance() + ordermod.getAmount();
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);

				walletRepo.save(currentWalletObj);

				
				orderCreated = orderRepo.save(orderModelObj);

				return orderCreated;

			} else {
				throw new NullPointerException("Don't have enough money to buy coins");
			}

		}

		else {

			if (ordermod.getAmount() < currentWalletObj.getBalance()) {

				logger.info("Coming in the seller portion");
				orderModelObj.setAmount(ordermod.getAmount());
				orderModelObj.setFee(ordermod.getFee());
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setUserModelInOrderModel(model);

				Double newDefaultShadowBalance = defaultWalletObj.getShadowBalance() + totalAmount;
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletRepo.save(defaultWalletObj);

				Double newCurrentShadowBalance = currentWalletObj.getShadowBalance() - amount;
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);
				walletRepo.save(currentWalletObj);

				return orderRepo.save(orderModelObj);
			} else {
				throw new NullPointerException("Don't have enough coins to sell");
			}
		}
		
	}

	// -----------------------------------------------------------------------------------------
	public OrderModel updateOrderData(Integer orderId, OrderModel order) throws UserNotFoundException {
		OrderModel orderModel = orderRepo.findOneByOrderId(orderId);

		if (orderModel == null) {

			throw new UserNotFoundException("Order id " + orderId + " does not exist");

		} else {

			orderModel.setAmount(order.getAmount());
			orderModel.setOrderType(order.getOrderType());
			orderModel.setFee(order.getFee());
			orderModel.setStatus(order.getStatus());
			orderModel.setCoinName(order.getCoinName());

			OrderModel updatedOrder = orderRepo.save(orderModel);
			return updatedOrder;
		}
	}

	// getOrdersByUserId
	public List<OrderModel> getOrderByUserId(Integer userId) {

		UserModel getAllUsersDetails = userRepo.findByUserId(userId);
		if (getAllUsersDetails == null) {
			throw new NullPointerException("User doesn't exist");
		}
		List<OrderModel> orderList = getAllUsersDetails.getOrders();

		if (orderList.isEmpty()) {
			throw new NullPointerException("No Order Present for this user");
		} else {
			return orderList;
		}
	}

	public OrderModel admincreateSellOrder(OrderModel order) {

		logger.info("first hit");
		Optional<UserModel> optionModel = userRepo.findById(order.getUserId());
		UserModel model = optionModel.get();
		logger.info("second hit");
		Coinmodel coinDetails = coinRepo.findByCoinName(order.getCoinName());
		
		if(coinDetails==null)
			throw new NullPointerException("Trading not possible on this coin");
		OrderModel orderModelObj = new OrderModel();

		logger.error("hit before for loop");
		if (order.getAmount() < coinDetails.getInitialSupply()) {

			logger.info("Coming in the seller portion");
			orderModelObj.setAmount(order.getAmount());
			orderModelObj.setFee(2.0);
			orderModelObj.setQuoteValue(coinDetails.getPrice());
			orderModelObj.setOrderType("sell");
			orderModelObj.setOrderCreatedOn(new Date());
			orderModelObj.setStatus("pending");
			orderModelObj.setCoinName(order.getCoinName());

			Double amount = order.getAmount();
			Double quoteValue = coinDetails.getPrice();	
			Double totalAmount = amount * quoteValue;
//			Double transFee = totalAmount * (order.getFee() / 100);

			Double grossAmount = totalAmount;

			orderModelObj.setGrossAmount(grossAmount);
			orderModelObj.setUserModelInOrderModel(model);
			logger.info("hit before save");
			return orderRepo.save(orderModelObj);
		} else {
			throw new NullPointerException("Don't have enough coins to sell");
		}

	}

}
