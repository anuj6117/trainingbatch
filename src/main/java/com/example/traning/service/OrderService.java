package com.example.traning.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Coin;
import com.example.traning.domain.Order;
import com.example.traning.domain.Register;
import com.example.traning.domain.Wallet;
import com.example.traning.exception.UserNotFoundException;
import com.example.traning.repository.CoinRepository;
import com.example.traning.repository.OrderRepository;
import com.example.traning.repository.RegisterRepository;
import com.example.traning.repository.WalletRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderrepository;

	@Autowired
	RegisterRepository registerrepository;

	@Autowired
	WalletRepository walletrepository;

	@Autowired
	CoinRepository coinrepository;

	public static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public ResponseEntity<List<Order>> getAllData() {

		List<Order> find = orderrepository.findAll();
		if (find.isEmpty()) {
			throw new NullPointerException("No data there");
		}

		else {
			return new ResponseEntity<List<Order>>(find, HttpStatus.OK);
		}
	}

	// ==========================================================================CreateBuyOrder
	public Order createBuyOrder(Order ordermod, String type) throws UserNotFoundException {

		Register model = registerrepository.findByUserId(ordermod.getUserId());
		if(model==null)
		{
			throw new NullPointerException("User Id cant null!");
		}
		if(model.getStatus()==false)
		{
			throw new UserNotFoundException("you are not verified user!");
		}
		Order orderModelObj = new Order();
		Order orderCreated = null;

		Float amount = ordermod.getTradingAmount();
		Float quoteValue = ordermod.getQuoteValue();
		Float fee = ordermod.getFee();

		Wallet defaultWalletObj = null;
		Wallet currentWalletObj = null;

		// Walletmodel createwalletObj = new Walletmodel();
		//
		float totalAmount = ordermod.getTradingAmount() * ordermod.getQuoteValue();
		System.out.println("tottalamopunt"+totalAmount);
		float transactionFee = totalAmount *(fee/100);
		System.out.println("transectin fee"+transactionFee);
		float grossAmount = totalAmount + transactionFee;

		boolean walletFlag = true;
		boolean flag = true;

		List<Coin> allCoinList = coinrepository.findAll();

		logger.info(" Ispe trading karni hai " + ordermod.getCoinName());
		for (Coin gettingAllCoins : allCoinList) {
			System.out.println("All coins in coin management1 " + gettingAllCoins.getCoinName());
             System.out.println("@nd Validation"+ordermod.getCoinName().equals(gettingAllCoins.getCoinName()));
			if (ordermod.getCoinName().equals(gettingAllCoins.getCoinName()))
			{
				List<Wallet> userWalletList = model.getWall();
				defaultWalletObj = userWalletList.get(0);
				flag = false;
				System.out.println("All coins in coin management2" );

				for (Wallet gettingUserWallet : userWalletList) {
					System.out.println(ordermod.getCoinName().equalsIgnoreCase(gettingUserWallet.getWalletType()));
					if (ordermod.getCoinName().equalsIgnoreCase(gettingUserWallet.getWalletType()))
					{

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
			throw new NullPointerException("Trading not possible on this coin(Enter coinName not exist)");
		}

		if (type.equals("buyer")) {

			if (grossAmount < defaultWalletObj.getShadowBalance()) {
            System.out.println(defaultWalletObj.getShadowBalance());
				if (walletFlag) {
					Wallet createwalletObj = new Wallet();
					createwalletObj.setAmount(createwalletObj.getAmount());
					createwalletObj.setShadowBalance(createwalletObj.getShadowBalance());
					createwalletObj.setWalletType(ordermod.getCoinName());
					createwalletObj.setRegister(model);
					logger.error("wallet is creating");
					walletrepository.save(createwalletObj);
					currentWalletObj=createwalletObj;
					
				}
				orderModelObj.setTradingAmount(ordermod.getTradingAmount());
				orderModelObj.setFee(ordermod.getFee());
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setGrossAmount(grossAmount);

				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setRegister(model);

				// updating amount in default wallet;

				Long newDefaultShadowBalance = (long) (defaultWalletObj.getShadowBalance() - grossAmount);
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletrepository.save(defaultWalletObj);

				// updating amount in another wallet;
				Long newCurrentShadowBalance = (long) (currentWalletObj.getShadowBalance() + ordermod.getTradingAmount());
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);

				walletrepository.save(currentWalletObj);

				orderCreated = orderrepository.save(orderModelObj);

				return orderCreated;

			} else {
				throw new NullPointerException("Don't have enough money to buy more coins");
			}

		}
		//==================================================================================sell
		else {

			if (ordermod.getTradingAmount() < currentWalletObj.getAmount()) {

				logger.info("Coming in the seller portion");
				orderModelObj.setTradingAmount(ordermod.getTradingAmount());
				orderModelObj.setFee(ordermod.getFee());
				orderModelObj.setQuoteValue(ordermod.getQuoteValue());
				orderModelObj.setOrderType(type);
				orderModelObj.setOrderCreatedOn(new Date());
				orderModelObj.setStatus("pending");
				orderModelObj.setCoinName(ordermod.getCoinName());
				orderModelObj.setGrossAmount(grossAmount);
				orderModelObj.setRegister(model);

				Long newDefaultShadowBalance = (long) (defaultWalletObj.getShadowBalance() + totalAmount);
				defaultWalletObj.setShadowBalance(newDefaultShadowBalance);
				walletrepository.save(defaultWalletObj);

				Long newCurrentShadowBalance = (long) (currentWalletObj.getShadowBalance() - amount);
				currentWalletObj.setShadowBalance(newCurrentShadowBalance);
				walletrepository.save(currentWalletObj);

				return orderrepository.save(orderModelObj);
			} 
			else {
				throw new NullPointerException("Don't have enough coins to sell");
			}
		}
	}

	// -----------------------------------------------------------------------------------------
//	public Order updateOrderData(Integer orderId, Order order)  {
//		
//		Order orderModel = orderrepository.findOneByOrderId(orderId);
//
//		if (orderModel == null) {
//
//			throw new NullPointerException("Order id " + orderId + " does not exist");
//
//		} else {
//
//			orderModel.setTradingAmount(order.getTradingAmount());
//			orderModel.setOrderType(order.getOrderType());
//			orderModel.setFee(order.getFee());
//			orderModel.setStatus(order.getStatus());
//			orderModel.setCoinName(order.getCoinName());
//
//			Order updatedOrder = orderrepository.save(orderModel);
//			return updatedOrder;
//		}
//	}

    //getOrdersByUserId
	public Set<Order> getOrderByUserId(Long userId) {

		Register getAllUsersDetails = registerrepository.findByUserId(userId);
		if (getAllUsersDetails == null) {
			throw new NullPointerException("User doesn't exist");
		}
		Set<Order> orderList = getAllUsersDetails.getOrder();

		if (orderList.isEmpty()) {
			throw new NullPointerException("No Order Present for this user");
		} else {
			return orderList;
		}
	}

	public Order admincreateSellOrder(Order order) {


		Optional<Register> optionModel = registerrepository.findById(order.getUserId());
		Register model = optionModel.get();
		Coin coinDetails = coinrepository.findByCoinName(order.getCoinName());
		Order orderModelObj = new Order();


		if (order.getTradingAmount() < coinDetails.getInitialSupply()) {

			logger.info("Coming in the seller portion");
			orderModelObj.setTradingAmount(order.getTradingAmount());
			orderModelObj.setFee(2.0f);
			orderModelObj.setQuoteValue((float) coinDetails.getPrice());
			orderModelObj.setOrderType("sell");
			orderModelObj.setOrderCreatedOn(new Date());
			orderModelObj.setStatus("pending");
			orderModelObj.setCoinName(order.getCoinName());

			Float amount = order.getTradingAmount();
			long quoteValue = coinDetails.getPrice();	
			float totalAmount = amount * quoteValue;
//			Double transFee = totalAmount * (order.getFee() / 100);

			float grossAmount = totalAmount;

			orderModelObj.setGrossAmount(grossAmount);
			orderModelObj.setRegister(model);
			return orderrepository.save(orderModelObj);
		} else {
			throw new NullPointerException("Don't have enough coins to sell");
		}

	}
	
	
public List<Order> showOrder() {
		
		List<Order> allorder=orderrepository.findAll();
		
		return allorder;
	}

}