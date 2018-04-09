package com.example.demo.services;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.userDTO.ResetPasswordDTO;
import com.example.demo.dto.userDTO.RoleDTO;
import com.example.demo.model.userModel.RoleModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.VerifyModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.RoleRepository;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.VerifyRepository;

@Service
public class UserServices {

	@Autowired
	UserRepository userData;

	@Autowired
	RoleRepository roleData;

	@Autowired
	MailServices mailServices;

	@Autowired
	SmsServieces smsServieces;

	@Autowired
	VerifyRepository verifyData;

	public UserModel saveUserData(UserModel data) {
		if(data.getPassword()==null||data.getPassword().trim().length()==0)
			throw new RuntimeException("password not be null");
		Random rand = new Random();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		WalletModel walletModel = new WalletModel();
		long leftLimit = 1L;
		long rightLimit = 10000000000L;
		long randemId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		walletModel.setRandemId(randemId);
		walletModel.setAmount(0);
		walletModel.setShadoBalance(0);
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String date = formatter.format(today);

		UserModel model = new UserModel();
		model.setCountry(data.getCountry());
		model.setEmail(data.getEmail());
		model.setPhoneNumber(data.getPhoneNumber());
		model.setUserId(data.getUserId());
		// model.setPassword(passwordEncoder.encode(data.getPassword()));
		model.setPassword(data.getPassword());
		model.setUserName(data.getUserName());
		model.setStatus(false);
		model.setCreatedOn(date);
		model.getWalletModel().add(walletModel);
		RoleModel role = roleData.findOneByRoleType("user");
		if(role==null)
			throw new RuntimeException("role not found");
		model.getRole().add(role);
		role.getUser().add(model);
		walletModel.setUserdata(model);
		UserModel checkData = userData.findByEmailOrPhoneNumber(data.getEmail(), data.getPhoneNumber());
		if (checkData != null) {
			System.out.println("dsfdsfds");
			throw new NullPointerException("user already inserted Email and PhoneNumber change ");
		}
		UserModel result=userData.save(model);
		
		try {
			int otp = rand.nextInt(1000000);
			VerifyModel verify = new VerifyModel();
			verify.setTokenOtp(otp);
			verify.setUserName(model.getUserName());
			verify.setDate(date);
			Thread thread = new Thread(){
			    public void run(){
			    	verifyData.save(verify);
					try {
						mailServices.sendMail(data.getEmail(),otp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			  };
			  thread.start();
			
			// smsServieces.sendSMS(otp);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}

	public List<UserModel> findAll() {
		List<UserModel> data = userData.findAll();
		if (data.isEmpty())
			throw new NullPointerException("data not fond");
		return userData.findAll();

	}

	public UserModel findById(Long id) {
		UserModel result=userData.findByUserId(id);;
		if(result==null)
			throw new RuntimeException("id not found");
		return result;
	}

	//
	public List<UserModel> findbyusername(String username, int page) {
		Pageable pageable = new PageRequest(page, 10, Sort.Direction.ASC, "userName");
		List<UserModel> model2 = userData.findByUserNameContaining(username, pageable);
		if(model2==null)
			throw new NullPointerException("username not match");
		return model2;
	}

	// addrole---------
	public RoleModel addRole(RoleModel data) {
		if(data.getRoleType().trim().length()==0)
			throw new RuntimeException("please enter valid role");
		RoleModel model = roleData.findOneByRoleType(data.getRoleType());
		if (model != null) 
			throw new RuntimeException("role already exist");
			model=roleData.save(data);
			return model;
	}

	// remove user role----------
	public String removeRoleToUser(RoleModel model) {
		boolean result = false;
		UserModel user = userData.findOne(model.getRoleId());
		RoleModel role = roleData.findOneByRoleType(model.getRoleType());
		if (user != null && role != null) {
			List<RoleModel> rolecheck = user.getRole();
			for (RoleModel role1 : rolecheck) {
				if ((role1.getRoleType().equals(role.getRoleType()))) {
					user.getRole().remove(role);
					result = true;
					break;
				}

			}
			if (result) {
				// user.getRole().add(role);
				userData.save(user);
				return "success";
			}
			// user.getRole().add(role);
			// userData.save(user);

		}
		return "error";

	}

	// addroletouser---------
	public String addRoleToUser(RoleDTO model) {
		boolean result = true;
		UserModel user = userData.findOne(model.getUserId());
		RoleModel role = roleData.findOneByRoleType(model.getRoleType());
		if (user != null && role != null) {
			List<RoleModel> rolecheck = user.getRole();
			for (RoleModel role1 : rolecheck) {
				if ((role1.getRoleType().equals(role.getRoleType()))) {
					result = false;
					throw new RuntimeException("role already assign");
				}

			}
			if (result) {
				user.getRole().add(role);
				userData.save(user);
				return "success";
			}
			// user.getRole().add(role);
			// userData.save(user);

		}
		else
			throw new NullPointerException("user or role not found");
		return "error";

	}

	public List<RoleModel> findAllUserModel() {
		List<RoleModel> model2 = roleData.findAll();
		return model2;
	}

	public List<RoleModel> findAllByUserModel(String data) {
		List<RoleModel> model2 = roleData.findAllByRoleType(data);
		return model2;
	}

	public List<RoleDTO> findAllRole() {
		List<RoleModel> models = roleData.findAll();
		List<RoleDTO> data = new ArrayList<RoleDTO>();
		for (RoleModel model : models) {
			RoleDTO dto = new RoleDTO();
			dto.setUserId(model.getRoleId());
			dto.setRoleType(model.getRoleType());
			data.add(dto);
		}
		return data;
	}

	// -----------------
	public UserModel userLogin(String email, String password) {
		UserModel model=userData.findOneByEmailAndPassword(email, password);
		if(model==null)
			throw new RuntimeException("eewrwe");
		return model;
	}

	// -------
	public UserModel resetPassword(ResetPasswordDTO resetdata) {
		UserModel user = userData.findOneByEmailAndPassword(resetdata.getEmail(), resetdata.getOldPassword());
		if (user != null) {
			user.setPassword(resetdata.getNewPassword());
			userData.save(user);
		}
		return user;

	}

	// get user Role----
	public List<String> findRoleByUserName(String name) {
		UserModel role = userData.findByUserName(name);
		List<RoleModel> roletype = role.getRole();
		List<String> roleget = new ArrayList<>();
		for (RoleModel role1 : roletype) {
			roleget.add(role1.getRoleType());
		}
		return roleget;
	}

	// -verifyuser-----
	public UserModel verifyUser(VerifyModel data) {
		VerifyModel verify = verifyData.findOneByUserNameAndTokenOtp(data.getUserName(), data.getTokenOtp());
		if (verify== null) 
			throw new NullPointerException("user id or otp not found");
		UserModel user=null;
		user=userData.findByUserId(data.getId());
		if(user==null)
			throw new RuntimeException("id not match");
		user.setStatus(true);
		userData.save(user);
			verifyData.delete(verify.getId());
			return user;
		
	}

	// avtive and seactive user
	public UserModel activeDeactiveUser(UserModel data) {
		UserModel user = userData.findOne(data.getUserId());
		if (user == null)
			throw new NullPointerException("id not found");
		user.setStatus(data.isStatus());
		user=userData.save(user);
		return user;
	}
}
