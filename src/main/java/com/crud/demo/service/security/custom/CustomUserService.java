
package com.crud.demo.service.security.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crud.demo.jpaRepositories.RoleJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.model.Role;
import com.crud.demo.model.User;
import com.crud.demo.model.security.custom.CustomUserDetails;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private RoleJpaRepository roleJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User user = userJpaRepository.findByUserName(userName);
		
		/* GrantedAuthority must contain all roleType as String */
		List<GrantedAuthority> listGrantedAuthority = new ArrayList<>();

		if (user != null) {
			for (Role r : user
					.getRoles()) { /*
									 * here we appending "ROLE_" to database role strings because Spring Security
									 * internally requires it if we have attached hasAnyRole filter with antmatchers
									 */
				listGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_" + r.getRoleType()));
				System.out.println("::::::::Testing"+ r.getRoleType());
			}
			/*
			 * (User is also predefined in security package so its recommended not to use
			 * User name as you model which currently is mistake)
			 */
			/* Below is the constructor of that predefined User class */
			
		} 
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), listGrantedAuthority);
		return userDetails;
	}

}
