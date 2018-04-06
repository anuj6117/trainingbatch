package com.example.adarsh.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	@Size(min=3,max=20,message="Username length Must be between 3 to 20") 
	@NotEmpty(message="User name must not be empty") 
	@NotBlank(message="Space Not Accepted")
	private String userName;
	private String phoneNumber;
	private String country;
	private String date;
	@NotEmpty(message ="Email must not be empty") 
	@Email 
	@NotBlank(message="Space Not Accepted")
	private String email;
	@NotEmpty
	@Size(message = "Password size must be between 4 to 20")
	private String password;

	@OneToMany(cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<OrderTabel> orderTabel;

	public List<OrderTabel> getOrderTabel() {
		return orderTabel;
	}

	public void setOrderTabel(List<OrderTabel> orderTabel) {
		this.orderTabel = orderTabel;
	}

	@OneToMany
	private List<Role> role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Wallet> wallet = new ArrayList<>();

	public List<Wallet> getWallet() {
		return wallet;
	}

	public void setWallet(List<Wallet> wallet) {
		this.wallet = wallet;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
