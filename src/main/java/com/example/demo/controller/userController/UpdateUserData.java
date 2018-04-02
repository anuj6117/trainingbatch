package com.example.demo.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.userDTO.UserDTO;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.services.UpdateService;

@RestController
public class UpdateUserData {
	
	@Autowired
	UpdateService update;

@RequestMapping(value="/updateData",method=RequestMethod.POST)
public String updateUserData(@RequestBody UserDTO data)
{
	UserModel model=new UserModel();
	model.setId(data.getId());
	model.setUserName(data.getUserName());
	model.setPhoneNumber(data.getUserName());
	return update.updateUserData(model);
	
}
}
