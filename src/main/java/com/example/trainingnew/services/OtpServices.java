package com.example.trainingnew.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainingnew.reprository.OTPRepo;

@Service
public class OtpServices {
	
	@Autowired
	OTPRepo otpRepo;
	
	// private static final Integer EXPIRE_MINS = 5;
	 
//	 private LoadingCache<String, Integer> otpCache;
	
	

	public int generateOTP(String username) {
		// TODO Auto-generated method stub
		
		Random random=new Random();
		int otp=1000+random.nextInt(9000);
		
		return otp;
	}

}
