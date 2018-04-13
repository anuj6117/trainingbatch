package com.example.adarsh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.example.adarsh.constant.WalletEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer walletId;
	private Double balance=0.0;
	private Double shadowBalance=0.0;
	@NotEmpty(message = "walletType  must not be empty")
	@NotBlank(message = "Space Not Accepted")
	private String walletType=WalletEnum.fiat.toString();
	private Long randomId;
	
	@ManyToOne
	@JoinColumn(name = "User_id")
	@JsonIgnore
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	

	public Double getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(Double shadowBalance) {
		this.shadowBalance = shadowBalance;
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public Long getRandomId() {
		return randomId;
	}

	public void setRandomId(Long randomId) {
		this.randomId = randomId;
	}

	

}
