package com.crud.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.crud.demo.enums.WalletType;
import com.crud.demo.id.randomgenerator.RandomIDGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="UserWallet")
public class UserWallet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer walletId;
	private Integer wallet_random_id=RandomIDGenerator.randomIdGenerator().nextInt(100);
	private float balance;//never use primitive types
	private float shadowBalance;
	private String walletType = WalletType.fiate.toString();
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_wallet_fk",nullable=false)
	@JsonIgnore
	private User user;
	
	
	
	
	public Integer getWalletId() {
		return walletId;
	}
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}
	public Integer getWallet_random_id() {
		return wallet_random_id;
	}
	public void setWallet_random_id(Integer wallet_random_id) {
		this.wallet_random_id = wallet_random_id;
	}
	
	
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getShadowBalance() {
		return shadowBalance;
	}
	public void setShadowBalance(float shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
