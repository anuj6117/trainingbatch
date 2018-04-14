package com.example.traning.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "coin")
@EntityListeners(AuditingEntityListener.class)
public class Coin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long coinId ;
	@Pattern(regexp = "[a-zA-z]*",message="Number and special charecter not accepted")
	@Size(min=3,max=20,message="lenght must be 2 to 20")
	private String coinName;
	private String symbol;
	private Integer fee=2;
	private float profit;
	
	private float coinInINR;
	
	public float getProfit() {
		return profit;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}
	public float getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(float coinInINR) {
		this.coinInINR = coinInINR;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	private float initialSupply;
	private long price;
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
	public float getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(float initialSupply) {
		this.initialSupply = initialSupply;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	
	
	
	
	
	
	

}
