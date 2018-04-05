package com.example.trainingnew.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.services.UserServices;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableJpaRepositories(basePackageClasses= UserRepo.class)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
	

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Autowired
	private UserServices userservice;
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userservice);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		 
				
		auth.userDetailsService(userservice)
		.passwordEncoder(passwordEncoder()) ;
			
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.csrf().disable()
		.authorizeRequests()
//		.antMatchers("/getallusers").hasAnyRole("admin")
//		.antMatchers("/hello").hasAnyRole("user")
//		.antMatchers("/manager").hasAnyRole("manager")
//		.antMatchers("/managerAndUser").hasAnyRole("manager","user")
//		.anyRequest().authenticated()
		
		.anyRequest().permitAll()
		.and().formLogin();
		
	}
	
	
	
}
