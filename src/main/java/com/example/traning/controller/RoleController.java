package com.example.traning.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Register;
import com.example.traning.domain.Role;
import com.example.traning.dto.UserRoleDto;
import com.example.traning.repository.RegisterRepository;
import com.example.traning.repository.RoleRepository;
import com.example.traning.service.RoleService;
import com.example.traning.utill.CustomExceptionhandler;

@RestController
//@RequestMapping("/tranapi")
public class RoleController {


	@Autowired
	private RoleService roleservice;
	

	@Autowired
	RegisterRepository registerrepository;
	
	@Autowired
	RoleRepository rolerepository;
	
	//========================CURD OPERATIONS==========================

	//show all role
	@RequestMapping(value="/getallrole",method= RequestMethod.GET)
	public List<Role> showrole() {
		return roleservice.getAllNotes();
	}
	
	
	
	
	
	
	//create Role
		@RequestMapping(value = "/createrole", method = RequestMethod.POST)
			public ResponseEntity<Object> createRole(@Validated @RequestBody Role role) {
			Role obj=null;
	    	if(role.getRoleType().isEmpty())
	    	{
	    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Role can't be null",obj);
	    	}
	    	else {
	    	try {
	    	obj= roleservice.createrole(role);
	    	
	            }
	    	catch(Exception e) {
	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
	    	}
	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
	    	}
				
		
			}
		
		
		
		//add role	
		@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
		public ResponseEntity<Object> assignrole(@Valid @RequestBody UserRoleDto userroledto) {
			Register obj=null;
			if(userroledto.getRoleType().isEmpty())
	    	{
	    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Role cant be null---1",obj);
	    	}
			else {
			
		  try {
	    	obj= roleservice.insertDataWithRole(userroledto);
	            }
	    	catch(Exception e) {
	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
	    	}
	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
	    	}
		}

		//========================CURD OPERATIONS==========================
}
