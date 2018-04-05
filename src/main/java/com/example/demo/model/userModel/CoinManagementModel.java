package com.example.demo.model.userModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class CoinManagementModel {
	
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private long coinId;
@NotEmpty(message="coinname not empty")
private String coinName;
@NotEmpty(message="symble not empty")
private String symbol;
//@NotEmpty(message="initialSupply not empty")
//@Size(min=2,max=10,message="must be 2 digit")
//@Pattern(regexp="^(0|[1-9][0-9]*)$")
private int initialSupply;
//@Size(min=2,max=10,message="must be 2 digit")
//@NotEmpty(message="price not empty")
//@Pattern(regexp="^(0|[1-9][0-9]*)$")
private int price;


public long getCoinId() {
	return coinId;
}
public void setCoinId(long coinId) {
	this.coinId = coinId;
}
public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}

public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public int getInitialSupply() {
	return initialSupply;
}
public void setInitialSupply(int initialSupply) {
	this.initialSupply = initialSupply;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
}
