package com.example.demo.controller.userController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.RoleModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.VerifyModel;
import com.example.demo.services.UserServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class UserController {

	@Autowired
	UserServices userservice;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> show(@RequestBody UserModel data) {
		Map<String, Object> result = null;
		try {
			result = userservice.saveUserData(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		System.out.println(result);
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("result").toString(), result);
	}

	@PostMapping("/verifyuser")
	public String verifyUser(@RequestBody VerifyModel data) {
		return userservice.verifyUser(data);
	}

	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	public String addRole(@RequestBody RoleModel data) {

		return userservice.addRole(data);
	}

	@RequestMapping(value = "/removerole", method = RequestMethod.POST)
	public String removeRole(@RequestBody RoleModel data) {

		return userservice.removeRoleToUser(data);
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String AddRoleToUser(@RequestBody RoleModel data) {
		return userservice.addRoleToUser(data);
	}

	@RequestMapping(value="/activeuser",method=RequestMethod.POST)
	public String activeUser(@RequestBody UserModel data)
	{
		return userservice.activeDeactiveUser(data);
		
	}
	
	
	
	
}