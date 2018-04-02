package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AuthToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private int tokenId;
	private long otp;
	private String userName;
	private Date dateOfGeneration;
	private Boolean forSignUp=true;
	
	public Boolean getForSignUp() {
		return forSignUp;
	}
	public void setForSignUp(Boolean forSignUp) {
		this.forSignUp = forSignUp;
	}
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public long getOtp() {
		return otp;
	}
	public void setOtp(long otp) {
		this.otp = otp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDateOfGeneration() {
		return dateOfGeneration;
	}
	public void setDateOfGeneration(Date dateOfGeneration) {
		this.dateOfGeneration = dateOfGeneration;
	}
	

}
