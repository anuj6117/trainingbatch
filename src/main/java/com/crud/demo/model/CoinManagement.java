package com.crud.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CoinManagement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
	private String  coinName;
	private String symbol;//not usable
	private float initialSupply;// no primitive types
	private float price;
	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
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
	public float getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(float initialSupply) {
		this.initialSupply = initialSupply;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

}
