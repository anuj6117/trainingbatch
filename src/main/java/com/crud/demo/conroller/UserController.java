package com.crud.demo.conroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.model.User;
import com.crud.demo.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@Validated @RequestBody User user, @RequestHeader("host") String hostName) {
		System.out.println("Request header information is:::::::" + hostName);
		LOGGER.info("Message on usercontroller (adduser):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = userService.addUser(user);
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

	@RequestMapping(value = "/getbyuserid")
	public ResponseEntity<Object> getUserById(@RequestParam Integer userId, @RequestHeader("host") String token) {
		System.out.println("for fetch");
		Map<String, Object> map = null;
		try {
			map = userService.getUserByIdService(userId);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
		}
	}

	@RequestMapping("/getallusers")
	public ResponseEntity<Object> getAllUser() {
		Map<String, Object> map = null;
		try {
			map = userService.getAllUserService();
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
		}
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public ResponseEntity<Object> updateUserById(@Validated @RequestBody User user) {
		Map<String, Object> map = null;
		try {
		map=userService.updateUser(user);
		if (map.get("isSuccess").equals(true)) {
			map.remove("isSuccess", true);
			return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
		}
	} catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
	}
	}

	@RequestMapping("/deleteuser")
	public ResponseEntity<Object> deleteUserById(@RequestParam Integer userId) {
		Map<String, Object> map = null;
		try
		{
		map=userService.deleteUser(userId);
		if (map.get("isSuccess").equals(true)) {
			map.remove("isSuccess", true);
			return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
		} else {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
		}
	} catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
	}
	}
}
