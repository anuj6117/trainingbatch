package com.crud.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
public class CoinManagement implements Cloneable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
	
	@NotEmpty(message="Coinname must not be empty")
	@NotBlank(message="Space Not Accepted")
	@NotNull(message="Coinname canot be null")
	private String  coinName;
	@NotEmpty(message="Symbol must not be empty")
	@NotBlank(message="Space Not Accepted")
	@NotNull(message="Symbol canot be null")
	private String symbol;
	
	
	private Integer initialSupply=0;
	private Integer price=0;
	private Integer profit=0;
	private Integer fees=2;
	private Integer INRConvergent=0;
	
	public Object clone() throws
    CloneNotSupportedException
{
return super.clone();
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
