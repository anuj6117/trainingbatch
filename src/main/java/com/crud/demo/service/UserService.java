package com.crud.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.enums.RoleType;
import com.crud.demo.id.randomgenerator.RandomIDGenerator;
import com.crud.demo.jpaRepositories.RoleJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.jpaRepositories.UserWalletJpaRepository;
import com.crud.demo.model.Role;
import com.crud.demo.model.User;
import com.crud.demo.model.UserWallet;
import com.crud.demo.passwordencoder.CustomPasswordEncoder;

@Service
public class UserService {

	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private RoleJpaRepository roleJpaRepository;
	@Autowired
	private OTPSMSService otpsmsService;
	@Autowired
	private EmailService emailService;

	/* Teting */
	/*-----------------------------------------------------------------*/
	public Map<String, Object> addUserService(User user) {
		Map<String, Object> mapResult = new HashMap<>();
		boolean isSuccess = false;
		try {
			defaultWalletDetails(user);
			user.setRoles(defaultRoleDetails(user));

			user.setCreatedOn(new Date());
			user.setPassword(CustomPasswordEncoder.customPasswordEncoder(user.getPassword()));

			userJpaRepository.save(user);// if user successfully saved then

			System.out.println("User successfully saved");
			Integer integerAutomaticOTP = RandomIDGenerator.randomIdGenerator().nextInt(500);
			String StringAutomaticOTP = integerAutomaticOTP.toString();
			otpsmsService.saveOTP(StringAutomaticOTP);
			emailService.sendEmailToGmail(user.getEmail(), StringAutomaticOTP);
			mapResult.put("Result", "User data saved successfully");
			mapResult.put("isSuccess", true);
			return mapResult;
		} catch (Exception e) {
			mapResult.put("Result", "Failed to save or Error");
			mapResult.put("isSuccess", isSuccess);
			return mapResult;
		}
		/* return mapResult; */
	}
	/*--------------------------------------------------------*/
	/*
	 * public String addUserService(User user) { defaultWalletDetails(user);
	 * user.setRoles(defaultRoleDetails(user));
	 * 
	 * user.setCreatedOn(new Date());
	 * user.setPassword(CustomPasswordEncoder.customPasswordEncoder(user.getPassword
	 * ())); userJpaRepository.save(user);//if user successfully saved then
	 * System.out.println("User successfully saved"); String
	 * automaticOTP=RandomIDGenerator.randomIdGenerator().toString();
	 * otpsmsService.saveOTP(automaticOTP);
	 * emailService.sendEmailToGmail(user.getEmail(), automaticOTP); return
	 * "successfully added"; }
	 */

	/*--------------------------------------------------------*/
	public Map<String, Object> getUserByIdService(Integer userId) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		User user = null;
		try {
			 user = userJpaRepository.findOne(userId);
			if (user == null) {
				throw new NullPointerException();
			}
			map.put("Result", user);
			map.put("isSuccess", true);
			return map;
		} catch (Exception e) {
			map.put("Result", user);
			map.put("isSuccess", isSuccess);
			return map;
		}

	}

	/*--------------------------------------------------------*/
	public Map<String, Object> getAllUserService() {
		Map<String, Object> map=new HashMap<>();
		boolean isSuccess = false;
		try {
			List<User> usersList = userJpaRepository.findAll();
			if(!usersList.isEmpty())
			{
			map.put("Result", usersList);
			map.put("isSuccess", true);
			
			}
			return map;
		} catch (Exception e) {
			map.put("Result", null);
			map.put("isSuccess", isSuccess);
			return map;
		}

	}

	/*--------------------------------------------------------*/
	public void updateUser(User user) {
		if (userJpaRepository.findOne(user.getUserId()) != null) {
			User existingUser = userJpaRepository.findOne(user.getUserId());
			if (user.equals(existingUser) == false) {
				for (Role role : existingUser.getRoles()) {
					roleJpaRepository.delete(role);
					roleJpaRepository.delete(role.getRoleId());

				}

				existingUser.setUserName(user.getUserName());
				existingUser.setPassword(user.getPassword());
				existingUser.setEmail(user.getEmail());
				existingUser.setPhoneNumber(user.getPhoneNumber());
				existingUser.setStatus(user.getStatus());
				existingUser.setCreatedOn(new Date());
				existingUser.setRoles(user.getRoles());

				userJpaRepository.save(existingUser);
			}
		} else {
			System.out.println("user does not exist");
		}
	}

	public void deleteUser(Integer id) {
		userJpaRepository.delete(id);

	}

	// for user default details about wallet and others details to be automatically
	// set
	public User defaultWalletDetails(User user) {
		UserWallet userWallet = new UserWallet();
		Set<UserWallet> setOfUserWallet = new HashSet<>();
		setOfUserWallet.add(userWallet);
		user.setUserWallet(setOfUserWallet);

		user.getUserWallet().forEach(wallet -> wallet.setUser(user));// important
		return user;
	}

	// for default roles details
	public Set<Role> defaultRoleDetails(User user) {
		Set<Role> setOfRole = null;
		Role existingRole = roleJpaRepository.findByRoleType("user");
		// this condition done by admin but here for testing
		if (existingRole == null) {
			existingRole = roleJpaRepository.save(new Role());
			setOfRole = new HashSet<>();
			setOfRole.add(existingRole);
			user.setRoles(setOfRole);
			return setOfRole;
		} else {
			setOfRole = new HashSet<>();
			setOfRole.add(existingRole);
			user.setRoles(setOfRole);
			return setOfRole;
		}
	}
}
