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

	public String transactionCheck() {
		Integer price = 0;
		int difference = 0;

		List<OrderModel> buyer = orderdata.findAllByOrderTypeAndStatus("buyer", "pending");
		List<OrderModel> seller = orderdata.findAllByOrderTypeAndStatus("seller", "pending");
		// if (seller.isEmpty())
		// throw new RuntimeException("seller not found");
		if (buyer.isEmpty())
			throw new RuntimeException("Buyer not found");
		OrderModel sellerresult = null;
		CoinManagementModel coinresult = null;
		List<OrderModel> data = null;
		WalletModel getsellorder = null;
		WalletModel getbuyorder = null;
		Integer grossamount = 0;
		Integer coinQuantity = 0;
		int getfee=0;
		int fee1=0,fee2=0,fee3=0;
		boolean useradmin=false;
		int getfee1=0;
		Integer sellCoinQuantity = 0;
		String orderresult = "";
		for (OrderModel buy : buyer) {
			boolean flag = true;
			boolean user = false;
			boolean admin = false;
			price = buy.getPrice();
			CoinManagementModel coinManagementModel = coindata.findByCoinName(buy.getCoinName());
			Integer coinprice = coinManagementModel.getPrice();
			data = orderdata.findByCoin(buy.getCoinName(), "seller", buy.getPrice(), "pending");
			if (data.isEmpty()) {
				flag = true;
				if (coinprice <= price) {
					admin = true;
					coinresult = coinManagementModel;
					difference = buy.getGrossAmount()
							- ((buy.getCoinQuantity() * coinresult.getPrice() + buy.getFee()));
				}
			}

			a: for (OrderModel getresult : data) {
				if (buy.getUser().getUserId() == getresult.getUser().getUserId()) {
					// admin=true;
					// coinresult = coinManagementModel;
					// difference = buy.getGrossAmount()
					// - ((buy.getCoinQuantity() * coinresult.getPrice() + buy.getFee()));
					continue;
				} else {
					flag = true;
					if (price >= getresult.getPrice()) {
						price = getresult.getPrice();
						if (coinprice <= price) {
							admin = true;
							coinresult = coinManagementModel;
							difference = buy.getGrossAmount()
									- ((buy.getCoinQuantity() * coinresult.getPrice() + buy.getFee()));
						} else {
							sellerresult = getresult;
							user = true;
							admin = false;
							// if(buy.getPrice()!=sellerresult.getPrice())
							difference = buy.getGrossAmount()
									- ((buy.getCoinQuantity() * sellerresult.getPrice() + buy.getFee()));

						}
					}
				}
			}

			if (flag) {
				if (user) {
					if (buy.getCoinQuantity() == sellerresult.getCoinQuantity()) {
						sellerresult.setStatus("completed");
						buy.setStatus("completed");
						orderresult = "success";
						grossamount = buy.getGrossAmount();
						getfee=buy.getFee();
						coinQuantity = buy.getCoinQuantity();
						buy.setCoinQuantity(sellerresult.getCoinQuantity());
						buy.setGrossAmount(buy.getGrossAmount());
						sellCoinQuantity = sellerresult.getCoinQuantity();
						difference = buy.getGrossAmount()
								- ((buy.getCoinQuantity() * sellerresult.getPrice() + buy.getFee()));

					} else if (buy.getCoinQuantity() < sellerresult.getCoinQuantity()) {
						grossamount = buy.getGrossAmount();
						coinQuantity = buy.getCoinQuantity();
						getfee=buy.getFee();
						System.out.println(getfee);
						sellerresult.setCoinQuantity(sellerresult.getCoinQuantity() - buy.getCoinQuantity());
						sellerresult.setGrossAmount(sellerresult.getCoinQuantity() * sellerresult.getPrice());
						sellerresult.setStatus("pending");
						buy.setStatus("completed");
						orderresult = "success";
						sellCoinQuantity = buy.getCoinQuantity();
						difference = buy.getGrossAmount()
								- ((buy.getCoinQuantity() * sellerresult.getPrice() + buy.getFee()));

					} else if (buy.getCoinQuantity() > sellerresult.getCoinQuantity()) {
						grossamount = sellerresult.getCoinQuantity() * buy.getPrice()+buy.getFee();
						coinQuantity = sellerresult.getCoinQuantity();
						difference = grossamount - ((coinQuantity * sellerresult.getPrice() + buy.getFee()));
						int fee = 2 * (grossamount-buy.getFee()) / 100;
						System.out.println(fee);
						getfee=fee;
						fee2=2*((buy.getCoinQuantity()-sellerresult.getCoinQuantity())*buy.getPrice())/100;
						System.out.println(fee2+"  fee2");
						grossamount = sellerresult.getCoinQuantity() * buy.getPrice()+fee;
						System.out.println(grossamount);
						buy.setCoinQuantity(buy.getCoinQuantity() - sellerresult.getCoinQuantity());
						buy.setGrossAmount((buy.getCoinQuantity() * buy.getPrice()) + fee2);
						sellerresult.setStatus("completed");
						buy.setStatus("pending");
						orderresult = "success";
						sellCoinQuantity = sellerresult.getCoinQuantity();
						
						buy.setFee(fee2);
						System.out.println(buy.getFee());
						System.out.println(difference);
						System.out.println(fee);
					}
				} else if (admin) {

					if (!(buy.getCoinQuantity() > coinresult.getInitialSupply())) {
						
						buy.setStatus("completed");
						orderresult = "success";
//						getfee1=buy.getFee();
						grossamount = buy.getGrossAmount();
						coinQuantity = buy.getCoinQuantity();
						int fee = 2 * (grossamount-buy.getFee()) / 100;
						

						System.out.println(fee);
						
							getfee=buy.getFee();
						difference = buy.getGrossAmount()
									- ((buy.getCoinQuantity() * coinresult.getPrice() + buy.getFee()));
						buy.setFee(fee);
						//System.out.println(grossamount);
					}
					
				}
				if (orderresult.equals("success")) {
					// CoinManagementModel coin = new CoinManagementModel();
					// coin = coindata.findByCoinName(buy.getCoinName());
					System.out.println(price);
					Integer amount = grossamount;
					Integer fee = (2 * amount) / 100;
				
					coinManagementModel.setProfit(coinManagementModel.getProfit() + getfee);
					TransactionModel transactionModel = new TransactionModel();
					transactionModel.setCointype(buy.getCoinName());
					transactionModel.setDescription("order successfully accepted");
					if (admin) {
						coinManagementModel.setCoinInINR(coinManagementModel.getCoinInINR()
								+ (buy.getCoinQuantity() * coinManagementModel.getPrice() + difference));
						transactionModel.setSellerId(0l);
						coinManagementModel
								.setInitialSupply(coinManagementModel.getInitialSupply() - buy.getCoinQuantity());

					} else {
						coinManagementModel.setCoinInINR(coinManagementModel.getCoinInINR() + difference);
						transactionModel.setSellerId(sellerresult.getUser().getUserId());
						System.out.println(difference);
						// coinManagementModel.setInitialSupply(coinManagementModel.getInitialSupply()-buy.getCoinQuantity());
					}
					transactionModel.setBuyerId(buy.getUser().getUserId());
					transactionModel.setStatus("completed");
					transactionModel.setTransactionCreatedOn(new Date());
					transactionModel.setTransationFee(getfee);
					transactionModel.setNetAmount(amount);
					transactionModel.setCoinQuantity(coinQuantity);
					transactionModel.setGrossAmount((amount));
					transactionModel.setPrice(buy.getPrice());
					String result = updateWalletOfBuyer(buy, grossamount, coinQuantity);
					if (result.equals("error"))
						throw new RuntimeException("buy dont hava more amount");
					transactionRepository.save(transactionModel);
					orderdata.save(sellerresult);
					orderdata.save(buy);
					coindata.save(coinManagementModel);
					updateWalletOfSeller(sellerresult);
					return "success";
				}

			}
		}
		return "success";
	}

	private String updateWalletOfAdmin(OrderModel data, int coinq, int amount, int fee) {
		CoinManagementModel result = coindata.findByCoinIdAndCoinName(data.getUserId(), data.getCoinName());
		result.setCoinInINR(result.getCoinInINR() + amount);
		result.setInitialSupply(result.getInitialSupply() - coinq);
		// result.setProfit(result.getProfit()+fee);
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
				fiatwallet.setShadoBalance(fiatwallet.getShadoBalance() + data.getGrossAmount());
				walletype.setAmount(walletype.getAmount() - data.getCoinQuantity());
				userresult.getWalletModel().add(walletype);
				userresult.getWalletModel().add(fiatwallet);
				userdata.save(userresult);
				return "success";
			}

		}
		return "error";

	}

	private String updateWalletOfBuyer(OrderModel data, Integer grossamount, Integer coinquantity) {
		UserModel userresult = userdata.findByUserId(data.getUser().getUserId());
		List<WalletModel> walletModels = userresult.getWalletModel();
		for (WalletModel walletype : walletModels) {
			if (walletype.getWalletType().equals(data.getCoinName())) {

				WalletModel fiatwallet = walletModels.get(0);
				int fee = 2 * grossamount / 100;
				Integer buyamount = fiatwallet.getAmount() - grossamount;
				if (buyamount < 0)
					return "error";
				fiatwallet.setAmount((fiatwallet.getAmount() - grossamount));
				walletype.setAmount(walletype.getAmount() + coinquantity);
				walletype.setShadoBalance(walletype.getShadoBalance() + coinquantity);
				// fiatwallet.setShadoBalance(fiatwallet.getShadoBalance()-data.getGrossAmount());
				userresult.getWalletModel().add(walletype);
				userresult.getWalletModel().add(fiatwallet);
				userdata.save(userresult);
				return "success";
			}

		}
		return "error";

	}

	// -***---buyer***---
	public List<OrderModel> getAllBuyer() {
		List<OrderModel> data = orderdata.findAllByOrderTypeAndStatus("buyer", "pending");
		if (data.isEmpty())
			throw new RuntimeException("buyer not found");
		return data;

	}

	// ----getallseller-----
	public List<OrderModel> getAllSeller() {
		List<OrderModel> data = orderdata.findAllByOrderTypeAndStatus("seller", "pending");
		if (data.isEmpty())
			throw new RuntimeException("seller not found");
		return data;
	}

	public List<TransactionModel> getAllTransaction() {
		List<TransactionModel> data = transactionRepository.findAll();
		if (data.isEmpty())
			throw new RuntimeException("no transaction available");
		return data;

	}

	public List<OrderModel> getAllOrder() {
		List<OrderModel> data = orderdata.findAll();
		if (data.isEmpty())
			throw new RuntimeException("no order available");
		return data;
	}

}
