package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderModel;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepo;
	@Autowired
	private OrderRepository orderRepo;
	List<OrderModel> buyerList= new ArrayList<OrderModel>();
	
	public void createTransaction() {
		List<OrderModel> buyerOrder;
		List<OrderModel> sellerOrder=orderRepo.findByOrderType("sell");
		for(OrderModel sellorder : sellerOrder) {
			buyerOrder = orderRepo.findBuyer(sellorder.getCoinName(),"buy",(Float)sellorder.getQuoteValue());
			for(OrderModel buyorder : buyerOrder) {
				if(sellorder.getTradingAmount()<=buyorder.getTradingAmount()) {
					buyerList.add(buyorder);
				}
			}
		}
	}
}
