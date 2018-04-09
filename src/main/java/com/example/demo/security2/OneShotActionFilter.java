package com.example.demo.security2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.model.userModel.UserModel;
import com.example.demo.repoINterface.UserRepository;

public class OneShotActionFilter extends GenericFilterBean {

	@Autowired
	UserRepository userData;
	  //private static final Logger LOGGER = LoggerFactory.getLogger(OneShotActionFilter.class);
	  private static Map<String, String> users = new HashMap<String, String>();
	  static {
	    users.put("0000000000001", "ravi");
	    users.put("0000000000002", "admin");
	    users.put("0000000000003", "mod");
	  }
	  private static final String PARAM_NAME = "id";
	  
	  private AuthenticationManager authenticationManager;
	 
	  public UserDetailsService userDetailsService;
	  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
	  private enum AuthenticationStates {
	    REDIRECT, CONTINUE;
	  }
	  public void setUserDetailsService(UserDetailsService userDetailsService) {
		    this.userDetailsService = userDetailsService;
		  }
	  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    this.authenticationManager = authenticationManager;
	  }
	 
	
	 
	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
	    ServletException {
	    //LOGGER.debug("One shot filter invoked");
		  String code = request.getParameter("id");
		 System.out.println(code);
	    if (users.containsKey(code)) {
	    	System.out.println(code+"fsdfdsfds");	
	    	HttpServletResponse response2=(HttpServletResponse) response;
	    	response2.setHeader("dsf", "dfsdfsd");
	    	response2.addHeader("dsd", "dsd");
	    	this.redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response2,
	    		        "/getallusers");
	      // Note that we should handle that dynamically but for learning purposes we'll consider that one-shot
	      // authentication works only for this URL
	     // this.redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response,
	      //  "/getallusers");
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
	    System.out.println(code);
	    if ((authentication == null || !authentication.isAuthenticated()) && code != null &&
	        users.containsKey(code)) {
	        //LOGGER.debug("Checking user for code "+code);
	    	
String name=users.get(code);
System.out.println("dafdfd"+name);
	       // UserDetails user = userDetailsService.loadUserByUsername(name);
UserModel user = userData.findByUserName(name);
//System.out.println(user.getCountry());
	       // LOGGER.debug("Found user from code ("+users.get(code)+"). User found is "+user);
//	        if (user != null) {
//	          users.remove(code);
//	          System.out.println("fdsfdssd");
//	          UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(),
//	            user.getPassword());
//	        authentication = this.authenticationManager.authenticate(authRequest);
//	        if (authentication != null && authentication.isAuthenticated()) {
//	        	System.out.println("fdsfdssd");
//	            SecurityContextHolder.getContext().setAuthentication(authentication);
//	            state = AuthenticationStates.REDIRECT;
//	        }
//	      }
	    }
 return state;
	  
	}
	  
}