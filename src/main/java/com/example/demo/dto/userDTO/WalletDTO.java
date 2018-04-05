package com.example.demo.dto.userDTO;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class WalletDTO {
	
	@NotEmpty(message="id required")
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	private Long walletId;
	@NotEmpty(message="walletType required")
	private String walletType;
	@NotEmpty(message="amount required")
	@Pattern(regexp="^(0|[1-9][0-9]*)$")
	@NotBlank(message="require amount")
	private int amount;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
}
