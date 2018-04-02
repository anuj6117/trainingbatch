package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;

	@Autowired
	private UserAuthenticationEntryPoint userAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http .csrf().disable() .authorizeRequests(). .antMatchers().hasAnyRole()
		 * .and().httpBasic().realmName("User Security")
		 * .authenticationEntryPoint(userAuthenticationEntryPoint);
		 */

		http.csrf().disable().authorizeRequests().antMatchers("/userdata/signup").permitAll();

		http.csrf().disable().authorizeRequests().antMatchers("/userdata/getallusers")
				.hasAnyRole("ADMIN", "MANAGER", "USER").and().formLogin();

		http.csrf().disable().authorizeRequests().antMatchers("/coin/getallcurrency").hasAnyRole("ADMIN").and()
				.formLogin();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		auth.inMemoryAuthentication();
	}

}
