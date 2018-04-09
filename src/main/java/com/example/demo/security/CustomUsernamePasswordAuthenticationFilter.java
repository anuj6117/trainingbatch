//package com.example.demo.security;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.example.demo.model.userModel.UserModel;
//import com.example.demo.repoINterface.UserRepository;
//
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//	@Autowired
//	UserRepository userData;
//	
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		response.addHeader("token","5464f4sd4fdsf46");
//		
//		super.successfulAuthentication(request, response, chain, authResult);
//	}
//	
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		System.out.println("hfdkfsd");
//		 String code = request.getParameter("id");
//		 UserModel user = userData.findByUserName(code);
//		 UsernamePasswordAuthenticationToken token
//         = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
//		  return this.getAuthenticationManager().authenticate(token);
//	}
//	
//	
//}
