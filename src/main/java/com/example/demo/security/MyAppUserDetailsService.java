package com.example.demo.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.model.userModel.RoleModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.repoINterface.UserRepository;
@Service
public class MyAppUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userData;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		 UserModel user = userData.findByUserName(userName);
	        return new org.springframework.security.core.userdetails.User(
	          user.getUserName(), user.getPassword(),getAuthorities(user.getRole()));
	    }
	private Collection<? extends GrantedAuthority> getAuthorities(
	      Collection<RoleModel> roles) {
	        return getGrantedAuthorities(getPrivileges(roles));
	    }
	
private List<String> getPrivileges(Collection<RoleModel> roles) {
	  
    List<String> privileges = new ArrayList<>();
    for (RoleModel role : roles) {
    	  privileges.add(role.getUserRole());
       // System.out.println(role.getUserRole());
    }
    return privileges;
}
private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
    	//System.out.println(privilege);
        authorities.add(new SimpleGrantedAuthority("ROLE_"+privilege));
    }
    return authorities;
}
}






