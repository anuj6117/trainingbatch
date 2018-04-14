package com.example.traning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.traning.repository.RegisterRepository;
import com.example.traning.service.RegisterService;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses=RegisterRepository.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	RegisterService registerservice;
	
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(registerservice).passwordEncoder(getPasswordEncoder());
		
		
	}
	
	@Override
	protected void configure(HttpSecurity httpsecurity) throws Exception {
		httpsecurity.authorizeRequests()
		.antMatchers("/getalluser").hasAnyRole("user","manager").antMatchers("/sample1").hasAnyRole("manager")
		.antMatchers("/sample2").hasAnyRole("user")
		.antMatchers("/sample3").hasAnyRole("admin")
		.anyRequest().permitAll();
	    httpsecurity.csrf().disable();
	}
	
	

	
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and().formLogin().permitAll().and().logout().permitAll();
	}
	
	
	
	
	
	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		String password = "demo";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		System.out.println("hashed pass : "+hashedPassword);
		
		
		return new BCryptPasswordEncoder();
	}

	
	
	
	
	
	
	
	
	//========================Basic Login============================================
	
	
	
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		
		
		auth.inMemoryAuthentication().withUser("sahil").password("{noop}demo").roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity httpsecurity)throws  Exception {
		httpsecurity.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
		httpsecurity.csrf().disable();
		
	}
	
	
	//========================Basic Login============================================
	

	
	
	//=================================My implements===================================
		private CustomUserDetailService userDetailsService;
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
			auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
		}
		
		
		
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anuserDetailsServicetMatchers("/tranapi/**").authenticated().anyRequest().permitAll().and().formLogin().permitAll();
		}
		
		
		
		
		
		
		private PasswordEncoder getPasswordEncoder() {
			return new PasswordEncoder() {
				
				@Override
				public boolean matches(CharSequence rawPassword, String encodedPassword) {
					
					return true;
				}
				
				@Override
				public String encode(CharSequence rawPassword) {
					
					return rawPassword.toString();
				}
			};
			
		}
		
		//=================================My implements===================================
		*/
	
}