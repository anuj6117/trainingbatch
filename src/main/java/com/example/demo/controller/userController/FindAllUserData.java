package com.example.demo.controller.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.roleDTO.RoleDTO;
import com.example.demo.model.userModel.RoleModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.services.UserServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class FindAllUserData {

	@Autowired
	UserServices userData;

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public ResponseEntity<Object> getAll() {
		List<UserModel> result = null;
		try {
			result = userData.findAll();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/getusersbyid", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAll(@RequestParam("id") Long id) {
		UserModel result = null;
		try {
			result = userData.findById(id);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/showAllByname", method = RequestMethod.GET)
	public ResponseEntity<Object> findbyusername(@RequestParam("name") String name, @RequestParam("page") int page) {
		List<UserModel> result = null;
		try {
			result = userData.findbyusername(name, page);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/showAllByRole", method = RequestMethod.GET)
	@ResponseBody
	public List<RoleModel> getall() {
		return userData.findAllUserModel();
	}

	@RequestMapping(value = "/showRoleByUser", method = RequestMethod.GET)

	public List<String> getall(@RequestParam("name") String name) {
		return userData.findRoleByUserName(name);
	}

	@RequestMapping(value = "/showAllByUserRole", method = RequestMethod.GET)

	public List<RoleModel> getallUser(@RequestParam("userRole") String userRole) {
		return userData.findAllByUserModel(userRole);
	}

	@RequestMapping(value = "/showRole", method = RequestMethod.GET)
	public List<RoleDTO> getAllRole() {
		return userData.findAllRole();

	}

}
