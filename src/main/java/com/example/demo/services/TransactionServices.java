package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class TransactionServices {
	@Autowired
	CoinManagementRepository coindata;
	@Autowired
	UserRepository userdata;
	@Autowired
	WalletRepostiory walletdata;
	@Autowired
	OrderRepository orderdata;
	@Autowired
	WalletServices walletfunction;
	@Autowired
	TransactionRepository transactionRepository;

	public OrderModel transactionCheck() {
		Integer price = 0;
		List<OrderModel> buyer = orderdata.findAllByOrderTypeAndStatus("buyer", "pending");
		List<OrderModel> seller = orderdata.findAllByOrderTypeAndStatus("seller", "pending");
		if (seller.isEmpty())
			throw new RuntimeException("seller not found");
		if (buyer.isEmpty())
			throw new RuntimeException("Buyer not found");
		OrderModel sellerresult = null;
		List<OrderModel> data = null;
		WalletModel getsellorder = null;
		WalletModel getbuyorder = null;
		Integer grossamount = 0;
		Integer coinQuantity = 0;
		Integer sellCoinQuantity=0;
		String orderresult = "";
		for (OrderModel buy : buyer) {
			boolean flag = true;
			price = buy.getPrice();
			System.out.println(price);
			data = orderdata.findByCoin(buy.getCoinName(), "seller", buy.getPrice(), "pending");
			if(data.isEmpty())
				continue;
			a: for (OrderModel getresult : data) {
				if (buy.getUser().getUserId() == getresult.getUserId()& buy.getSellettype().equals(getresult.getSellettype())) {
					flag = false;
					break a;
				} else {
					flag = true;
					if (price >= getresult.getPrice()) {
						price = getresult.getPrice();
						sellerresult = getresult;
					}
				}
			}
			System.out.println(sellerresult.getSellettype());
			if (flag) {
				if (buy.getCoinQuantity() == sellerresult.getCoinQuantity()) {
					sellerresult.setStatus("completed");
					buy.setStatus("completed");
					orderresult = "success";
					grossamount = buy.getGrossAmount();
					coinQuantity = buy.getCoinQuantity();
					buy.setCoinQuantity(sellerresult.getCoinQuantity());
					buy.setGrossAmount(sellerresult.getGrossAmount());
					sellCoinQuantity=sellerresult.getCoinQuantity();

				} else if (buy.getCoinQuantity() < sellerresult.getCoinQuantity()) {
					grossamount = buy.getGrossAmount();
					coinQuantity = buy.getCoinQuantity();
					sellerresult.setGrossAmount(sellerresult.getGrossAmount() - buy.getGrossAmount());
					sellerresult.setCoinQuantity(sellerresult.getCoinQuantity() - buy.getCoinQuantity());
					sellerresult.setStatus("pending");
					buy.setStatus("completed");
					orderresult = "success";
					sellCoinQuantity=buy.getCoinQuantity();
					
					
				} else if (buy.getCoinQuantity() > sellerresult.getCoinQuantity()) {
					grossamount = sellerresult.getGrossAmount();
					coinQuantity = sellerresult.getCoinQuantity();
					buy.setGrossAmount(buy.getGrossAmount() - sellerresult.getGrossAmount());
					buy.setCoinQuantity(buy.getCoinQuantity() - sellerresult.getCoinQuantity());
					sellerresult.setStatus("completed");
					buy.setStatus("pending");
					orderresult = "success";
					sellCoinQuantity=sellerresult.getCoinQuantity();
					sellCoinQuantity=sellCoinQuantity+sellerresult.getCoinQuantity();
					
				}
				if (orderresult.equals("success")) {
					CoinManagementModel coin = new CoinManagementModel();
					coin = coindata.findByCoinName(buy.getCoinName());
					Integer exchangerate = 1025;
					Integer amount = grossamount;
					Integer fee = (2 * amount) / 100;
					coin.setProfit(coin.getProfit() + fee);
					TransactionModel transactionModel = new TransactionModel();
					transactionModel.setCointype(buy.getCoinName());
					transactionModel.setDescription("order successfully accepted");
					transactionModel.setSellerId(sellerresult.getUserId());
					transactionModel.setBuyerId(buy.getUser().getUserId());
					transactionModel.setStatus("completed");
					transactionModel.setTransactionCreatedOn(new Date());
					transactionModel.setTransationFee(fee);
					transactionModel.setExchangeRate(exchangerate);
					transactionModel.setNetAmount(amount);
					transactionModel.setCoinQuantity(coinQuantity);
					transactionModel.setGrossAmount((fee + exchangerate + amount));
					transactionRepository.save(transactionModel);
					orderdata.save(sellerresult);
					orderdata.save(buy);
				  coindata.save(coin);
					if(sellerresult.getSellettype().equals("admin"))
					updateWalletOfAdmin(sellerresult,sellCoinQuantity,amount,fee);
					else
				updateWalletOfSeller(sellerresult);
					updateWalletOfBuyer(buy);
					System.out.println(sellerresult.getCoinQuantity());
					System.out.println(sellCoinQuantity);
					System.out.println(buy.getCoinQuantity());
				}

			}
		}
		return sellerresult;
	}


	private String updateWalletOfAdmin(OrderModel data,int coinq,int amount,int fee) {
		CoinManagementModel result=coindata.findByCoinIdAndCoinName(data.getUserId(), data.getCoinName());
		result.setCoinInINR(result.getCoinInINR()+amount);
		result.setInitialSupply(result.getInitialSupply()-coinq);
		result.setProfit(result.getProfit()+fee);
		coindata.save(result);
		return "success";
	}

	private String updateWalletOfSeller(OrderModel data) {
		UserModel userresult = userdata.findByUserId(data.getUser().getUserId());
		List<WalletModel> walletModels = userresult.getWalletModel();
		for (WalletModel walletype : walletModels) {
			if (walletype.getWalletType().equals(data.getCoinName())) {
				WalletModel fiatwallet = walletModels.get(0);
				fiatwallet.setAmount((fiatwallet.getAmount() + data.getGrossAmount()));
				walletype.setAmount(walletype.getAmount() - data.getCoinQuantity());
				userresult.getWalletModel().add(walletype);
				userresult.getWalletModel().add(fiatwallet);
				userdata.save(userresult);
				return "success";
			}

		}
		return "error";

	}

	private String updateWalletOfBuyer(OrderModel data) {
		UserModel userresult = userdata.findByUserId(data.getUser().getUserId());
		List<WalletModel> walletModels = userresult.getWalletModel();
		for (WalletModel walletype : walletModels) {
			if (walletype.getWalletType().equals(data.getCoinName())) {

				WalletModel fiatwallet = walletModels.get(0);
				fiatwallet.setAmount((fiatwallet.getAmount() - data.getGrossAmount()));
				walletype.setAmount(walletype.getAmount() + data.getCoinQuantity());
				userresult.getWalletModel().add(walletype);
				userresult.getWalletModel().add(fiatwallet);
				userdata.save(userresult);
				return "success";
			}

		}
		return "error";

	}

	//-***---buyer***---
	public List<OrderModel> getAllBuyer()
	{
		List<OrderModel> data=orderdata.findAllByOrderTypeAndStatus("buyer","pending");
		if(data.isEmpty())
			throw new RuntimeException("buyer not found");
		return data;
		
	}
	//----getallseller-----
	public List<OrderModel> getAllSeller()
	{
		List<OrderModel> data=orderdata.findAllByOrderTypeAndStatus("seller","pending");
		if(data.isEmpty())
			throw new RuntimeException("seller not found");
		return data;
	}
	public List<TransactionModel> getAllTransaction()
	{
		List<TransactionModel> data=transactionRepository.findAll();
		if(data.isEmpty())
			throw new RuntimeException("no transaction available");
		return data;
		
	}
	public List<OrderModel> getAllOrder()
	{
		List<OrderModel> data=orderdata.findAll();
		if(data.isEmpty())
			throw new RuntimeException("no order available");
		return data;
		
	}
}
