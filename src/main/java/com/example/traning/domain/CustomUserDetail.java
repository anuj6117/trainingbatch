/*package com.example.traning.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail extends Register implements UserDetails{
	public CustomUserDetail (final Register register) {
		super(register);
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(){
		
		return getRoles().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.getRole())).collect(Collectors.toList());
		
	}
	public  String getPassword() {
		return super.getPassword();
	}

	
	public  String getUsername() {
		return super.getUserName();
		}

	
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	};

	

}
*/