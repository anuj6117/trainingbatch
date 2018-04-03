package com.example.demo.model;

import java.util.Date;

public class TransactionModel {
	
	private Integer transactionId;
	private String type;
	private String status;
	private String userName;
	private Date transactionCreatedOn;
	private Float netAmount;
	private Float exchangeRate;
	private String description;
	private Integer buyerId;
	private Integer sellerId;
	private Float grossAmount;
}
