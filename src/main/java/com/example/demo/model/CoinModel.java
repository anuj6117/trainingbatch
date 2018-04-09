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

	private Integer coinId;
	private Long profit=0l;
	private Long fees=0l;
	private Long INRConvergent=0l;
	private String coinName;
	private String symbol;
	private Long initialSupply =100000l;
	private Long price=0l;
	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}
	public Long getProfit() {
		return profit;
	}
	public void setProfit(Long profit) {
		this.profit = profit;
	}
	public Long getFees() {
		return fees;
	}
	public void setFees(Long fees) {
		this.fees = fees;
	}
	public Long getINRConvergent() {
		return INRConvergent;
	}
	public void setINRConvergent(Long iNRConvergent) {
		INRConvergent = iNRConvergent;
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
	public Long getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(Long initialSupply) {
		this.initialSupply = initialSupply;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}


}
