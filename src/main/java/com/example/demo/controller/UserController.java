package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponse;

@RestController
//@RequestMapping("/userdata")
public class UserController {

	@Autowired
	private UserService userService;

	// -------------------SIGNUP--------------------------

	// To add a new user and a default is created
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = { "application/JSON" })
	public ResponseEntity<Object> add(@RequestBody UserModel user) throws Exception {
		String response = "";
		try {
			response = userService.addUser(user);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, response, null);

	}

	// To verify user
	@RequestMapping(value = "/verifyUser/{otp}", method = RequestMethod.POST)
	public ResponseEntity<Object> verifySignUp(@RequestBody UserModel user, @PathVariable(value = "otp") Integer otp)
			throws Exception {
		userService.verifyUser(user,otp);
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", null);
	}

	// ----------------GET ALL USER LIST---------------------

	// Get All User Detail
	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllDetail() {

		Object obj;
		try {
			obj = userService.getAllDetails();
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Data Loaded", obj);

	}

	// ------------UPDATE USER DATA & ADD WALLET AND ROLE--------------------------

	// To update user data
	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public List<UserModel> update( @RequestBody UserModel userDetails) {
		return userService.updateUser(userDetails.getUserid(), userDetails);
	}

	// Add another role for user
	@RequestMapping(value = "/addrole/{role}", method = RequestMethod.POST)
	public ResponseEntity<Object> addRoles(@PathVariable(value = "role") String roleType, @RequestBody UserModel user) {
		userService.addAnotherRole(user, roleType);
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", null);
	}

	// To add another wallet for user
	@RequestMapping(value = "/addwallet/{name}", method = RequestMethod.POST)
	public ResponseEntity<Object> addingAnotherWallet(@PathVariable(value = "name") String walletType,
			@RequestBody UserModel user) {
	
		Object obj;
		try {
			obj = userService.addAnotherWallet(user, walletType);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Data Loaded", obj);
		
		
	}

	// To add amount into user wallet
	@RequestMapping(value = "/addamount/{amount}/{walletType}", method = RequestMethod.POST)
	public ResponseEntity<Object> addAmount(@PathVariable(value = "amount") Float amount,
			@PathVariable(value = "walletType") String walletType, @RequestBody UserModel userDetails) {
		userService.addAmountIntoWallet(userDetails, amount, walletType);
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", null);
	}

	// To withdraw amount into user wallet
	@RequestMapping(value = "/withdrawamount/{amount}/{walletType}", method = RequestMethod.POST)
	public ResponseEntity<Object> withdrawAmount(@PathVariable(value = "amount") Float amount,
			@PathVariable(value = "walletType") String walletType, @RequestBody UserModel userDetails) {
		userService.withdrawAmountFromWallet(userDetails, amount, walletType);
		return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", null);

	}

	// ------------ FETCH ROLE AND USER AND VICE VERSA
	// To fetch role
	@RequestMapping(value = "/fetchrole", method = RequestMethod.POST)
	public Set<RoleModel> fetchRoles(@RequestBody UserModel user) {
		Set<RoleModel> roleList = userService.fetchRole(user);
		return roleList;
	}

	// To fetch user
	@RequestMapping(value = "/fetchuser", method = RequestMethod.POST)
	public Set<UserModel> fetchUser(@RequestBody RoleModel role) {
		Set<UserModel> roleList = userService.fetchUser(role);
		return roleList;
	}

	// ----------USER LOGIN--------------------

	// For user login
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public ResponseEntity<Object> getUser(@RequestBody UserModel userDetails, @RequestHeader("token") String ll) {
		try {
//			System.out.println("-------------------");
//			System.out.println(userDetails.getEmail() + "-------------------");
//			System.out.println(userDetails.getPassword() + "-------------------");
			if (userDetails.getEmail().equals("") || userDetails.getPassword().equals("")) {
				System.out.println("--------aaaa-----------");
				return ApiResponse.generateResponse(HttpStatus.OK, true, "Failure", null);
			} else {

				System.out.println("--------azxcvbnm-----------");

				UserModel user = userService.getUser(userDetails);
				System.out.println("--------azxc+" + user + "+vbnm-----------");
				return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", user);
			}
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "failure", null);
		}

	}
	// ----------------TO DELETE A USER

	// To delete amount into user wallet
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public ResponseEntity<Object> delete(@RequestBody UserModel userDetails, HttpServletResponse httpResponse) {
		if (userDetails.getUserid() != 0) {
			userService.deleteUser(userDetails.getUserid());
			return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "Failure", null);
		}

	}

	// --------------------PAGINATION METHODS/LIKE METHODS----------------------
	@RequestMapping(value = "/getallpagination", method = RequestMethod.GET)
	public ResponseEntity<Object> finAllPagination() {
		List<UserModel> list;

		try {
			list = userService.findAllPagination(PageRequest.of(1, 2, Direction.ASC, "userName"));
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", list);
	}

	@RequestMapping(value = "/getnamepagination", method = RequestMethod.POST)
	public ResponseEntity<Object> finAllByName(@RequestBody UserModel userModel) {
		List<UserModel> list;

		try {
			list = userService.findByName(userModel.getUserName(), PageRequest.of(0, 2, Direction.ASC, "userName"));
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", list);
	}

	@RequestMapping(value = "/getnamecontaining", method = RequestMethod.POST)
	public ResponseEntity<Object> findByNameContaining(@RequestBody UserModel userModel) {
		List<UserModel> list;

		try {
			list = userService.findByNameContaininy(userModel.getUserName(), PageRequest.of(1, 2, Direction.ASC, "userName"));
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", list);
	}
	
	
	// For user login
		@RequestMapping(value = "/getbyuserid", method = RequestMethod.POST)
		public ResponseEntity<Object> getUserByid(@RequestBody UserModel userDetails) {
			UserModel user = new UserModel();
			try {

				if (userDetails.getUserid()>0) {
					 user = userService.getUserById(userDetails);
					
				}
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.OK, true, "Enter Valid Id", null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, "Success", user);
		}

}
