package com.example.demo.utils;

import java.util.Comparator;

import com.example.demo.model.OrderModel;

public class DateComparator implements Comparator<OrderModel>  {

	@Override
	public int compare(OrderModel minCompare, OrderModel listCompare) {
		// TODO Auto-generated method stub
		return (minCompare.getOrderCreatedOn()).compareTo(listCompare.getOrderCreatedOn());
	}

}
