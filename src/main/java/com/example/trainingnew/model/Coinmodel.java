package com.example.trainingnew.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="coins")
public class Coinmodel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer coinId;
	
	@NotEmpty(message="Coin name can't be null")
	@NotBlank(message="Space Not Accepted")
	private String coinName;
	
	@NotEmpty(message="Coin symbol can't be null")
	@NotBlank(message="Space Not Accepted")
	private String symbol;
	@NotNull(message="Intial Supply can't be null")
	private Double initialSupply=0.0;
	@NotNull(message="Price can't be null")
	private Double price=0.0;
	private Integer fee;
	private Double profit;
	private Double coinInINR;
	
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Double getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(Double coinInINR) {
		this.coinInINR = coinInINR;
	}
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
