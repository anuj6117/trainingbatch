package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AuthToken;
import com.example.demo.repository.AuthRepository;

@Service
public class AuthTokenService {

	@Autowired
	private AuthRepository authRepo;

	// Add otp for sign up verification
	public void addAuthToken(String userName, Integer otp) {
		AuthToken authToken = new AuthToken();
		authToken.setUserName(userName);
		authToken.setDateOfGeneration(new Date());
		authToken.setOtp(otp);
		authToken.setForSignUp(true);
		authRepo.save(authToken);

	}

	// Add otp for login
	public void addAuthTokenForLoginSession(String userName, Integer otp) {
		AuthToken authToken = new AuthToken();
		authToken.setUserName(userName);
		authToken.setDateOfGeneration(new Date());
		authToken.setOtp(otp);
		authToken.setForSignUp(false);
		authRepo.save(authToken);

	}
}
