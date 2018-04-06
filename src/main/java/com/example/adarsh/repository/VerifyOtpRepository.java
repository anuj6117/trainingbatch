package com.example.adarsh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adarsh.domain.VerifyOtp;

public interface VerifyOtpRepository extends JpaRepository<VerifyOtp, Serializable>{

	VerifyOtp findOneByUserNameAndTokenOtp(String userName, int tokenOtp);

}
