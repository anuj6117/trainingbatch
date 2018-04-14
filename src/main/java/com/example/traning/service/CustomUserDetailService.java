/*package com.example.traning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.traning.domain.CustomUserDetail;
import com.example.traning.domain.Register;
import com.example.traning.repository.RegisterRepository;
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private RegisterRepository registerrepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
	Register optionaluser=registerrepository.findByUsername(username);
	optionaluser.orElseThrow(()-> new UsernameNotFoundException("user Not exist"));
	
	
	return optionaluser.map(CustomUserDetail::new).get();
	}
		
}*/