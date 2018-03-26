package com.example.demo.model.userModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
//@Table(name="user")
public class UserModel  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String userName;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String mobileNo;
	private boolean status;
	private String country;
	private String password;
	private String createdOn;
	@OneToMany(mappedBy = "userdata", cascade = CascadeType.ALL)
	private List<WalletModel> WalletModel=new ArrayList<>();
	public List<WalletModel> getWalletModel() {
		return WalletModel;
	}
	public void setWalletModel(List<WalletModel> walletModel) {
		WalletModel = walletModel;
	}
	@ManyToMany(
            cascade = {
                    CascadeType.ALL
                })
	@JoinTable(
			name="user_role",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")}
			)
	@JsonIgnore
	private List<RoleModel> role=new ArrayList<>();

	public List<RoleModel> getRole() {
		return role;
	}
	public void setRole(List<RoleModel> role) {
		this.role = role;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
