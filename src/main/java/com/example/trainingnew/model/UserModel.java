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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdOn" }, allowGetters = true)
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	
	@Size(min=3,max=20,message="Username length Must be between 3 to 20")
	@NotEmpty(message="User name must not be empty")
	@NotBlank(message="Space Not Accepted")
	private String userName;
	
	@NotEmpty(message ="Email must not be empty")
	@Email
	@NotBlank(message="Space Not Accepted")
	@Column(unique=true)
	private String email;
	private Boolean status;
	@NotBlank(message="Space Not Accepted")
	private String country;
	@NotEmpty(message="Password can't be null")
	@NotBlank(message="Space Not Accepted")
	private String password;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
//	@Pattern(regexp="[0-9]*")
//	@Length(min=10,max=11,message="Number must be 10 digits")
	@Column(unique=true)
	private String phoneNumber;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "users_id") }, inverseJoinColumns = {
		@JoinColumn(name = "roles_id") })
	private List<Rolemodel> roles = new ArrayList<>();

	@OneToMany(mappedBy ="userModel",cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	private List<Walletmodel> wallets = new ArrayList<>();
	
	
	@OneToMany(mappedBy ="userModelInOrderModel",cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	private List<OrderModel> orders = new ArrayList<>();
	
	public UserModel() {

	}

	public UserModel(UserModel model) {
		this.status = model.getStatus();
		this.email = model.getEmail();
		this.roles = model.getRoles();
		this.userName = model.getUserName();
		this.phoneNumber = model.getPhoneNumber();
		this.country = model.getCountry();
		this.userId = model.getUserId();
		this.createdOn = model.getCreatedOn();

	}
	
	

	public List<OrderModel> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderModel> orders) {
		this.orders = orders;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer id) {
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
