package com.example.trainingnew.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.OTPModel;
import com.example.trainingnew.model.Rolemodel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.model.Walletmodel;
import com.example.trainingnew.reprository.OTPRepo;
import com.example.trainingnew.reprository.RoleRepo;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.reprository.WalletRepo;
import com.example.trainingnew.util.CustomErrorType;

@Service
public class UserServices implements UserDetailsService {

	public static final Logger logger = LoggerFactory.getLogger(UserServices.class);
	int otp;
	String body = "Hello User Your Generated Otp is -";
	Date date = new Date();

	boolean hasdata = false;

	@Autowired
	UserRepo userrepo;

	@Autowired
	RoleRepo rolerepo;

	@Autowired
	private OTPRepo otpRepo;

	@Autowired
	EmailServices emailservices;
	WalletRepo walletRepo;

	public int generateOtp() {

		Random random = new Random();
		otp = 1000 + random.nextInt(9000);

		return otp;
	}

	// getallServices
	public ResponseEntity<List<UserModel>> getAllData() {
		List<UserModel> find = userrepo.findAll();

		if (find.isEmpty()) {
			return (new ResponseEntity(new CustomErrorType("No any data exist"), HttpStatus.NOT_FOUND));
		}

		else {
			return new ResponseEntity<List<UserModel>>(find, HttpStatus.OK);
		}

	}

	

	// ----------------------------------------------------------------------------------------addData
	public UserModel createData(UserModel note) throws UserNotFoundException {

		Rolemodel rolemodel = rolerepo.findOneByRoleType("user");
		UserModel userModel = new UserModel();
		UserModel umodel = userrepo.findOneByEmail(note.getEmail());
	
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(note.getPassword());
		
		if (umodel != null) {
			logger.error("uanable to create new user with {} email", note.getEmail());

			throw new UserNotFoundException("This User alredy exist");
		}
		else {
			userModel.setUsername(note.getUserName());
			userModel.setEmail(note.getEmail());
			userModel.setStatus(false);
			userModel.setPassword(hashedPassword);
			userModel.setPhoneNumber(note.getPhoneNumber());
			userModel.setCountry(note.getCountry());
			userModel.setCreatedOn(date);
			userModel.getRoles().add(rolemodel);

//			 if(sendOTP()) {
			Walletmodel wmodel=new Walletmodel();
			wmodel.setBalance(wmodel.getBalance());
			wmodel.setShadowBalance(wmodel.getShadowBalance());
			wmodel.setWalletType(wmodel.getWalletType());
			wmodel.setUserModel(userModel);
			
			userModel.getWallets().add(wmodel);
			
			//walletRepo.save(wmodel);
			return userrepo.save(userModel);
			
//			logger.error("after send otp cursor come save the data on usertable.");
			 }
//			 else {
//			 logger.error("Sorry!unable to send otp.");
//			  throw new UsernameNotFoundException("Unable to send otp");
//			 }
//		}
//		return userModel;

	}
	
	// ---------------------------------------------------------
//	 Set OTP
//	 public boolean sendOTP() {
//	 generateOtp();
//	 OTPModel otpModel = new OTPModel();
//	 otpModel.setOtp(otp);
//	 otpModel.setEmail(userModel.getEmail());
//	 otpModel.setCreatedOn(date);
	
//	 boolean a=emailservices.sendSimpleMessage(userModel.getEmail(),otp);
	
//	 boolean a=true;
//	 if(a) {
//	 otpRepo.save(otpModel);
//	 }
//	 System.out.println(a);
//	 return a;
//	
//	 }
	// //-------------------------------------------------------------------------------
	// //validate
	//
	// public ResponseEntity<?> otpValidate(OTPModel otp) {
	// logger.error("cursor enter in the validate");
	// logger.error("Lets see what happen before:"+otp.getOtp());
	//
	// OTPModel serverotp=otpRepo.findOneByEmailAndOtp(otp.getEmail(),otp.getOtp());
	//
	//
	// logger.error("cursor moves validate"+serverotp.toString());
	//
	// if(serverotp==null)
	// {
	// return new ResponseEntity(new CustomErrorType("OTP doesn't
	// matches"),HttpStatus.NOT_FOUND);
	// }
	// else {
	//
	// if(userModel!=null)
	// {
	// return new ResponseEntity(userModel,HttpStatus.OK);
	// }
	// else {
	// return new ResponseEntity(new CustomErrorType("OTP
	// Expire"),HttpStatus.NOT_FOUND);
	// }
	// }
	//
	//
	//
	// }

	//getDataById
	public UserModel getDataById(Long id) throws UserNotFoundException {
		UserModel model = userrepo.findOneByUserId(id);

		if (model == null) {
			// return new ResponseEntity(new CustomErrorType("User with id " + id + " does
			// not exist"),
			// HttpStatus.NOT_FOUND);
			throw new UserNotFoundException("User with id " + id + " does not exist");
		} else {
			return model;
		}

	}

	//updateDataByid
	public UserModel updateData(Long userId, UserModel userDetails) throws UserNotFoundException {
		UserModel user = userrepo.findOneByUserId(userId);

		if (user == null) {

			throw new UserNotFoundException("User with id " + userId + " does not exist");

		} else {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encryptPass = bCryptPasswordEncoder.encode(userDetails.getPassword());
			user.setUsername(userDetails.getUserName());
			user.setStatus(userDetails.getStatus());
			user.setCountry(userDetails.getCountry());
			user.setPassword(encryptPass);
			user.setPhoneNumber(userDetails.getPhoneNumber());
			UserModel updatedNote = userrepo.save(user);

			return updatedNote;
		}
	}

	//DeleteData
	public UserModel deleteData(Long userId) throws UserNotFoundException {
		UserModel userrow = userrepo.findOneByUserId(userId);

		if (userrow == null) {
			throw new UserNotFoundException("User with id " + userId + " does not exist");
		} else {
			userrepo.delete(userrow);
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<UserModel> user = userrepo.findByEmail(email);
		UserModel userModel = user.get();
		if (user == null || user.equals(null)) {
			throw new UsernameNotFoundException("User " + email + " not found.");
		} else {
			List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

			List<Rolemodel> authStrings = userModel.getRoles();

			for (Rolemodel roles : authStrings) {
	

				logger.error("These roles are assigns for particular user is :: " + roles.getRoleType());

				authorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleType().toString()));
			}
			// for(SimpleGrantedAuthority authority:authorities)
			// {
			// System.out.println(authority.getAuthority());
			// }

			UserDetails ud = new User(userModel.getEmail(), userModel.getPassword(), authorities);
			logger.error("Before return the loadUser functions:: " + userModel.getEmail() + ","
					+ userModel.getPassword() + "," + authorities);
			return ud;
		}

	}

	public List<UserModel> getByPagable(String username, int p) {

		logger.error("services:" + username + " " + p);
		PageRequest page = PageRequest.of(p, 2, Sort.Direction.ASC, "userName");

		return userrepo.findByUserName(username, page);
	}

	public List<UserModel> search(String searchTerm) {

		String likeExpression = "%" + searchTerm + "%";

		return userrepo.searchWithNativeQuery(likeExpression);
	}

}
