package com.example.traning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traning.domain.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

	public Otp findOneBytokenOTP(int tokenOTP);





}
