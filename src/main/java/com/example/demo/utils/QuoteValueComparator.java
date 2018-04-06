package com.example.demo.utils;

import java.util.Comparator;

import com.example.demo.model.OrderModel;

public class QuoteValueComparator implements Comparator<OrderModel>  {

	@Override
	public int compare(OrderModel minCompare, OrderModel listCompare) {
		// TODO Auto-generated method stub
		return (minCompare.getQuoteValue()).compareTo(listCompare.getQuoteValue());
	}

}
