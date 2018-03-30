package com.crud.demo.passwordencoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPasswordEncoder {
	
	public static String customPasswordEncoder(String password)
	{
		BCryptPasswordEncoder bCryptPasswordEncoder=new  BCryptPasswordEncoder();
		String encodedPassword=bCryptPasswordEncoder.encode(password);
		return encodedPassword;
	}

}
