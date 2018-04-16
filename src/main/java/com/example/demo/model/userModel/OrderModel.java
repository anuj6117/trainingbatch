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
	private Long orderId;
	private Integer coinQuantity;
	private Integer fee;
	private String orderType;
	private Integer price;
	private Integer grossAmount;
	private Date orderCreatedOn;
	private String status;
	@Transient
	private Long userId;
	private String coinName;
	@ManyToOne
	@JoinColumn(name = "UserOrderId")
	@JsonIgnore
	private UserModel user;
	
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
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
	
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
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
