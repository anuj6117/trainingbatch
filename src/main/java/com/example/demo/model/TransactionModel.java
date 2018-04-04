package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class TransactionModel {
	@Id
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	@JsonIgnore
	 private UserModel userModel;
	
	
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}
	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
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
	public Float getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Float grossAmount) {
		this.grossAmount = grossAmount;
	}

}
