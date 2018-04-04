package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponse;

@RestController
//@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getAllDetail", method = RequestMethod.GET)
	public List<RoleModel> getAllDetail() {
		return roleService.getAllDetails();
	}

	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody RoleModel roleModel) {
		Object obj;
		try {
			obj = roleService.addRole1(roleModel);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", obj);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(Integer id) {
		roleService.deleteUser(id);
	}

}
