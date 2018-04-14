package com.example.traning.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traning.domain.Register;

public interface RegisterRepository extends JpaRepository<Register, Long> {

	public Register findByUserId(Long userId);

	public Register findByUserName(String userName);

	public Register findByEmail(String email);

	public List<Register> findByUserName(String userName,Pageable request);



	




}
