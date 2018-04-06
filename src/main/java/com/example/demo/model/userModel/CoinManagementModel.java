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

private Integer initialSupply;
private Integer price;
private Integer profit;
private Integer coinInINR;
private Integer fee;

public Integer getFee() {
	return fee;
}
public void setFee(Integer fee) {
	this.fee = fee;
}
public Integer getProfit() {
	return profit;
}
public void setProfit(Integer profit) {
	this.profit = profit;
}
public Integer getCoinInINR() {
	return coinInINR;
}
public void setInitialSupply(Integer initialSupply) {
	this.initialSupply = initialSupply;
}
public Integer getInitialSupply() {
	return initialSupply;
}
public Integer getPrice() {
	return price;
}
public void setPrice(Integer price) {
	this.price = price;
}
public void setCoinInINR(Integer coinInINR) {
	this.coinInINR = coinInINR;
}
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


}
