package com.example.traning.dto;

public class UserRoleDto {
	private Long userId;
	private String roleType;
	public Long getUserid() {
		return userId;
	}
	public void setUserid(Long userId) {
		this.userId = userId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}
