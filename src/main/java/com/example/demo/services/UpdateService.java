package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.UserModel;
import com.example.demo.repoINterface.UserRepository;

@Service
public class UpdateService {

	@Autowired
	UserRepository userData;
	public String updateUserData(UserModel data)
	{
		UserModel model=new UserModel();
		model=userData.findOne(data.getId());
		model.setPhoneNumber(data.getPhoneNumber());
		model=userData.save(model);
		if(model!=null)
		return "success";
		return "error";
				
		
	}
}
