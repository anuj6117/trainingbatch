package com.crud.demo.dto;

public class UserRoleDTO {
	
	private Integer userId;
	private String roleType;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
