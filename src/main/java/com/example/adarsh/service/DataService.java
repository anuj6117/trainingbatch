package com.example.adarsh.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adarsh.Dto.AssigneRole;
import com.example.adarsh.SmsServices.SmsServices;
import com.example.adarsh.domain.OrderTabel;
import com.example.adarsh.domain.Role;
import com.example.adarsh.domain.User;
import com.example.adarsh.domain.VerifyOtp;
import com.example.adarsh.domain.Wallet;
import com.example.adarsh.mailService.MailServieces;
import com.example.adarsh.repository.RoleRepository;
import com.example.adarsh.repository.UserRepository;
import com.example.adarsh.repository.VerifyOtpRepository;

@Service
public class DataService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	MailServieces mailServices;

	@Autowired
	SmsServices smsServices;

	@Autowired
	VerifyOtpRepository verifyOtpRepository;

	// ****************************************************************************************************************
	public Map<String, Object> saveUser(User user) {

		System.err.println(
				"+++++++++++++++++++++++++++++++User Registration++++++++++++++++++++++++++++++++++++++++++++++");

		User user1 = new User();
		Wallet wallet = new Wallet();
		OrderTabel orderTabel = new OrderTabel();
		Random rand = new Random();
		long leftLimit = 1L;
		long rightLimit = 10000000000L;
		long randomId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		wallet.setRandomId(randomId);
		Map<String, Object> result = new HashMap<>();

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String date = formatter.format(today);
		if (userRepository.findByEmail(user.getEmail()) != null) {
			// result.put("isSuccess", "isSuccess");
			result.put("message", "ALREADY_EXIST");
			// result.put("user", user);
			return result;
		}

		user1.setUserName(user.getUserName());
		user1.setCountry(user.getCountry());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setEmail(user.getEmail());
		user1.setPassword(user.getPassword());
		user1.setDate(date);
		user1.getWallet().add(wallet);
		wallet.setUser(user1);
		// user1.getOrderTabel().add(orderTabel);
		// orderTabel.setUser(user1);

		/*
		 * List<Wallet> walletList = user1.getWallet(); walletList.size();
		 * System.out.println("**********" + walletList.size());
		 */

		// wallet.setUser(user1);
		userRepository.save(user1);

		System.err.println("+++++++++++++++++++++++++ Registration mail++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			// mailServices.sendMail(user.getEmail());
			// smsServices.sedSms(otp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		result.put("isSuccess", true);
		result.put("message", "SUCCESS");

		// result.put("user", user);
		return result;

	}

	// **********************************************************************************************************

	public List<User> getAllUsers() {
		System.err.println("+++++++++++++++++++++++++All user list++++++++++++++++++++++++++++++++++++++++++++++");
		List<User> userList = userRepository.findAll();
		if (userList.isEmpty()) {
			throw new NullPointerException("data not fond");
		}

		return userList;
	}

	/*
	 * public List<User> asignRole(AssigneRole assigneRole) { Long[] userids =
	 * assigneRole.getUser(); Role role =
	 * roleRepository.findOne(assigneRole.getId()); if (role != null) {
	 * 
	 * List<User> result = new ArrayList<>(); for (Long id : userids) { User user =
	 * userRepository.findOne(id); if (user != null) {
	 * 
	 * result.add(user); }
	 * 
	 * } role.setUser(result); System.out.println("result........" + result);
	 * 
	 * roleRepository.save(role); return result; } return null; }
	 */

	/*
	 * public List<User> getByRole(Long id) { Role role =
	 * roleRepository.findOne(id); return role.getUser(); }
	 */

//******************************************************************************************************************

	public User assignRole(AssigneRole assigneRole) {
		System.err.println("+++++++++++++++++++++++++assigne role++++++++++++++++++++++++++++++++++++++++++++++");
		User userID = userRepository.findOneByUserId(assigneRole.getUserId());
		Role roleType = roleRepository.findOneByRoleType(assigneRole.getRoleType());
		if (userID != null) {
			if (roleType != null) {
				userID.getRole().add(roleType);
				User users = userRepository.save(userID);
				return users;
			} else {
				throw new NullPointerException("User role doesn't exist");
			}
		} else {
			throw new NullPointerException("User id doesn't exist");
		}
	}

//***************************************************************************************************************

	public String verifyUser(VerifyOtp data) {
		
		System.err.println("+++++++++++++++++++++++++ verify otp++++++++++++++++++++++++++++++++++++++++++++++");
		VerifyOtp verify = verifyOtpRepository.findOneByUserNameAndTokenOtp(data.getUserName(), data.getTokenOtp());
		if (verify == null)
			throw new NullPointerException("id not found");
		verifyOtpRepository.delete(verify.getId());
		return "success";

	}
	
	//*********************************************************************************************************

	public User getuserById(int id) {
		System.err.println("+++++++++++++++++++++++++ All user list through id++++++++++++++++++++++++++++++++++++++++++++++");
		User usrid = userRepository.findByUserId(id);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id doen't be exist");
		}
	}

	
	//***************************************************************************************************************8
	public String deleteUser(int userId) {
		
		System.err.println("+++++++++++++++++++++++++ delete user through id++++++++++++++++++++++++++++++++++++++++++++++");
		User deleteById = userRepository.findByUserId(userId);
		if (deleteById != null) {
			userRepository.delete(deleteById);
			return "succesfully delete " + deleteById.getUserId() + " " + deleteById.getUserName();
		} else {
			return "User Doesnt exist";
		}
	}

	//**************************************************************************
	public User UpdateUser(int userId, User userDetails) {
		
		System.err.println("+++++++++++++++++++++++++ update user function++++++++++++++++++++++++++++++++++++++++++++++");
		User usrDetail = userRepository.findByUserId(userId);
		if (usrDetail != null) {
			usrDetail.setUserName(userDetails.getUserName());
			usrDetail.setEmail(userDetails.getEmail());
			usrDetail.setPassword(userDetails.getPassword());
			usrDetail.setCountry(userDetails.getCountry());
			usrDetail.setPhoneNumber(userDetails.getPhoneNumber());
			User updatedUser = userRepository.save(usrDetail);
			return updatedUser;
		} else {
			throw new NullPointerException("Id doen't be exist");
		}
	}
}
