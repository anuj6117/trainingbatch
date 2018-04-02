package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AuthToken;

public interface AuthRepository extends JpaRepository<AuthToken,Integer> {
	AuthToken findByOtp(long otp);
}
