package com.example.adarsh.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Dto.AssigneRole;
import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.domain.Role;
import com.example.adarsh.domain.User;
import com.example.adarsh.domain.VerifyOtp;
import com.example.adarsh.repository.RoleRepository;
import com.example.adarsh.repository.UserRepository;
import com.example.adarsh.service.DataService;

@RestController
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DataService dataService;

	@Autowired
	RoleRepository roleRepository;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		Map<String, Object> result = null;
		try {

			result = dataService.saveUser(user);

			if (result.get("isSuccess").equals(true)) {

				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else

				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);

		} catch (Exception e) {

			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);

		}

	}

	/*
	 * @RequestMapping("/alluser") public List<User> getAllUser() { return
	 * dataService.getAllUsers(); }
	 */
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public ResponseEntity<Object> getAll() {
		List<User> result = null;
		try {
			result = dataService.getAllUsers();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	/*
	 * @RequestMapping(value = "/createrole", method = RequestMethod.POST) public
	 * Role saveRole(@RequestBody Role role) {
	 * 
	 * Role role2 = new Role(); role2.setRoleType(role.getRoleType());
	 * 
	 * return roleRepository.save(role2); }
	 */
	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<Object> saveRole(@RequestBody Role role) {
		ResponseEntity<Object> responseEntity=null;
		Role result = null;

		try {
             if(!("").equals(role.getRoleType().trim())) {
			Role role2 = new Role();
			role2.setRoleType(role.getRoleType());
			result = roleRepository.save(role2);
			responseEntity=ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
             }
             else
             {
            	 responseEntity= ResponseHandler.generateResponse(HttpStatus.OK, false, "failure", result); 
             }
             
		} catch (Exception e) {
			responseEntity= ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return responseEntity;
	}

	/*
	 * @RequestMapping(value = "/asignRole", method = RequestMethod.POST) public
	 * List<Role> asignRole(@RequestBody AssigneRole assigneRole) {
	 * 
	 * return dataService.asignRole(assigneRole);
	 * 
	 * }
	 */
	/*
	 * @RequestMapping("/getbyRole") public List<User> getByRole(@RequestParam Long
	 * id) { return dataService.getByRole(id); }
	 */

	@PostMapping("/verifyuser")
	public ResponseEntity<Object> verifyUser(@RequestBody VerifyOtp data) {
		String result = null;
		try {
			result = dataService.verifyUser(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}

	@RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@RequestParam("id") int id) {
		User obj = null;
		try {
			obj = dataService.getuserById(id);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, e.getMessage(), obj);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Data Search suceessfuly", obj);
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public ResponseEntity<Object> assigneRole(@Valid @RequestBody AssigneRole assigneRole) {
		User obj = null;
		try {
			obj = dataService.assignRole(assigneRole);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successful", obj);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String deletedata(@RequestParam(value = "userId") int userId) {
		if (userId!= 0)
			return dataService.deleteUser(userId);
		else
			return "id is not exist";
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public ResponseEntity<?> updatedata(@RequestParam(value = "userId") int userId, @RequestBody User userDetails) {
		User obj = null;
		try {
			obj = dataService.UpdateUser(userId, userDetails);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, e.getMessage(), obj);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Update suceessfuly", obj);
	}
}
