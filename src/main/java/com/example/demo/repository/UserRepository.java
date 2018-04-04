package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.UserModel;

@Transactional
public interface UserRepository extends CrudRepository <UserModel,Integer>{

	public UserModel findByEmail(String email);
	public UserModel findByUserName(String userName);
	public UserModel findByPhoneNumber(Long phoneNumber);
	public UserModel findByEmailAndPassword(String email,String password);
	public List<UserModel> findAll(Pageable	 pageable);
	public List<UserModel> findByUserName(String userName,Pageable pageable);
	public List<UserModel> findByUserNameContaining(String userName,Pageable pageable);
	
}
