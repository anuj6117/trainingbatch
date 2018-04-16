package com.example.demo.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.userDTO.UserDTO;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.services.UpdateService;
import com.example.demo.utils.ResponseHandler;

@RestController
public class UpdateUserData {

	@Autowired
	UpdateService update;

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public ResponseEntity<Object> updateUserData(@Validated @RequestBody UserDTO data) {
		UserModel model = new UserModel();
		model.setUserId(data.getUserId());
		model.setUserName(data.getUserName());
		model.setPhoneNumber(data.getPhoneNumber());
		
		UserModel result = null;
		try {
			result = update.updateUserData(model);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}
}
