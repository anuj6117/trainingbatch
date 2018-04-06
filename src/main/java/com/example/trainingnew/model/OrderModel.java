package com.example.trainingnew.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orderTable")
public class OrderModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private Float amount;
	private Float fee;
	private Float quote;
	private String orderType;
	private Date orderCreatedOn;
	private String status="pending";
	private String coinName;
	
	@Transient
	private long userId;
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "user_order_fk")
	@JsonIgnore
	 private UserModel userModelInOrderModel;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}
	
	public Float getQuote() {
		return quote;
	}

	public void setQuote(Float quote) {
		this.quote = quote;
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

	public UserModel getUserModelInOrderModel() {
		return userModelInOrderModel;
	}

	public void setUserModelInOrderModel(UserModel userModelInOrderModel) {
		this.userModelInOrderModel = userModelInOrderModel;
	}
	
	
}
