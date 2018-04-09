package com.crud.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer transactionId;
	private Integer coinQuantity;
	private String cointype;
	private String status;
	private Date transactionCreatedOn;
	private Integer netAmount=0;
	private Integer transationFee=0;
	private Integer exchangeRate=0;
	private Integer grossAmount=0;
	private Integer buyerId=0;
	private Integer sellerId=0;
	private String description;
	
	
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public String getCointype() {
		return cointype;
	}
	public void setCointype(String cointype) {
		this.cointype = cointype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}
	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}
	public Integer getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Integer netAmount) {
		this.netAmount = netAmount;
	}
	public Integer getTransationFee() {
		return transationFee;
	}
	public void setTransationFee(Integer transationFee) {
		this.transationFee = transationFee;
	}
	public Integer getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Integer getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
