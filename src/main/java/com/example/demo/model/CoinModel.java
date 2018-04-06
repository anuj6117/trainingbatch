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
	private Integer profit=0;
	private Integer fees=0;
	private Integer INRConvergent=0;
	private String coinName;
	private String symbol;
	private Long initialSupply =100000l;
	private Integer price;
	public Long getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(Long initialSupply) {
		this.initialSupply = initialSupply;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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
