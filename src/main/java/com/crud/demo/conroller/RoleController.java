package com.crud.demo.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.model.Role;
import com.crud.demo.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/createrole",method=RequestMethod.POST)
	public String createRole(@RequestBody Role role) {
		System.out.println("controller hit");
		return roleService.createRole(role);	
		
	}
	@RequestMapping(value="/assignRole")
	public void assignRole(@RequestParam Integer userId,@RequestParam String roleType)
	{System.out.println("controller hit");
		roleService.assignRole(userId,roleType);
	}

}
