/*package com.example.Configuration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;



@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
 
    
    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
    	response.addHeader("WWW-Authenticate", "Basic realm="+" + getRealmName() + "+"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authException.getMessage());
	}

	
 
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Baeldung");
        super.afterPropertiesSet();
    }
    
    
    public MyBasicAuthenticationEntryPoint() {
		// TODO Auto-generated constructor stub
	}
    
}*/