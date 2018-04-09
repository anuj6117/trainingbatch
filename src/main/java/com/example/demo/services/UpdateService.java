package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.UserModel;
import com.example.demo.repoINterface.UserRepository;

@Service
public class UpdateService {

	@Autowired
	UserRepository userData;

	public UserModel updateUserData(UserModel data) {
		UserModel model = new UserModel();
		model = userData.findOne(data.getUserId());
		if (model == null)
			throw new NullPointerException("id not found");
		UserModel checkData = userData.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber());
		if (checkData != null) {
			throw new NullPointerException("user already inserted Email and PhoneNumber change ");
		}
		
		model.setUserName(data.getUserName());
		model.setEmail(data.getEmail());
		model.setPhoneNumber(data.getPhoneNumber());
		model = userData.save(model);
		if (model != null)
			return model;
		return null;

	}
}
