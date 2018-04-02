package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/getAllDetail", method = RequestMethod.GET)
	public List<RoleModel> getAllDetail() {
		return roleService.getAllDetails();
	}

	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	public void add() {
		roleService.addRole();
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(Integer id) {
		roleService.deleteUser(id);
	}

}
