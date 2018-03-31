package com.example.trainingnew.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.trainingnew.exception.ResourcesNotFoundException;
import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.OTPModel;
import com.example.trainingnew.model.Rolemodel;
import com.example.trainingnew.model.Usermodel;
import com.example.trainingnew.reprository.OTPRepo;
import com.example.trainingnew.reprository.RoleRepo;
import com.example.trainingnew.reprository.UserRepo;
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

	public int generateOtp() {

		Random random = new Random();
		otp = 1000 + random.nextInt(9000);

		return otp;
	}

	// getallServices
	@SuppressWarnings("unchecked")
	public ResponseEntity<List<Usermodel>> getAllData() {
		List<Usermodel> find = userrepo.findAll();

		if (find.isEmpty()) {
			return (new ResponseEntity(new CustomErrorType("No any data exist"), HttpStatus.NOT_FOUND));
		}

		else {
			return new ResponseEntity<List<Usermodel>>(find, HttpStatus.OK);
		}

	}

	// ----------------------------------------------------------------------------------------
	// addData
	public Usermodel createData(Usermodel note) throws UserNotFoundException {

		Usermodel usermodel = new Usermodel();
		Rolemodel rolemodel = rolerepo.findOneByRole("user");

		Usermodel umodel = userrepo.findOneByEmail(note.getEmail());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(note.getPassword());

		if (umodel != null) {

			logger.error("uanable to create new user with {} email", note.getEmail());

			throw new UserNotFoundException("This User alredy exist");
		}

		else {
			usermodel.setUsername(note.getUserName());
			usermodel.setEmail(note.getEmail());
			usermodel.setStatus(true);
			usermodel.setPassword(hashedPassword);
			usermodel.setPhoneNumber(note.getPhoneNumber());
			usermodel.setCountry(note.getCountry());
			usermodel.setCreatedOn(date);
			usermodel.getRoles().add(rolemodel);

			// if(sendOTP()) {
			userrepo.save(usermodel);

			logger.error("after send otp cursor come save the data on usertable.");
			// }
			// else {
			// logger.error("Sorry!unable to send otp.");
			// return new ResponseEntity(new CustomErrorType("Sorry! getting problem to send
			// otp"),HttpStatus.BAD_GATEWAY);
			// }
		}
		return usermodel;
		// return new ResponseEntity<String>("Otp Has Been sent on you number please
		// verify it.", HttpStatus.OK);

	}
	// ---------------------------------------------------------
	// Set OTP
	// public boolean sendOTP() {
	// generateOtp();
	// OTPModel otpModel = new OTPModel();
	// otpModel.setOtp(otp);
	// otpModel.setEmail(usermodel.getEmail());
	// otpModel.setCreatedOn(date);
	//
	// // boolean a=emailservices.sendSimpleMessage(usermodel.getEmail(),otp);
	//
	// boolean a=true;
	// if(a) {
	// otpRepo.save(otpModel);
	// }
	// System.out.println(a);
	// return a;
	//
	// }
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
	// if(usermodel!=null)
	// {
	// return new ResponseEntity(usermodel,HttpStatus.OK);
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

	// --------------------------------------------------------------------------------------
	// getDataById
	public Usermodel getDataById(Long id) throws UserNotFoundException {
		Usermodel model = userrepo.findOneByUserId(id);

		if (model == null) {
			// return new ResponseEntity(new CustomErrorType("User with id " + id + " does
			// not exist"),
			// HttpStatus.NOT_FOUND);
			throw new UserNotFoundException("User with id " + id + " does not exist");
		} else {
			return model;
		}

	}

	// ------------------------------------------------------------------------------------
	// updateDataByid
	public Usermodel updateData(Long userId, Usermodel userDetails) throws UserNotFoundException {
		Usermodel user = userrepo.findOneByUserId(userId);

		if (user == null) {

			throw new UserNotFoundException("User with id " + userId + " does not exist");

		} else {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encryptPass = bCryptPasswordEncoder.encode(userDetails.getPassword());
			user.setUsername(userDetails.getUserName());
			user.setEmail(userDetails.getEmail());
			user.setStatus(userDetails.getStatus());
			user.setPassword(encryptPass);
			user.setPhoneNumber(userDetails.getPhoneNumber());
			Usermodel updatedNote = userrepo.save(user);

			return updatedNote;
		}
	}

	// ----------------------------------------------------------------------------------------
	// DeleteData
	public Usermodel deleteData(Long userId) throws UserNotFoundException {
		Usermodel userrow = userrepo.findOneByUserId(userId);

		if (userrow == null) {
			throw new UserNotFoundException("User with id " + userId + " does not exist");
		} else {
			userrepo.delete(userrow);
			return null;
		}
	}

	// ===========================================================================================
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Usermodel> user = userrepo.findByEmail(email);
		Usermodel usermodel = user.get();
		if (user == null || user.equals(null)) {
			throw new UsernameNotFoundException("User " + email + " not found.");
		} else {
			List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

			List<Rolemodel> authStrings = usermodel.getRoles();

			for (Rolemodel roles : authStrings) {
				System.out.println("These roles are assigns for particular user " + roles.getRole().toString());

				logger.error("These roles are assigns for particular user is :: " + roles.getRole());

				authorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRole().toString()));
			}
			// for(SimpleGrantedAuthority authority:authorities)
			// {
			// System.out.println(authority.getAuthority());
			// }

			UserDetails ud = new User(usermodel.getEmail(), usermodel.getPassword(), authorities);
			logger.error("Before return the loadUser functions:: " + usermodel.getEmail() + ","
					+ usermodel.getPassword() + "," + authorities);
			return ud;
		}

	}

}
