package com.example.adarsh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class CoinManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long coinId;
	private String coinName;
	private String symbol;
	private int initialSupply;
	private int price;

	public Long getCoinId() {
		return coinId;
	}

	public void setCoinId(Long coinId) {
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
