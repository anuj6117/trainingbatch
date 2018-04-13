package com.example.adarsh.domain;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orderTabel")
public class OrderTabel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	@NotNull(message = "Amount can't be null")
	private Double amount;
	private Double fee;
	@NotNull(message = "QuoteValue can't be null")
	private Double quoteValue;
	private String orderType;
	private Date orderCreatedOn;
	private String status = "pending";
	@NotNull(message = "Coin name can't be null")
	@NotBlank(message = "Coin name can't be null")
	private String coinName;
	private Double grossAmount;
	@Transient
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_order_fk")
	@JsonIgnore
	private User userModelInOrderModel;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getQuoteValue() {
		return quoteValue;
	}

	public void setQuoteValue(Double quoteValue) {
		this.quoteValue = quoteValue;
	}

	public Double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
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

	public User getUserModelInOrderModel() {
		return userModelInOrderModel;
	}

	public void setUserModelInOrderModel(User userModelInOrderModel) {
		this.userModelInOrderModel = userModelInOrderModel;
	}

}