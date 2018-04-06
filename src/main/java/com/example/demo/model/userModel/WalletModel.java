package com.example.demo.model.userModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.example.demo.constant.WalletEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WalletModel {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)	
private long walletId;
//@NotEmpty(message="amount not empty")
private Integer amount;
private Integer shadoBalance;
@NotEmpty(message="walletType not empty")
private String walletType=WalletEnum.fiat.toString();
private long randemId;
@ManyToOne
@JoinColumn(name = "User_id")
@JsonIgnore
private UserModel userdata;

public long getWalletId() {
	return walletId;
}
public void setWalletId(long walletId) {
	this.walletId = walletId;
}
public UserModel getUserdata() {
	return userdata;
}
public long getRandemId() {
	return randemId;
}
public void setRandemId(long randemId) {
	this.randemId = randemId;
}
public void setUserdata(UserModel userdata) {
	this.userdata = userdata;
}


public String getWalletType() {
	return walletType;
}
public Integer getAmount() {
	return amount;
}
public void setAmount(Integer amount) {
	this.amount = amount;
}
public Integer getShadoBalance() {
	return shadoBalance;
}
public void setShadoBalance(Integer shadoBalance) {
	this.shadoBalance = shadoBalance;
}
public void setWalletType(String walletType) {
	this.walletType = walletType;
}
}
