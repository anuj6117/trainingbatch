//package com.example.demo.security;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	
//	@Autowired
//	private MyAppUserDetailsService myAppUserDetailsService;	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception
//	{
//		//http.addFilterBefore(new CustomUsernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
//		http
//		.csrf().disable()
////.authorizeRequests().anyRequest().permitAll();
//.authorizeRequests().antMatchers("/getallusers").hasAnyRole("user","admin")
//.antMatchers("/getalluse","/signup","/addrole","/assignrole","userlogin").permitAll()
//.anyRequest().authenticated().and().formLogin();
//
////.failureHandler(failureHandler()).
////and().exceptionHandling().accessDeniedPage("/403");
////		
//	}
//
//		 
// @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		 auth.userDetailsService(myAppUserDetailsService);//.passwordEncoder(passwordEncoder);
//		
//	    }
//	 
////	 private AuthenticationFailureHandler failureHandler() {
////		    return new AuthenticationFailureHandler() {
////			@Override
////			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse httpServletResponse,
////					AuthenticationException exception) throws IOException, ServletException {
////				httpServletResponse.getWriter().append("Authentication failure");
////		        httpServletResponse.setStatus(401);
////			}
////		    };
//}
//
//	 
