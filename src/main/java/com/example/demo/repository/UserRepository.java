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
	public UserModel findByEmailAndPassword(String email,String password);
	public List<UserModel> findAll(Pageable	 pageable);
	public List<UserModel> findByUserName(String userName,Pageable pageable);
	//@Query(value = "select u.userName from UserModel u where u.userName like %:u.userName%")
	public List<UserModel> findByUserNameContaining(String userName);
	
}
