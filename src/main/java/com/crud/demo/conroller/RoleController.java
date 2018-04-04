package com.crud.demo.conroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.dto.UserRoleDTO;
import com.crud.demo.model.Role;
import com.crud.demo.service.RoleService;

@RestController
public class RoleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/createrole",method=RequestMethod.POST)
	public ResponseEntity<Object> createRole(@RequestBody Role role) {
		LOGGER.info("Role controller hit");
		Map<String, Object> map = null;
		try
		{
	map= roleService.createRole(role);
		if (map.get("isSuccess").equals(true)) {
			map.remove("isSuccess", true);
			return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
		} else
		{
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
		}
	} catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

	}
		
	}
	@RequestMapping(value="/assignrole",method=RequestMethod.POST)
	public ResponseEntity<Object> assignRole(@RequestBody UserRoleDTO userRoleDTO)
	{
		LOGGER.info("Role controller hit");
		Map<String, Object> map = null;
		try
		{
		map=roleService.assignRole(userRoleDTO.getUserId(),userRoleDTO.getRoleType());
		if (map.get("isSuccess").equals(true)) {
			map.remove("isSuccess", true);
			return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
		} else
		{
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
		}
	} catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

	}
	}

}
