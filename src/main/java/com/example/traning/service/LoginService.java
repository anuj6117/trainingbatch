/*package com.example.traning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Register;
import com.example.traning.repository.RegisterRepository;

@Service
public class LoginService {
	@Autowired
	RegisterRepository registerrepository;

	public String Login(String username, String password) {
		System.out.println("1"+username+""+password);
			Register model=registerrepository.findByUserName(username);
			System.out.println("---->"+model);
			String pass=model.getPassword();
			System.out.println(pass+"what is comming in userpassword and server pass"+password);
			System.out.println("3");
			if(pass.equals(password)) {
				System.out.println("4");
				return "Succesfully";
			}
			else {
				System.out.println("5");
			return "Dhappa ";
			}
		
		
	}
	

}
*/