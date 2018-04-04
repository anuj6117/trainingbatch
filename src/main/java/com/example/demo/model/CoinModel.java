package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CoinModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int coinId;
	private String coinName;
	private String coinSymbol="nill";
	private long initialSupply = 0;
	private float price = 0;
	public long getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(long initialSupply) {
		this.initialSupply = initialSupply;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public int getCoinId() {
		return coinId;
	}

	public void setCoinId(int coinId) {
		this.coinId = coinId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getCoinSymbol() {
		return coinSymbol;
	}

	public void setCoinSymbol(String coinSymbol) {
		this.coinSymbol = coinSymbol;
	}

	

}
