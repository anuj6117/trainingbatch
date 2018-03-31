package com.example.trainingnew.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="coins")
public class Coinmodel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long coinId;
	
	private String coinName;
	private String symbol;
	private double intialSupply=0.0;
	private double price=5.0;
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
	public double getIntialSupply() {
		return intialSupply;
	}
	public void setIntialSupply(double intialSupply) {
		this.intialSupply = intialSupply;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
