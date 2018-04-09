package com.example.trainingnew.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name="auth_token")
public class OTPModel {
	
	@Id
	private Integer otp;
	private String email;
	
	@Column(nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	

}
