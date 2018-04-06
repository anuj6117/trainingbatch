package com.example.adarsh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VerifyOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int tokenOtp;
	private String userName;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTokenOtp() {
		return tokenOtp;
	}

	public void setTokenOtp(int tokenOtp) {
		this.tokenOtp = tokenOtp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
