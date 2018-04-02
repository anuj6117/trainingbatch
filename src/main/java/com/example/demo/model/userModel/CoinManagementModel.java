package com.example.demo.model.userModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CoinManagementModel {
	
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private long id;
private String coinName;

private String symble;
private int initialSupply;
private int price;

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
public String getSymble() {
	return symble;
}
public void setSymble(String symble) {
	this.symble = symble;
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
