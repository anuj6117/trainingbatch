package com.example.demo.generate.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordGenerator {
	public static String passwordGenerator(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);

	}
}
