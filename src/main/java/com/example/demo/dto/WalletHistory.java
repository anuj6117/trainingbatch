package com.example.demo.dto;

public class WalletHistory {
private Integer userId;
private String description;
private Long netAmount;
private String status;

public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Long getNetAmount() {
	return netAmount;
}
public void setNetAmount(Long netAmount) {
	this.netAmount = netAmount;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
}
