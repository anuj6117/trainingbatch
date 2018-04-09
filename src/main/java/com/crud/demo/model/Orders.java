package com.crud.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Orders {//we can't take Order as class name for this model
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private Integer fee=0;
	private String orderType;
	private Date orderCreatedOn=new Date();
	private String status="pending";
	private String coinName;
	private Integer quoteValue=0;
	private Integer grossAmount=0;
	private Integer coinQuantity=0;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_userId",nullable=false)
	@JsonIgnore
	private User user;
	
	@Transient
	private Integer userId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
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

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public Integer getQuoteValue() {
		return quoteValue;
	}

	public void setQuoteValue(Integer quoteValue) {
		this.quoteValue = quoteValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Integer getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
