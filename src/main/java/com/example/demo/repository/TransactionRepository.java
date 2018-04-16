package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TransactionModel;

public interface TransactionRepository extends CrudRepository<TransactionModel,Integer>{

	
	public TransactionModel findByTransactionIdAndStatus(Integer transactionId,String status);
	public TransactionModel findByBuyerId(Integer buyerId);
	public List<TransactionModel> findByBuyerIdAndCurrencyType(Integer buyerId,String currencyType);
	//public TransactionModel findByBuyerIdAndCurrencyType(Integer buyerId,String currencyType);
}
