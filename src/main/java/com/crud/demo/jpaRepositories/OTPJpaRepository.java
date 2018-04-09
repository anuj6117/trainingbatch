package com.crud.demo.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.demo.model.OTPSMS;

public interface OTPJpaRepository extends JpaRepository<OTPSMS, Integer>{
	
	OTPSMS findByTokenOtp(String tokenOtp);

}
