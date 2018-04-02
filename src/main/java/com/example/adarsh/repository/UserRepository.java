package com.example.adarsh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adarsh.domain.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	public User findByEmail(String email);

}
