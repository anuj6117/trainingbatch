package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import ch.qos.logback.classic.Logger;

public class OneShotActionFilter extends GenericFilterBean {

	//private static final Logger LOGGER = LoggerFactory.getLogger(OneShotActionFilter.class);
	  private static List<String> users = new ArrayList<>();
	  static {
		  users.add("123");
	    users.add("141");
	  }
	  private static final String PARAM_NAME = "uio";
	  private AuthenticationManager authenticationManager;
	  private UserDetailsService userDetailsService;
	  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
	  private enum AuthenticationStates {
	    REDIRECT, CONTINUE;
	  }
	 
	  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    this.authenticationManager = authenticationManager;
	  }
	 
	  public void setUserDetailsService(UserDetailsService userDetailsService) {
	    this.userDetailsService = userDetailsService;
	  }
	 
	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
	    ServletException {
	  //  LOGGER.debug("One shot filter invoked");
	    if (attemptAuthentication(request) == AuthenticationStates.REDIRECT) {
	      // Note that we should handle that dynamically but for learning purposes we'll consider that one-shot
	      // authentication works only for this URL
	      this.redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response,
	        "/userdata/getalluser");
	    } else {
	     // LOGGER.debug("User was not correctly authenticated, continue filter chain");
	      // continue execution of all other filters
	      // You can test the code without this fragment in the pages without ?uio parameter. You should see blank page because of
	      // security filter chain interruption.
	      filterChain.doFilter(request, response);
	    }
	  }
	 
	  private AuthenticationStates attemptAuthentication(ServletRequest request) {
	    AuthenticationStates state = AuthenticationStates.CONTINUE;
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String code = request.getParameter(PARAM_NAME);
	    if ((authentication == null || !authentication.isAuthenticated()) && code != null &&
	        users.contains(code)) {
	      //  LOGGER.debug("Checking user for code "+code);
	        UserDetails user = userDetailsService.loadUserByUsername(users.get(0));
	       // LOGGER.debug("Found user from code ("+users.get(code)+"). User found is "+user);
	        if (user != null) {
	          users.remove(code);
	          UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(),
	            user.getPassword());
	        authentication = this.authenticationManager.authenticate(authRequest);
	        if (authentication != null && authentication.isAuthenticated()) {
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            state = AuthenticationStates.REDIRECT;
	        }
	      }
	    }
	    return state;
	  }

	
	
}
