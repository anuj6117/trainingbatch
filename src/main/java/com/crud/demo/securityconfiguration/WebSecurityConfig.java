package com.crud.demo.securityconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	 
	 protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("WebSecurityConfigurerAdapter::::::::Started or loaded");
		httpSecurity.authorizeRequests().antMatchers("/signup","/getallusers").permitAll()
		.anyRequest().authenticated()
	                .and()	               
	                .formLogin()
	                .and()
	                .csrf().disable();
	 }
	 // working In-memory implementation
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("mohit").password("admin").roles("manager");
		
	} */
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(); 
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

}
