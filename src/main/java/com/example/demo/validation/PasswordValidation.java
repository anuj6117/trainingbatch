package com.example.demo.validation;

public class PasswordValidation {

	public static Boolean passwordValidation(String password) {
		  String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	      if(password.matches(pattern)) {
	    	  return true;
	      }else
	    	  return false;
	      
	}
	public static Boolean phoneNumberValidation(String phoneNumber) {
		if (phoneNumber.matches("\\d{10}")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static Boolean positiveNumberValidation(Integer number) {
		if(number>=0) {
			return true;
		}
		else {
			return false;
		}
	}
}
