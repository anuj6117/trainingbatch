package com.crud.demo.conroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@RequestBody User user, @RequestHeader("host") String hostName) {
		System.out.println("Request header information is:::::::" + hostName);
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = userService.addUserService(user);
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
			} else

			{
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
			} else

			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public void updateUserById(@RequestBody User user) {

		userService.updateUser(user);
	}

	@RequestMapping("/deleteuser")
	public void deleteUserById(@RequestParam Integer id) {

		userService.deleteUser(id);
	}
}
