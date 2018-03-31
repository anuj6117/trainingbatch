package com.example.trainingnew.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.Usermodel;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.services.EmailServices;
import com.example.trainingnew.services.UserServices;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class UserController {

	@Autowired
	UserServices services;

	@Autowired
	UserRepo userrepo;

	@Autowired
	EmailServices emailServices;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	// @PreAuthorize("hasRole('admin')")
	@GetMapping("/secured/hello")
	public String securedhello() {
		return "Secured Hello World";
	}

	@GetMapping("/manager")
	public String manager() {
		return "Only manager can see this";
	}

	@GetMapping("/managerAndUser")
	public String manageranduser() {
		return "Only Manager and User both can see this.";
	}

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	// ==============================================================================================

	// @PreAuthorize("hasRole('admin')")
	// -----------------------------------------------------------------------------------getall
	// users api
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public ResponseEntity<List<Usermodel>> showData() {

		return services.getAllData();
	}

	// ----------------------------------------------------------------------------------signup api
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> insertData(@Valid @RequestBody Usermodel note) {

		Usermodel obj = null;

		if (note.getUserName().isEmpty()) {
			// return new ResponseEntity(new CustomErrorType("UserName can't be
			// empty"),HttpStatus.NOT_FOUND);
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Username can't be empty", obj);
		} else if (note.getEmail().isEmpty()) {
			// return new ResponseEntity(new CustomErrorType("Email asdf can't be
			// empty"),HttpStatus.NOT_FOUND);
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Email can't be empty", obj);
		} else if (note.getPassword().isEmpty()) {
			// return new ResponseEntity(new CustomErrorType("Password can't be
			// empty"),HttpStatus.NOT_FOUND);
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Password can't be empty", obj);
		} else {
			try {
				obj = services.createData(note);
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
		}

		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
		//
	}

	// @RequestMapping(value="/validateotp",method=RequestMethod.POST)
	// public ResponseEntity<?> verifyUser(@RequestBody() OTPModel otp) {
	// return services.otpValidate(otp);
	// }

	// --------------------------------------------------------------------------------------
	@RequestMapping(value = "/show/", method = RequestMethod.POST)
	public ResponseEntity<Object> show() {
		Usermodel obj = null;
		return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Please Enter the id", obj);
	}

	// ------------------------------------------------------------------------------get single user api
	@RequestMapping(value = "/show/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
		Usermodel obj = null;

		try {
			obj = services.getDataById(id);
		} catch (UserNotFoundException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}

	// ---------------------------------------------------------------------------------update user api
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody Usermodel allDetails) {
		Usermodel obj = null;
		try {
			obj = services.updateData(id, allDetails);
		} catch (Exception e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Updated", obj);
	}

	// ------------------------------------------------------------------------------------delete user api
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
		Usermodel obj = null;
		try {
			obj = services.deleteData(id);
		} catch (Exception e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Deleted", obj);
		 
	}

	// ==============================================================================

}
