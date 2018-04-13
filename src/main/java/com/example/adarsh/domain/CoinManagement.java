package com.example.adarsh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity

public class CoinManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long coinId;
	private String coinName;
	private String symbol;
	@NotNull(message="can not be null")
	private Double initialSupply;
	private int price;
	private Double coinInr=0.0;
	private Double profit=0.0;
	private int fee;

	public Long getCoinId() {
		return coinId;
	}

	public Double getCoinInr() {
		return coinInr;
	}

	public void setCoinInr(Double coinInr) {
		this.coinInr = coinInr;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
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

	public Double getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(Double newIntialSupply) {
		this.initialSupply = newIntialSupply;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
