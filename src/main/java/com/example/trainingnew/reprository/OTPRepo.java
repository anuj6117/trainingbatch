package com.example.trainingnew.reprository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.OTPModel;

public interface OTPRepo extends JpaRepository<OTPModel, Integer>{

	OTPModel findOneByEmailAndOtp(String email, Integer otp);

	void deleteByOtp(Integer otp);

//	void deleteByOtp(long otp);


}
