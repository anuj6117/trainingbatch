package com.example.trainingnew.controller;

import java.util.List;

import javax.persistence.EntityListeners;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.DTO.UserRoleDTO;
import com.example.trainingnew.model.Rolemodel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.reprository.RoleRepo;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.services.RoleServices;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
@EntityListeners(AuditingEntityListener.class)
public class RoleController {
	
	@Autowired
	RoleRepo rolerepo;
	
	@Autowired
	UserRepo userrepo;
	
	@Autowired
	RoleServices roleservices;
	
	@RequestMapping(value="/showrole",method= RequestMethod.GET)
	public List<Rolemodel> showrole() {
		return rolerepo.findAll();
	}
	
	
	//createRoledataServices
	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
		public ResponseEntity<Object> createRole(@RequestBody Rolemodel note) {

		 Rolemodel obj = null;
			try {
				 obj =roleservices.createRole(note);
			}catch(Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
			
		
		}
	
	//assigingRoleToUsers
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public ResponseEntity<Object> insertDataWithRole(@Valid @RequestBody UserRoleDTO note) {
		 UserModel obj = null;
		try {
			 obj =roleservices.assignRole(note);
		}catch(Exception e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
		
	}
	
}
