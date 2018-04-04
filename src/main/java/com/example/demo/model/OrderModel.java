package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class OrderModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private Float tradingAmount;
	private Float fee;
	private String orderType;
	private Date orderCreatedOn;
	private String status="pending";
	private String coinName;
	private Float quoteValue;
	
	public Float getTradingAmount() {
		return tradingAmount;
	}
	public void setTradingAmount(Float tradingAmount) {
		this.tradingAmount = tradingAmount;
	}
	public Float getQuoteValue() {
		return quoteValue;
	}
	public void setQuoteValue(Float quoteValue) {
		this.quoteValue = quoteValue;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	@JsonIgnore
	 private UserModel userModel;
	
	
    public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
