package com.example.demo.controller.userController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.userDTO.LoginDTO;
import com.example.demo.dto.userDTO.ResetPasswordDTO;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.services.UserServices;

@RestController
public class UserLoginController {

	@Autowired
	UserServices userData;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public Map<String, Object> userLogin(@RequestBody LoginDTO data) {
		Map<String, Object> map = new HashMap<>();
		UserModel user = userData.userLogin(data.getEmail(), data.getPassword());
		UserModel user1 = userData.findById(user.getId());
		if (user == null) {
			map.put("failure", new String("Fa"));
			return map;
		} else {
			map.put("success", user1);

			return map;
		}
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetPassword(@RequestBody ResetPasswordDTO data) {
		UserModel user = userData.resetPassword(data);
		if (user == null)
			return "error";

		return "success";
	}
}
