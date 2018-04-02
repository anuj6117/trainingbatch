package com.example.demo.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repoINterface.RoleRepository;
import com.example.demo.services.deleteService.DeleteService;
import com.example.demo.utils.ResponseHandler;

@RestController
public class DeleteController {

	@Autowired
	DeleteService userDelete;
	@Autowired
	RoleRepository roleData;

	@RequestMapping(value = "/deleteById", method = RequestMethod.GET)
	public ResponseEntity<Object> deleteById(@RequestParam("id") Long id) {
		String result = null;
		try {
			result = userDelete.deleteById(id);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public void deleteRole(@RequestParam("id") Long id) {

		roleData.delete(id);
	}

}
