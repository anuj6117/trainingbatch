package com.example.trainingnew.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdOn" }, allowGetters = true)
public class Usermodel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@NotNull(message="User name must not be empty")
	private String userName;
	
	@NotNull(message ="Email must not be empty")
	private String email;
	private boolean status;
	private String country;
	@NotNull
	private String password;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@NotNull
	private long phoneNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, CascadeType.MERGE })
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "users_id") }, inverseJoinColumns = {
			@JoinColumn(name = "roles_id") })
	
	@JsonIgnore
	private List<Rolemodel> roles = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usermodel")
	@JsonIgnore
	private List<Walletmodel> wallets = new ArrayList<>();

	public Usermodel() {

	}
	
	

	public Usermodel(Usermodel model) {
		this.status = model.getStatus();
		this.email = model.getEmail();
		this.roles = model.getRoles();
		this.userName = model.getUserName();
		this.phoneNumber = model.getPhoneNumber();
		this.country = model.getCountry();
		this.userId = model.getUserId();
		this.createdOn = model.getCreatedOn();

	}

	public List<Walletmodel> getWallets() {
		return wallets;
	}

	public void setWallets(List<Walletmodel> wallets) {
		this.wallets = wallets;
	}

	public List<Rolemodel> getRoles() {
		return roles;
	}

	public void setRoles(List<Rolemodel> roles) {
		this.roles = roles;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(@NotEmpty(message = "Name Can't be empty") String username) {

		this.userName = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(@Email(message = "Email must be well formed") String email) {
		this.email = email;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
