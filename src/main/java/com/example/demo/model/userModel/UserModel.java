package com.example.demo.model.userModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
//@Table(name="user")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class UserModel implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@NotEmpty(message="user name not empty")
	private String userName;
	@Email(message="email not valid")
	@NotEmpty(message="email not empty")
	private String email;
	@NotEmpty(message="phone number not valid")
	@Size(min=10,max=13,message="phone number not valid")
	@Pattern(regexp="^(0|[1-9][0-9]*)$")
	private String phoneNumber;
	public List<OrderModel> getOrderModel() {
		return orderModel;
	}
	public void setOrderModel(List<OrderModel> orderModel) {
		this.orderModel = orderModel;
	}

	private boolean status;
	@Column
	@NotEmpty(message="country not valid")
	private String country;
	@NotEmpty(message="password not valid")
	private String password;
	private String createdOn;
	@OneToMany(mappedBy = "userdata", cascade = CascadeType.ALL)
	private List<WalletModel> WalletModel=new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderModel> orderModel=new ArrayList<>();
	public List<WalletModel> getWalletModel() {
		return WalletModel;
	}
	public void setWalletModel(List<WalletModel> walletModel) {
		WalletModel = walletModel;
	}
//	@ManyToMany(
//            cascade = {
//                    CascadeType.ALL
//                })
	
	@ManyToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinTable(
			name="user_role",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			)
	
	private List<RoleModel> role=new ArrayList<>();
	

	public List<RoleModel> getRole() {
		return role;
	}
	//@JsonIgnore
	public void setRole(List<RoleModel> role) {
		this.role = role;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isStatus() {
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
	
}
