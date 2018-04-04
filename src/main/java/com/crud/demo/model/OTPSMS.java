package com.crud.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OTPSMS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer otpId;
	private String email = "mohitjainrk@gmail.com";
	private Date date = new Date();//add timing also
	private String tokenOTP;

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

	public String getTokenOTP() {
		return tokenOTP;
	}

	public void setTokenOTP(String tokenOTP) {
		this.tokenOTP = tokenOTP;
	}

}
