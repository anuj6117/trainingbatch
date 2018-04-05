package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.userDTO.WalletDTO;
import com.example.demo.model.userModel.OrderModel;
import com.example.demo.model.userModel.TransactionModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.OrderRepository;
import com.example.demo.repoINterface.TransactionRepository;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.WalletRepostiory;

@Service
public class TransactionServices {

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
	Integer quote=0;
	List<OrderModel> buyer=orderdata.findAllByOrderType("buyer");
	List<OrderModel> seller=orderdata.findAllByOrderType("Seller");
	OrderModel sellerresult=null;
	List<OrderModel> data=null;
	String getsellorder=null;
	String getbuyorder=null;
	for(OrderModel buy:buyer )
	{	
		quote=buy.getQuote();
		data=orderdata.findByCoin(buy.getCoinName(),"seller",buy.getQuote());
		for(OrderModel getresult:data)
		{
			if(quote>getresult.getQuote())
			{
				quote=getresult.getQuote();
				sellerresult=getresult;
			}
		}
		if(buy.getAmount()==sellerresult.getAmount())
		{
		 getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
		getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
		sellerresult.setStatus("completed");
		buy.setStatus("completed");
		buy.setCoinName("fiat");
		sellerresult.setCoinName("fiat");
		 getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(sellerresult));
			getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(buy));
		}
		else if(buy.getAmount()<sellerresult.getAmount())
		{
			sellerresult.setAmount(sellerresult.getAmount()-buy.getAmount());
			sellerresult.setStatus("pending");
			buy.setStatus("completed");
			getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
			getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
			buy.setCoinName("fiat");
			sellerresult.setCoinName("fiat");
			 getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(sellerresult));
				getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(buy));
		}
		else if(buy.getAmount()>sellerresult.getAmount())
		{
			buy.setAmount(buy.getAmount()-sellerresult.getAmount());
			sellerresult.setStatus("completed");
			buy.setStatus("pending");
			getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(buy));
			getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(sellerresult));
			buy.setCoinName("fiat");
			sellerresult.setCoinName("fiat");
			 getsellorder=walletfunction.AddMoneyInWallet(convertModelToDTO(sellerresult));
				getbuyorder=walletfunction.withdrawMoneyInWallet(convertModelToDTO(buy));
		}
		if(getbuyorder.equals("success")&&getsellorder.equals("success"))
		{
			float fee=25.5f;
			float exchangerate=1025f;
			float amount=(float)buy.getAmount();
			TransactionModel transactionModel=new TransactionModel();
			transactionModel.setCointype(buy.getCoinName());
			transactionModel.setDescription("order successfully accepted");
			transactionModel.setSellerId(sellerresult.getUser().getUserId());
			transactionModel.setBuyerId(buy.getUser().getUserId());
			transactionModel.setStatus("completed");
			transactionModel.setTransactionCreatedOn(new Date());
			transactionModel.setTransationFee(25.2f);
			transactionModel.setExchangeRate(1025.5f);
			transactionModel.setNetAmount(amount);
			transactionModel.setGrossAmount((fee+exchangerate+amount));
			transactionRepository.save(transactionModel);
			orderdata.save(sellerresult);
			orderdata.save(buy);
			
		}
		
	}
	return sellerresult;
}
private WalletDTO convertModelToDTO(OrderModel data)
{
	WalletDTO dto=new WalletDTO();
	dto.setUserId(data.getUser().getUserId());
	dto.setWalletType(data.getCoinName());
	dto.setAmount(data.getAmount());
	return dto;
}
}
