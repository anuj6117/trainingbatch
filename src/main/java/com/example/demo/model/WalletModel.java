package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.example.demo.constant.WalletEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WalletModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int walletId;
	@NotNull
	private int userIdW;
	private Long balance = 0l;
	private Long shadowBalance=0l;
	private String walletType = WalletEnum.Fiate.toString();
	private int walletHash;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	@JsonIgnore
	 private UserModel userModel;
	 
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public int getWalletHash() {
		return walletHash;
	}
	public void setWalletHash(int walletHash) {
		this.walletHash = walletHash;
	}
	public int getWalletId() {
		return walletId;
	}
	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}
	public int getUserIdW() {	
		return userIdW;
	}
	public void setUserIdW(int userId) {
		this.userIdW = userId;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getShadowBalance() {
		return shadowBalance;
	}
	public void setShadowBalance(Long shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

}
