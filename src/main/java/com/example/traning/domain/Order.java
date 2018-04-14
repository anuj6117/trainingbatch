package com.example.traning.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "orderTable")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdOn"}, allowGetters = true)
public class Order {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private Float tradingAmount;
	private Float fee;
	private String orderType;
	@Column(nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderCreatedOn;
	private String status="pending";
	private String coinName;
	private Float quoteValue;
	
	private Float grossAmount;
	@Transient
	private Long userId;
	
	
	//==================================================================
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserid(Long userId) {
		this.userId = userId;
	}
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="user_order_fk")
	@JsonIgnore
	private Register register;
	
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}
	
	
	//==================================================================
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Float getTradingAmount() {
		return tradingAmount;
	}
	public void setTradingAmount(Float tradingAmount) {
		this.tradingAmount = tradingAmount;
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
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public Float getQuoteValue() {
		return quoteValue;
	}
	public void setQuoteValue(Float quoteValue) {
		this.quoteValue = quoteValue;
	}

	public Float getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Float grossAmount) {
		this.grossAmount = grossAmount;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
		

}
