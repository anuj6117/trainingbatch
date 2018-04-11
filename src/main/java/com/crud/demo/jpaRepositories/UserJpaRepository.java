package com.crud.demo.jpaRepositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.crud.demo.model.User;

public interface UserJpaRepository extends JpaRepository<User,Integer> ,JpaSpecificationExecutor<User>{
	//use for security in custom security Service
	User findByUserName(String userName);
	//for pagination
	User findByEmail(String email);
	User findByPhoneNumber(String phoneNumber);

}
