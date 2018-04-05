package com.example.demo.model.userModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class TransactionModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long transactionId;
	private String cointype;
	private String status;
	private Date transactionCreatedOn;
	private Float netAmount;
	private Float transationFee;
	private Float exchangeRate;
	private String description;
	private Long buyerId;
	private Long sellerId;
	private Float grossAmount;

	public Float getTransationFee() {
		return transationFee;
	}

	public void setTransationFee(Float transationFee) {
		this.transationFee = transationFee;
	}

	
	


	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getCointype() {
		return cointype;
	}

	public void setCointype(String cointype) {
		this.cointype = cointype;
	}

	public Float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Float netAmount) {
		this.netAmount = netAmount;
	}

	public Float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}

	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}

	

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Float getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Float grossAmount) {
		this.grossAmount = grossAmount;
	}

}
