package com.example.demo.controller.userController;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.userDTO.RoleDTO;
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
	public ResponseEntity<Object> show(@Validated @RequestBody UserModel data) {
		UserModel result = null;
		try {
			result = userservice.saveUserData(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		System.out.println(result);
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@PostMapping("/verifyuser")
	public ResponseEntity<Object> verifyUser(@RequestBody VerifyModel data) {
		UserModel result = null;
		try {
			result = userservice.verifyUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<Object> addRole(@Valid @RequestBody RoleModel data) {
		String result = null;
		try {
			result = userservice.addRole(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/removerole", method = RequestMethod.POST)
	public ResponseEntity<Object> removeRole(@RequestBody RoleModel data) {
		String result = null;
		try {
			result = userservice.removeRoleToUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public ResponseEntity<Object> AddRoleToUser(@RequestBody RoleDTO data) {
		String result = null;
		try {
			result = userservice.addRoleToUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/activeuser", method = RequestMethod.POST)
	public ResponseEntity<Object> activeUser(@RequestBody UserModel data) {
		UserModel result = null;
		try {
			result = userservice.activeDeactiveUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);

		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

}