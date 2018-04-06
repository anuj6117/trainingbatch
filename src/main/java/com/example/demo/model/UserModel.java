package com.example.demo.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer userid;
	private String userName;
	private String country;
	private Date createdOn;

	private String email;
	private Long phoneNumber;
	private String password;
	private Boolean status = false;
	@Transient
	private String walletType;
	@Transient
	private Integer tokenOTP;


	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	private Set<RoleModel> roleType = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userModel")
	private Set<WalletModel> userWallet = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userModel")
	private Set<OrderModel> userOrder = new HashSet<>();
		
	public Integer getTokenOTP() {
		return tokenOTP;
	}

	public void setTokenOTP(Integer tokenOTP) {
		this.tokenOTP = tokenOTP;
	}
	
	public Set<OrderModel> getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(Set<OrderModel> userOrder) {
		this.userOrder = userOrder;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<RoleModel> getRoleType() {
		return roleType;
	}

	public void setRoleType(Set<RoleModel> roleType) {
		this.roleType = roleType;
	}

	public Set<WalletModel> getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(Set<WalletModel> userWallet) {
		this.userWallet = userWallet;
	}
	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

}
