package com.example.trainingnew.reprository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.Usermodel;

public interface UserRepo extends JpaRepository<Usermodel ,Long>{

	Usermodel findOneByUserId(long id);

	Usermodel findOneByEmail(String email);


	Usermodel findByUserName(String username);

	Usermodel findAllByEmail(String email);

	Usermodel findOneByUserName(String username);

	Optional<Usermodel> findByEmail(String email);



}
