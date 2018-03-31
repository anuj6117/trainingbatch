//package com.example.trainingnew.services;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.trainingnew.model.CustomUserDetails;
//import com.example.trainingnew.model.Usermodel;
//import com.example.trainingnew.reprository.UserRepo;
//
//
//@Service
//public class CustomUserDetailServices implements UserDetailsService{
//
//	@Autowired
//	private UserRepo userrepo;
//	
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//	
//		Optional<Usermodel> optionaluser=userrepo.findByEmail(email);
//		optionaluser.orElseThrow(()-> new UsernameNotFoundException("user Not exist"));
//		
//		return optionaluser.map(CustomUserDetails::new).get();
//		
//	}
//
//}
