package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.constant.RoleEnum;
import com.example.demo.constant.WalletEnum;
import com.example.demo.controller.OTPController;
import com.example.demo.generate.password.BcryptPasswordGenerator;
import com.example.demo.model.AuthToken;
import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WalletModel;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utils.SendEmail;
import com.example.demo.utils.Utility;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private WalletRepository walletRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AuthTokenService authService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthRepository authRepo;

	@Autowired
	private JavaMailSender sender1;

	// Add Default Wallet For A New User
	public void addWallet(UserModel userModel) {
		WalletModel walletModel = new WalletModel();
		walletModel.setUserIdW(userModel.getUserid());
		walletModel.setUserModel(userModel);
		walletModel.setWalletHash(Utility.generateId(100));
		walletRepo.save(walletModel);
	}

	// Add New User
	// @Secured({"admin","user"})
	public String addUser(UserModel userModel) throws Exception {

		UserModel user = userRepo.findByEmail(userModel.getEmail());
		if (user == null) {
			userModel.setCreatedOn(new Date());
			userModel.setPassword(BcryptPasswordGenerator.passwordGenerator(userModel.getPassword()));
			Set<RoleModel> roleModel = addDefaultRole(RoleEnum.ROLE_USER.toString());
			userModel.setRoleType(roleModel);
			userRepo.save(userModel);
			addWallet(userModel);
			Integer otpNum = Utility.generateId(1000);
			authService.addAuthToken(userModel.getUserName(), otpNum);
			System.out.println("-----------------" + sender1.createMimeMessage());
			// SendEmail.sendEmail(userModel.getEmail(), otpNum,
			// userModel.getUserName(),sender1);
			// OTPController.sendSMS(userModel.getMobile().toString(),otpNum.toString());
			return "success";
		} else {
			return "Email Already Exists";
		}

	}

	// verify user
	public String verifyUser(UserModel userModel, Integer otp) {
		Optional<UserModel> userOp = userRepo.findById(userModel.getUserid());
		if (userOp.get() != null) {
			AuthToken authToken = authRepo.findByOtp(otp);
			if (authToken != null) {
				userOp.get().setStatus(true);
				userRepo.save(userOp.get());
				authRepo.deleteById(authToken.getTokenId());
				return "Success";
			} else {
				// re-send the One time password
				return "not a valid otp";
			}
		} else {
			// sign up first
			return "user does not exist";
		}

	}

	// For User Login
	public UserModel getUser(UserModel u) {
		UserModel userOp = userRepo.findByEmailAndPassword(u.getEmail(), u.getPassword());
		return userOp;
	}

	// fetch the roles
	public Set<RoleModel> fetchRole(UserModel userModel) {
		Optional<UserModel> userData = userRepo.findById(userModel.getUserid());
		UserModel user = userData.get();
		Set<RoleModel> role = user.getRoleType();
		return role;

	}

	// fetch the user basis on role
	public Set<UserModel> fetchUser(RoleModel role) {
		Iterable<UserModel> userData = userRepo.findAll();
		Iterator<UserModel> userIterator = userData.iterator();
		Set<UserModel> userModelList = new HashSet<UserModel>();
		while (userIterator.hasNext()) {
			UserModel userModel = userIterator.next();
			Set<RoleModel> roles = userModel.getRoleType();
			for (RoleModel type : roles) {
				if (type.getRole().equalsIgnoreCase(role.getRole())) {
					userModelList.add(userModel);
				}
			}
		}
		return userModelList;
	}

	// Re-send the One time Password
	public void resendOtp(UserModel userModel) throws Exception {
		Integer otp = Utility.generateId(100);
		SendEmail.sendEmail(userModel.getEmail(), otp, userModel.getUserName(), sender1);
		OTPController.sendSMS(userModel.getPhoneNumber().toString(), otp.toString());
	}

	// Add Default Role For A New User
	public Set<RoleModel> addDefaultRole(String role) {
		roleService.addRole();
		RoleModel roleModel = roleRepo.findByRole(role);
		Set<RoleModel> roleSet = new HashSet<RoleModel>();
		if (roleModel != null) {
			roleSet.add(roleModel);
			return roleSet;
		} else {

			return null;
		}
	}

	// Check Role is Valid or Not.
	public Boolean isRoleTypeValid(String roleType) {
		int flag = 0;
		for (RoleEnum type : RoleEnum.values()) {
			if (roleType.equalsIgnoreCase(type.toString())) {
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			return true;
		} else {
			return false;
		}

	}

	public String addAnotherRole(UserModel user, String roleType) {

		RoleModel roleOp = roleRepo.findByRole(roleType);
		Optional<UserModel> userData = userRepo.findById(user.getUserid());
		UserModel userModel = userData.get();

		if (roleOp != null) {
			Set<RoleModel> role = userModel.getRoleType();
			role.add(roleOp);
			userModel.setRoleType(role);
			userModel.setCountry(userModel.getCountry());
			userModel.setCreatedOn(userModel.getCreatedOn());
			userModel.setEmail(userModel.getEmail());
			userModel.setStatus(userModel.getStatus());
			userModel.setUserName(userModel.getUserName());
			userModel.setPhoneNumber(userModel.getPhoneNumber());
			userModel.setPassword(userModel.getPassword());
			userRepo.save(userModel);
			return "success";
		} else {
			return "unsuccessssfukl";
		}
	}

	// Check WalletType is Valid or Not.
	public Boolean isWalletTypeValid(String walletType) {
		int flag = 0;
		for (WalletEnum type : WalletEnum.values()) {
			if (walletType.equalsIgnoreCase(type.toString())) {
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			return true;
		} else {
			return false;
		}

	}

	// Add Another For a Existing User wallet
	public void addAnotherWallet(UserModel userModel, String walletType) {
		Boolean check = isWalletTypeValid(walletType);
		if (check) {

			int flag = 0;
			List<WalletModel> walletOp = walletRepo.findAllByUserIdW(userModel.getUserid());
			for (WalletModel type : walletOp) {
				if (type.getWalletType().equalsIgnoreCase(walletType)) {
					System.out.println(type.getWalletType());
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				WalletModel walletModel = new WalletModel();
				walletModel.setUserIdW(userModel.getUserid());
				walletModel.setUserModel(userModel);
				walletModel.setWalletHash(Utility.generateId(100));
				walletModel.setWalletType(walletType);
				walletRepo.save(walletModel);
			}

		}

	}

	public void addAmountIntoWallet(UserModel userModel, Float amount, String walletType) {
		WalletModel walletModel = walletRepo.findByWalletTypeAndUserIdW(walletType, userModel.getUserid());
		walletModel.setBalance(walletModel.getBalance() + amount);
		walletModel.setShadowBalance(walletModel.getBalance() + amount);
		walletRepo.save(walletModel);
	}

	public void withdrawAmountFromWallet(UserModel userModel, Float amount, String walletType) {
		WalletModel walletModel = walletRepo.findByWalletTypeAndUserIdW(walletType, userModel.getUserid());
		if (amount < walletModel.getBalance()) {
			walletModel.setBalance(walletModel.getBalance() - amount);
			walletModel.setShadowBalance(walletModel.getBalance() - amount);
			walletRepo.save(walletModel);
		}
	}

	/*
	 * public List<UserModel> getAllDetails() { List<UserModel> userDetails = new
	 * ArrayList<UserModel>(); userRepo.findAll().forEach(userDetails::add); return
	 * userDetails; }
	 */
	public Object getAllDetails() {
		List<UserModel> userDetails = new ArrayList<UserModel>();
		userRepo.findAll().forEach(userDetails::add);
		if (userDetails != null) {
			return userDetails;
		} else {
			return "No Data Found";
		}
	}

	public void deleteUser(int id) {
		userRepo.deleteById(id);
	}

	public List<UserModel> updateUser(int userId, UserModel u) {
		Optional<UserModel> userOp = userRepo.findById(userId);
		System.out.println(userOp + "---------------------------------------");
		userOp.get().setUserName(u.getUserName());
		userOp.get().setCountry(u.getCountry());
		userOp.get().setEmail(u.getEmail());
		userOp.get().setPhoneNumber(u.getPhoneNumber());
		userOp.get().setPassword(u.getPassword());
		userRepo.save(userOp.get());
		List<UserModel> userDetails = new ArrayList<UserModel>();
		userRepo.findAll().forEach(userDetails::add);
		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userRepo.findByEmail(username);

		if (userModel == null) {
			throw new UsernameNotFoundException("User email " + username + " not found.");
		} else {
			List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			int i = 0;
			Set<RoleModel> role = userModel.getRoleType();
			String[] authStrings = new String[role.size()];
			for (RoleModel type : role) {
				authStrings[i] = type.getRole();
				i++;
			}
			for (String authString : authStrings) {
				System.out.println(authString + "--------------authString----------------");
				authorities.add(new SimpleGrantedAuthority(authString));
			}

			System.out.println(userModel.getEmail());
			System.out.println(role + "---------------------------");
			User user = new User(userModel.getEmail(), userModel.getPassword(), authorities);

			UserDetails userDetails = (UserDetails) user;
			return userDetails;

		}

	}

}
