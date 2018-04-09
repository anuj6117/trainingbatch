package com.crud.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class OTPSMS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer otpId;
	private String email = "mohitjainrk@gmail.com";
	private Date date = new Date();//add timing also
	private String tokenOtp;
	
	/*@OneToOne()
	@JoinColumn(name="fk_user",nullable=false)
	@JsonIgnore
	private User user;*/
	
	@Transient
	private Integer userId;

	public Integer getOtpId() {
		return otpId;
	}

	public void setOtpId(Integer otpId) {
		this.otpId = otpId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTokenOtp() {
		return tokenOtp;
	}

	public void setTokenOtp(String tokenOtp) {
		this.tokenOtp = tokenOtp;
	}

	
	/*public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
