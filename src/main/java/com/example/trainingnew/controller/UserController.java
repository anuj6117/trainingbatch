package com.example.trainingnew.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.OTPModel;
import com.example.trainingnew.model.UserModel;
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

	// @PreAuthorize("hasRole('admin')")
	//getAllUsersApi
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public ResponseEntity<List<UserModel>> showData() {

		return services.getAllData();
	}

	//getListByUserName
	@RequestMapping(value = "/listPageable", method = RequestMethod.GET)
	public List<UserModel> showPagable(@RequestParam("name") String name, @RequestParam("page") int page) {

		logger.error(name + " " + page);
		return services.getByPagable(name, page);
	}

	//searchingDetailsUsingLike
	@RequestMapping(value = "/searching", method = RequestMethod.GET)
	public List<UserModel> searching(@RequestParam("search") String search) {

		return services.search(search);
	}
	
	//signUpApi
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> insertData(@Validated @RequestBody UserModel user) {

		String obj = null;
			try {
				obj = services.createData(user);
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}

		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
	}

	 @RequestMapping(value="/verifyuser",method=RequestMethod.POST)
	 public ResponseEntity<Object> verifyUser(@RequestBody() OTPModel otp) {
		 UserModel obj = null;

			try {
				obj = services.otpValidate(otp);
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	 }

	
	//getSingleUserApi
	@RequestMapping(value = "/getbyuserid/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id) {
		UserModel obj = null;

		try {
			obj = services.getDataById(id);
		} catch (UserNotFoundException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}

	//showAllActiveUserApi
	@RequestMapping(value = "/showActiveUsers", method = RequestMethod.GET)
	public List<UserModel> showActiveUsers() {

		return userrepo.findByStatusTrue();
	}

	//showAllInactiveUsers
	@RequestMapping(value = "/showInactiveUsers", method = RequestMethod.GET)
	public List<UserModel> showInActiveUsers() {

		return userrepo.findByStatusFalse();
	}

	//updateUserApi
	@RequestMapping(value = "/updateuser/{id}", method = RequestMethod.POST)
	public  ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody UserModel allDetails) {
		UserModel obj = null;
		try {
			obj = services.updateData(id, allDetails);
		} catch (Exception e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Updated", obj);
	}

	//deleteUserApi
	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
		UserModel obj = null;
		try {
			obj = services.deleteData(id);
		} catch (Exception e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Deleted", obj);

	}

}
