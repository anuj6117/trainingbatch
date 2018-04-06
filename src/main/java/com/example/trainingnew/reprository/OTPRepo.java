package com.example.trainingnew.reprository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.OTPModel;

public interface OTPRepo extends JpaRepository<OTPModel, Long>{

	OTPModel findOneByEmailAndOtp(String email, long otp);

	void deleteByOtp(long otp);

//	void deleteByOtp(long otp);


}
