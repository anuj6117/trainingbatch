package com.example.trainingnew.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserWalletDTO {
	
	private Integer userId;
	private Double balance;
	
	@NotEmpty(message="Please Enter wallet Type")
	@NotNull(message="Please Enter wallet Type")
	@NotBlank(message="Please Enter wallet Type")
	private String walletType;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	
	
	

}
