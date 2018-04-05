package com.example.demo.repoINterface;

import java.util.List;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.userModel.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	
	
	UserModel findByUserName(String username);
	
	//public List<UserModel> findByUserNameContaining(String name);
	public UserModel findByUserIdAndUserName(Long id,String userName);
	public UserModel findByEmailAndPassword(String email,String password);
	public UserModel findByEmailOrPhoneNumber(String userName,String phoneNumber);
	public UserModel findOneByUserNameContaining(String name);
	public UserModel findOneByEmail(String name);
	//public UserModel findOneByUserNameAndEmailAndMobileNo(String userName,String email,String password);
	public UserModel findOneByEmailAndPassword(String email,String password);
	public List<UserModel> findByUserNameContaining(String name,Pageable pageable);
}
