package com.example.trainingnew.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainingnew.model.Usermodel;
import com.example.trainingnew.reprository.UserRepo;



@Service
public class LoginService {

	@Autowired
	UserRepo userrepo;
	public String doLogin(String email, String password) {
		
		System.out.println(password);
		Usermodel model=userrepo.findOneByEmail(email);
		System.out.println("lets see"+model);
		
		String pass=model.getPassword();
			System.out.println(pass+"what is coming in userpassword and server pass"+password);
		
		if(pass.equals(password)) {
			return "Succesfully Login";
		}
		else {
		return "Wrong Username and Password ";
		}
	}

}
