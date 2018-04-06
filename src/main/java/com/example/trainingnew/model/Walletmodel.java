package com.example.trainingnew.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_wallet")
public class Walletmodel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long walletId;
	private Double balance=0.0;
	private Double shadowBalance=0.0;
	@NotNull(message="WalletType Can't be null")
	private String walletType="FIAT";

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "user_wallet_fk")
	@JsonIgnore
	private UserModel userModel;

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
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

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	

}
