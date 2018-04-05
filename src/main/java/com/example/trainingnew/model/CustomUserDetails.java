//package com.example.trainingnew.model;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class CustomUserDetails extends UserModel implements UserDetails {
//
//	public CustomUserDetails(final UserModel model) {
//		super(model);
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//
//		return getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return super.getPassword();
//	}
//
//	@Override
//	public String getEmail() {
//		// TODO Auto-generated method stub
//		return super.getEmail();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
