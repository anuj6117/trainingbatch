package com.example.adarsh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adarsh.domain.CoinManagement;
import com.example.adarsh.domain.OrderTabel;
import com.example.adarsh.domain.Transaction;
import com.example.adarsh.domain.User;
import com.example.adarsh.domain.Wallet;
import com.example.adarsh.repository.CoinManagementRepository;
import com.example.adarsh.repository.OrderRepository;
import com.example.adarsh.repository.TransactionRepository;
import com.example.adarsh.repository.WalletRepository;

@Service
public class TransactionService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private CoinManagementRepository coinManagementRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction gettransaction() {

		System.err.println(
				"++++++++++++++++++++++++++++Transaction function+++++++++++++++++++++++++++++++++++++++++++++");

		ArrayList<OrderTabel> buyers = (ArrayList<OrderTabel>) orderRepository.findAllByOrderTypeAndStatus("buyer",
				"pending");
		if (buyers.isEmpty()) {
			throw new RuntimeException("Buyer are not available");
		}
		ArrayList<OrderTabel> sellers = (ArrayList<OrderTabel>) orderRepository.findAllByOrderTypeAndStatus("sell",
				"pending");

		if (sellers.isEmpty()) {
			throw new RuntimeException("seller are not available ");
		}

		Transaction trans = null;
		OrderTabel buyer = null;
		OrderTabel seller = null;
		Boolean flag = true;
		// ************************************************************************************

		System.err.println(
				"++++++++++++++++++++++++++++++++++++++Condition for buyer and seller list++++++++++++++++++++++++++");

		for (OrderTabel allBuyers : buyers) {
			System.out.println(
					"::::::::::::::::::::::::::::::::::::buyers loop started::::::::::::::::::::::::::::::::::::::::::::");
			for (OrderTabel allSellers : sellers) {
				System.out.println(
						":::::::::::::::::::::::::::::::::::sellers loop started::::::::::::::::::::::::::::::::::::::::::::");
				if (allBuyers.getQuoteValue() >= allSellers.getQuoteValue()
						&& allBuyers.getCoinName().equals(allSellers.getCoinName())
						&& allBuyers.getAmount() <= allSellers.getAmount()
						&& allSellers.getStatus().equals("pending")) {
					System.out.println(
							":::::::::::::::::::::::::::::::order match:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

					flag = false;
					buyer = allBuyers;
					seller = allSellers;

				}
				System.out.println(
						"::::::::::::::::::::::::::::::::::::::::sellers loop end::::::::::::::::::::::::::::::::::::::::::::");
			}

			System.err.println("++++++++++++----------------------------");
			if (flag) {
				throw new NullPointerException("No any seller are available ");

			}

			// ********************************************************************************++**********
			System.err.println(
					"++++++++++++++++++++++++++++++++++++++++++++Condition for order match++++++++++++++++++++++++++++++");

			if (buyer.getQuoteValue() == seller.getQuoteValue()) {
				buyer.setStatus("completed");
				orderRepository.save(buyer);
				seller.setStatus("Completed");
				orderRepository.save(seller);
			}

			if (buyer.getQuoteValue() <= seller.getQuoteValue()) {
				buyer.setStatus("pending");
				seller.setAmount(seller.getAmount() - buyer.getAmount());
				seller.setStatus("Completed");
			}
			seller.setAmount(seller.getAmount() - buyer.getAmount());
			seller.setStatus("pending");
			orderRepository.save(seller);
			buyer.setStatus("completed");
			orderRepository.save(buyer);

			// *****************************************************************************************
			System.err.println("++++++++++++++++++++++++++++++++++++walletList through user++++++++++++++++++++++++++");

			User buyeruserData = buyer.getUserModelInOrderModel();
			User selleruserData = seller.getUserModelInOrderModel();
			Wallet buyerDefaultWallet = null;
			Wallet buyerCurrentWallet = null;

			if (buyer.getStatus().equals("completed")) {
				List<Wallet> buyerWallets = buyeruserData.getWallet();
				for (Wallet buyerWall : buyerWallets) {
					buyerDefaultWallet = buyerWallets.get(0);
					if (buyerWall.getWalletType().equals(buyer.getCoinName())) {
						buyerCurrentWallet = buyerWall;
					}
				}
			}
			buyerCurrentWallet.setBalance(buyerCurrentWallet.getShadowBalance());
			buyerDefaultWallet.setBalance(buyerDefaultWallet.getShadowBalance());
			walletRepository.save(buyerDefaultWallet);
			walletRepository.save(buyerCurrentWallet);

			//
			// ****************************************************************************************
			System.err.println(
					"+++++++++++++++++++++++++++++++++++++++++++Condition for coinManagement++++++++++++++++++++++++++");

			CoinManagement coin = coinManagementRepository.findByCoinName(seller.getCoinName());
			if (coin.getInitialSupply() >= buyer.getAmount()) {
				Double newIntialSupply = coin.getInitialSupply() - buyer.getAmount();
				coin.setInitialSupply(newIntialSupply);

				Double profit = (buyer.getAmount() * 100) * 2 / 100;
				coin.setProfit(profit);
				System.out.println(":::::::::::::::" + profit);

				Double newCoinInInR = coin.getPrice() * buyer.getAmount();
				coin.setCoinInr(newCoinInInR);
				System.out.println(":::::::::::::::" + newCoinInInR);
			} else {
				coin.setInitialSupply((double) 0);
				Double newCoinInInR = coin.getInitialSupply() * coin.getPrice();
				coin.setCoinInr(newCoinInInR);

			}

			coinManagementRepository.save(coin);

			// ***************************************************************************************************
			System.err.println(
					"++++++++++++++++++++++++Create Transaction+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			Transaction transactionModel = new Transaction();
			transactionModel.setCointype(buyer.getCoinName());
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setInQuantity(buyer.getAmount());
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setStatus("complete");
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setTransactionCreatedOn(new Date());
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			// transactionModel.setNetAmount(newIntialSupply * coin.getPrice());
			transactionModel.setTransationFee(coin.getFee());
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setExchangeRate(5);
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			Double grossAmount = (buyer.getGrossAmount() + seller.getGrossAmount()) / 2;
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setGrossAmount(grossAmount);
			transactionModel.setBuyerId(buyer.getUserId());
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setSellerId(0);
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			transactionModel.setDescription("None");
			System.out.println(":::::::::++++++++++++++++++++++++:::");
			trans = transactionRepository.save(transactionModel);

			System.out.println(":::::::::::::::buyers loop end::::::::::::::::::::::::::::::::::::::::::::");
		}

		return trans;
	}

	public List<Transaction> getAllTransaction() {
		List<Transaction> data = transactionRepository.findAll();
		if (data.isEmpty()) {
			throw new RuntimeException("transaction is empty");
		}

		return data;
	}
}
