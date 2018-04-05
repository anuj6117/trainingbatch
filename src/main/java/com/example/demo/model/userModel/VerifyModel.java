package com.example.demo.model.userModel;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class VerifyModel {
	
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
@NotEmpty(message="otp require")
@Pattern(regexp="^(0|[1-9][0-9]*)$")
private int tokenOtp;
@NotEmpty(message="userName require")
private String userName;
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
private String date;
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
