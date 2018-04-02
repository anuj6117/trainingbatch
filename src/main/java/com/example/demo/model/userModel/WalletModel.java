package com.example.demo.model.userModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.constant.WalletEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WalletModel {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)	
private long id;	
private int balance;
private int shadoBalance;
private String walletType=WalletEnum.fiat.toString();
private long randemId;
@ManyToOne
@JoinColumn(name = "User_id")
//@JsonIgnore
@JsonBackReference
private UserModel userdata;
public long getId() {
	return id;
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
public void setId(long id) {
	this.id = id;
}
public int getBalance() {
	return balance;
}
public void setBalance(int balance) {
	this.balance = balance;
}
public int getShadoBalance() {
	return shadoBalance;
}
public void setShadoBalance(int shadoBalance) {
	this.shadoBalance = shadoBalance;
}
public String getWalletType() {
	return walletType;
}
public void setWalletType(String walletType) {
	this.walletType = walletType;
}
}
