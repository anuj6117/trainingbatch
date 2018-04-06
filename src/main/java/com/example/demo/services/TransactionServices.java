package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.userDTO.WalletDTO;
import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.model.userModel.OrderModel;
import com.example.demo.model.userModel.TransactionModel;
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

public OrderModel transactionChech()
{
	Integer price=0;
	List<OrderModel> buyer=orderdata.findAllByOrderTypeAndStatus("buyer","pending");
	List<OrderModel> seller=orderdata.findAllByOrderTypeAndStatus("seller","pending");
	if(seller.isEmpty())
		throw new RuntimeException("seller not found");
	if(buyer.isEmpty())
		throw new RuntimeException("Buyer not found");
	OrderModel sellerresult=null;
	List<OrderModel> data=null;
	WalletModel getsellorder=null;
	WalletModel getbuyorder=null;
	Integer grossamount=0;
	Integer coinQuantity=0;
	for(OrderModel buy:buyer )
	{	
		price=buy.getPrice();
		data=orderdata.findByCoin(buy.getCoinName(),"seller",buy.getPrice(),"pending");
		for(OrderModel getresult:data)
		{
			if(price>=getresult.getPrice())
			{
				price=getresult.getPrice();
				if(buy.getUser().getUserId()==getresult.getUser().getUserId()) {
					throw new RuntimeException("not found match");
					}
				sellerresult=getresult;
			}
		}
		if(buy.getCoinQuantity()==sellerresult.getCoinQuantity())
		{
		 getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
		getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
		sellerresult.setStatus("completed");
		buy.setStatus("completed");
		
		}
		else if(buy.getCoinQuantity()<sellerresult.getCoinQuantity())
		{
			grossamount=buy.getGrossAmount();
			coinQuantity=buy.getCoinQuantity();
			sellerresult.setGrossAmount(sellerresult.getGrossAmount()-buy.getGrossAmount());
			sellerresult.setCoinQuantity(sellerresult.getCoinQuantity()-buy.getCoinQuantity());
			sellerresult.setStatus("pending");
			buy.setStatus("completed");
			getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
			getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
			
		}
		else if(buy.getCoinQuantity()>sellerresult.getCoinQuantity())
		{
			grossamount=buy.getGrossAmount();
			coinQuantity=buy.getCoinQuantity();
			buy.setGrossAmount(buy.getGrossAmount()-sellerresult.getGrossAmount());
			buy.setCoinQuantity(buy.getCoinQuantity()-sellerresult.getCoinQuantity());
			sellerresult.setStatus("completed");
			buy.setStatus("pending");
			getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
			getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
			
		}
		if(getbuyorder!=null&&getsellorder!=null)
		{
			CoinManagementModel coin=new CoinManagementModel();
			coin=coindata.findByCoinName(buy.getCoinName());
			Integer exchangerate=1025;
			Integer amount=grossamount;
			Integer fee=(2*amount)/100;
			coin.setProfit(fee);
			TransactionModel transactionModel=new TransactionModel();
			transactionModel.setCointype(buy.getCoinName());
			transactionModel.setDescription("order successfully accepted");
			transactionModel.setSellerId(sellerresult.getUser().getUserId());
			transactionModel.setBuyerId(buy.getUser().getUserId());
			transactionModel.setStatus("completed");
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setTransationFee(fee);
			transactionModel.setExchangeRate(exchangerate);
			transactionModel.setNetAmount(amount);
			transactionModel.setCoinQuantity(coinQuantity);
			transactionModel.setGrossAmount((fee+exchangerate+amount));
			transactionRepository.save(transactionModel);
			orderdata.save(sellerresult);
			orderdata.save(buy);
			coindata.save(coin);
		}
		
	}
	return sellerresult;
}
private WalletDTO convertModelToDTO(OrderModel data)
{
	WalletDTO dto=new WalletDTO();
	dto.setUserId(data.getUser().getUserId());
	dto.setWalletType(data.getCoinName());
	dto.setAmount(data.getGrossAmount());
	return dto;
}
}
