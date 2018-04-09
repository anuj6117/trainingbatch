package com.crud.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CoinManagement implements Cloneable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
	private String  coinName;
	private String symbol;//not usable
	private Integer initialSupply=0;// no primitive types
	private Integer price=0;
	private Integer profit=0;
	private Integer fees=2;
	private Integer INRConvergent=0;
	
	
	
	
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
	public Integer getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(Integer initialSupply) {
		this.initialSupply = initialSupply;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	public Integer getFees() {
		return fees;
	}
	public void setFees(Integer fees) {
		this.fees = fees;
	}
	public Integer getINRConvergent() {
		return INRConvergent;
	}
	public void setINRConvergent(Integer iNRConvergent) {
		INRConvergent = iNRConvergent;
	}

}
