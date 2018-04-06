package com.example.trainingnew.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="coins")
public class Coinmodel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long coinId;
	
	@NotEmpty(message="Coin name can't be null")
	@NotBlank(message="Space Not Accepted")
	private String coinName;
	
	@NotEmpty(message="Coin symbol can't be null")
	@NotBlank(message="Space Not Accepted")
	private String symbol;
	private Double initialSupply=0.0;
	private Double price=0.0;
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
	public Double getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(Double initialSupply) {
		this.initialSupply = initialSupply;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
