package com.example.demo.model.userModel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer orderId;
	private Integer amount;
	private Float fee;
	private String orderType;
	private Integer quote;
	private Date orderCreatedOn;
	private String status;
	@Transient
	private Long userId;
	private String coinName;
	
	@ManyToOne
	@JoinColumn(name = "UserOrderId")
	@JsonIgnore
	private UserModel user;
	public Integer getQuote() {
		return quote;
	}
	public void setQuote(Integer quote) {
		this.quote = quote;
	}
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}
	@CreatedDate
	@Temporal(TemporalType.DATE)
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	
}
