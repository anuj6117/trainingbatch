package com.crud.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crud.demo.id.randomgenerator.RandomIDGenerator;
import com.crud.demo.jpaRepositories.RoleJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.model.OTPSMS;
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

	 private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	/*-----------------------------------------------------------------*/
	public Map<String, Object> addUser(User user) {//method name must be meaningful
		Map<String, Object> mapResult = new HashMap<>();
		boolean isSuccess = false;
		
			if((user.getEmail()!=null)&&(user.getPhoneNumber()!=null)&&(userJpaRepository.findByEmail(user.getEmail())==null))
			{
				Integer integerAutomaticOTP = RandomIDGenerator.randomIdGenerator().nextInt(500);
				String StringAutomaticOTP = integerAutomaticOTP.toString();
			defaultWalletDetails(user);
			user.setRoles(defaultRoleDetails(user));
			user.setCreatedOn(new Date());
			user.setPassword(CustomPasswordEncoder.customPasswordEncoder(user.getPassword()));
			OTPSMS otpsms=new OTPSMS();
		      otpsms.setDate(new Date());
		      otpsms.setEmail(user.getEmail());
		      otpsms.setTokenOtp(StringAutomaticOTP);
		      user.setOtpsms(otpsms);
		     /* otpsms.setUser(user);*/
			userJpaRepository.save(user);// if user successfully saved then
			
			LOGGER.debug("debug");//doubt when printing
			LOGGER.trace("trace");//doubt when printing
			/*LOGGER.warn("warn");
			LOGGER.error("error");*/
			LOGGER.info("Message on service (adduser):::::::::::::::::User successfully saved");
			
			
			
			/*otpsmsService.saveOTP(StringAutomaticOTP,user.getEmail());*/
			otpsmsService.sendOTPSMS(StringAutomaticOTP);
			emailService.sendEmailToGmail(user.getEmail(), StringAutomaticOTP);
			mapResult.put("Result", "User data saved successfully");
			mapResult.put("isSuccess", true);
			LOGGER.info("Message on service (adduser)::::::::::::::::User saved +OTP saved+OTP send successfully");
			//only one return type in one function
		} else {
			mapResult.put("Result", "Failed to save or user alrady exist");
			mapResult.put("isSuccess", isSuccess);
			LOGGER.error("Message on service (adduser)::::::::::::::::Something went wrong");
			
		}
		return mapResult;
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
			LOGGER.info("Message on service::::::::::::::::::Successfully fetched user by id");
			return map;
		} catch (Exception e) {
			map.put("Result", user);
			map.put("isSuccess", isSuccess);
	LOGGER.error("Message on service::::::::::::::::::something went wrong in finding user based on id");
			return map;
		}
	}

	/*--------------------------------------------------------*/
	public Map<String, Object> getAllUserService() {
		Pageable pageRequest=new PageRequest(0, 50);
		Map<String, Object> map=new HashMap<>();
		boolean isSuccess = false;
		try {
			/*String email;*/
			Page<User> usersList = userJpaRepository.findAll(pageRequest);
			/*List<User> usersList = userJpaRepository.findAll();*/
			if(usersList.getTotalPages()>0)
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
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		if (userJpaRepository.findOne(user.getUserId()) != null) {
			User existingUser = userJpaRepository.findOne(user.getUserId());
			if (user.equals(existingUser) == false) {
				for (Role role : existingUser.getRoles()) {
					roleJpaRepository.delete(role);
					roleJpaRepository.delete(role.getRoleId());
				}
				
				//mail can't be changed
				if(existingUser.getEmail().equals(user.getEmail()))
				{
				existingUser.setEmail(user.getEmail());
				existingUser.setUserName(user.getUserName());
				existingUser.setPassword(user.getPassword());
				existingUser.setPhoneNumber(user.getPhoneNumber());
				existingUser.setStatus(user.getStatus());
				existingUser.setCreatedOn(new Date());
				existingUser.setRoles(user.getRoles());

				userJpaRepository.save(existingUser);
				map.put("Result", "success");
				map.put("isSuccess", true);
				LOGGER.info("Message on service::::::::::::::::::Successfull updation");
	
				}
				else
				{
					map.put("Result", "you cannot change your id");
					map.put("isSuccess", isSuccess);
					LOGGER.error("Message on service::::::::::::::::::You canot change your mail id");	
				}
			}
		} else {
			map.put("Result", "something went wrong in updationnor user does'not exist ");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service::::::::::::::::::Error in updation");
		}
		return map;
	}

	public Map<String, Object> deleteUser(Integer userId) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccess = false;
		if(userJpaRepository.findOne(userId)!=null)
		{
		userJpaRepository.delete(userId);
		map.put("Result", "success");
		map.put("isSuccess", true);
		LOGGER.info("Message on service::::::::::::::::::User Successfully deleted");
		}
		else
		{
			map.put("Result", "user doesnot exist");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service::::::::::::::::::Error in deletion");
		}
      return map;
	}

	// for user default details about wallet and others details to be automatically
	// set
	public User defaultWalletDetails(User user) {
		UserWallet userWallet = new UserWallet();
		Set<UserWallet> setOfUserWallet = new HashSet<>();
		setOfUserWallet.add(userWallet);
		user.setUserWallet(setOfUserWallet);

		user.getUserWallet().forEach(wallet -> wallet.setUser(user));// important
		LOGGER.info("Message on service::::::::::::::::::default role set");
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
			LOGGER.info("Message on service::::::::::::::::::Role successfully added to role table");
			return setOfRole;
		} else {
			setOfRole = new HashSet<>();
			setOfRole.add(existingRole);
			user.setRoles(setOfRole);
			LOGGER.info("Message on service::::::::::::::::::Default Existing role (User)is assigned successfully on signup");
			return setOfRole;
			
		}
	}
}
