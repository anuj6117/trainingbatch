package com.example.demo.security2;

import java.io.IOException;

import com.example.demo.model.userModel.UserModel;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.security2.SecurityConstants.HEADER_STRING;
import static com.example.demo.security2.SecurityConstants.SECRET;
import static com.example.demo.security2.SecurityConstants.TOKEN_PREFIX;


import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	MyAppUserDetailsService appUserDetailsService;
	  private AuthenticationManager authenticationManager;

	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	    @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	
	    	objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
	    	  System.out.println(req.getParameter("username"));
	    	  System.out.println(req.getParameter("password"));
	 
	    	try {
	               return authenticationManager.authenticate(
	                       new UsernamePasswordAuthenticationToken(
	                               req.getParameter("username"),
	                               req.getParameter("password"),
	                               new ArrayList<>())
	               );
	           } catch (Exception e) {
	               throw new RuntimeException(e);
	           }
	       }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {

	        String token = Jwts.builder()
	                .setSubject(((User) auth.getPrincipal()).getUsername())
	                .setExpiration(new Date())
	                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
	                .compact();
	        System.out.println(token);
	        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	    }
	}
