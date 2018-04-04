package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.constant.RoleEnum;
import com.example.demo.constant.WalletEnum;
import com.example.demo.controller.OTPController;
import com.example.demo.generate.password.BcryptPasswordGenerator;
import com.example.demo.model.AuthToken;
import com.example.demo.model.OrderModel;
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
	
	
	
	
	
	/*public Object searchType() {
		Session session = Hibernate.getHibernateSession();
		Criteria cr = session.createCriteria(UserModel.class);
		long startTimeCriteria = System.nanoTime();
		cr.add(Restrictions.like("itemName", "%item One%"));
		 
		List results = cr.list();

	}*/
	
	
	
	
	/*
	  public SearchResults<UserModel> search(UserSearchCriteria searchCriteria)
	    {
	        UserSearchType searchType = searchCriteria.getSearchType();
	        String sortOrder = searchCriteria.getSortOrder();
	        System.out.println(searchType+":"+sortOrder);
	        List<UserModel> results = null;
	        if(searchType == UserSearchType.BY_NAME)
	        {
	        	
	        }
	        else if(searchType == UserSearchType.BY_ID)
	        {
	           //Use hibernate Criteria API to get and sort results based on USER_ID field in sortOrder
	             results = UserDAO.searchUsers(...);
	        }
	        
	        UserSearchResults<UserModel> searchResults = new UserSearchResults<UserModel>();
	        searchResults.setPageSize(searchCriteria.getPageSize());
	        searchResults.setResults(results);
	        return searchResults;
	    }
	
	
	  public Map<String, Object> search(Map<String, Object> searchCriteriaMap)
	    {
	        return UserDAO.search(searchCriteriaMap);
	    }
	
	*/
	
	
	
	
	
	
	

	// ------------------------SIGN UP---------------------------------
	// Add New User
	// @Secured({"admin","user"})
	public String addUser(UserModel userModel) throws Exception {
        if(userModel.getUserid()==1) {
        	if (!(userModel.getPassword().equalsIgnoreCase(""))) {
				userModel.setCreatedOn(new Date());
				userModel.setPassword(BcryptPasswordGenerator.passwordGenerator(userModel.getPassword()));
				Set<RoleModel> roleModel = addDefaultRole(RoleEnum.ROLE_USER.toString());
				userModel.setRoleType(roleModel);

				userRepo.save(userModel);
				addWallet(userModel);
				Integer otpNum = Utility.generateId(1000);
				authService.addAuthToken(userModel.getUserName(), otpNum);
				System.out.println("-----------------" + sender1.createMimeMessage());
				 SendEmail.sendEmail(userModel.getEmail(), otpNum,
				 userModel.getUserName(),sender1);
				// OTPController.sendSMS(userModel.getPhoneNumber().toString(),otpNum.toString());
				 OTPController.sendSMS("9873020277",otpNum.toString());
				return "success";
			} else {
				System.out.println("----------------------azsxdfghjkl");
				throw new Exception("Password Cannot be null");
			}
        }
        else
        {
        	UserModel user = userRepo.findByEmail(userModel.getEmail());
    		System.out.println(userModel.getUserName()+"--------------------");
    		UserModel username = userRepo.findByUserName(userModel.getUserName());
    		System.out.println(username+"--------------------");
    		UserModel userphone = userRepo.findByPhoneNumber(userModel.getPhoneNumber());
    		if(userModel.getCountry()==""&&userModel.getEmail()==""&&userModel.getPhoneNumber()==null&&userModel.getUserName()=="") {
    			throw new Exception("Field cannot be null");
    			
    		}
    		else {
    			if(userphone == null) {
    				if(username == null) {
    					if (user == null) {
    						if (!(userModel.getPassword().equalsIgnoreCase(""))) {
    							userModel.setCreatedOn(new Date());
    							userModel.setPassword(BcryptPasswordGenerator.passwordGenerator(userModel.getPassword()));
    							Set<RoleModel> roleModel = addDefaultRole(RoleEnum.ROLE_USER.toString());
    							userModel.setRoleType(roleModel);

    							userRepo.save(userModel);
    							addWallet(userModel);
    							Integer otpNum = Utility.generateId(1000);
    							authService.addAuthToken(userModel.getUserName(), otpNum);
    							System.out.println("-----------------" + sender1.createMimeMessage());
    							 SendEmail.sendEmail(userModel.getEmail(), otpNum,
    							 userModel.getUserName(),sender1);
    							// OTPController.sendSMS(userModel.getPhoneNumber().toString(),otpNum.toString());
    							 OTPController.sendSMS("9873020277",otpNum.toString());
    							return "success";
    						} else {
    							System.out.println("----------------------azsxdfghjkl");
    							throw new Exception("Password Cannot be null");
    						}

    					} else {
    						throw new Exception("Email Already Exists");
    					}
    				}
    				else {
    					throw new Exception("UserName Already Exists");
    				}
    			}else {
    				throw new Exception("PhoneNumber Already Exists");
    			}
    		}
        }
		
	}

	// Add Default Wallet For A New User
	public void addWallet(UserModel userModel) {
		WalletModel walletModel = new WalletModel();
		walletModel.setUserIdW(userModel.getUserid());
		walletModel.setUserModel(userModel);
		walletModel.setWalletHash(Utility.generateId(100));
		walletRepo.save(walletModel);
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

	// -----------VERIFY USER ON SIGNUP------------------
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

	// ----------------FOR USER LOGIN------------------------
	// For User Login
	public UserModel getUser(UserModel u) {

		UserModel userOp = userRepo.findByEmailAndPassword(u.getEmail(), BcryptPasswordGenerator.passwordGenerator(u.getPassword()));
		System.out.println(userOp.getEmail() + "-------------------");
		return userOp;
	}
	
	public UserModel getUserById(UserModel u) {

		Optional<UserModel> userOp = userRepo.findById(u.getUserid());
		
		return userOp.get();
	}
	

	// ------------ FETCH ROLE AND USER AND VICE VERSA
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

	// -------------RESEND OTP----------------------
	// Re-send the One time Password
	public void resendOtp(UserModel userModel) throws Exception {
		Integer otp = Utility.generateId(100);
		SendEmail.sendEmail(userModel.getEmail(), otp, userModel.getUserName(), sender1);
		OTPController.sendSMS(userModel.getPhoneNumber().toString(), otp.toString());
	}

	// -------------ADD ANOTHER ROLE FOR USER------------------------------
	public String addAnotherRole(Integer userid, String roleType)throws Exception {

		RoleModel roleOp = roleRepo.findByRole(roleType);
		Optional<UserModel> userData = userRepo.findById(userid);
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
			throw new Exception("Role Does Not Exist");
		}
	}

	// ------------------ADD ANOTHER WALLET FOR USER----------------------
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
	public Object addAnotherWallet(UserModel userModel, String walletType) throws Exception{
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
			else {
				throw new Exception("Wallet already present");
			}
			
			return "Wallet Added Successfully";

		}
		else {
			throw new Exception("Walletype is invalid");
		}

	}

	// -----------------DEPOSIT AND WITHDRAW AMOUNT FORM USER-----------------------
	/*public void addAmountIntoWallet(UserModel userModel, Float amount, String walletType) {
		WalletModel walletModel = walletRepo.findByWalletTypeAndUserIdW(walletType, userModel.getUserid());
		walletModel.setBalance(walletModel.getBalance() + amount);
		walletModel.setShadowBalance(walletModel.getBalance() + amount);
		walletRepo.save(walletModel);
	}*/
	public void addAmountIntoWallet1(Integer userid, Float amount, String walletType) {
		WalletModel walletModel = walletRepo.findByWalletTypeAndUserIdW(walletType, userid);
		walletModel.setBalance(walletModel.getBalance() + amount);
		walletModel.setShadowBalance(walletModel.getBalance());
		walletRepo.save(walletModel);
	}

	public void withdrawAmountFromWallet(Integer userid, Float amount, String walletType) {
		WalletModel walletModel = walletRepo.findByWalletTypeAndUserIdW(walletType,userid);
		if (amount < walletModel.getBalance()) {
			walletModel.setBalance(walletModel.getBalance() - amount);
			walletModel.setShadowBalance(walletModel.getBalance());
			walletRepo.save(walletModel);//testing 
		}
	}

	// ---------------------GET LIST OF ALL USERS----------------------
	public Object getAllDetails() {
		List<UserModel> userDetails = new ArrayList<UserModel>();
		userRepo.findAll().forEach(userDetails::add);
		if (userDetails != null) {
			return userDetails;
		} else {
			return "No Data Found";
		}
	}

	// -------------DELETE USER------------------------------
	public void deleteUser(int id) {

		userRepo.deleteById(id);
	}

	// ----------------------UPDATE USER-----------------
	public List<UserModel> updateUser(int userId, UserModel u) {
		Optional<UserModel> userOp = userRepo.findById(userId);
		System.out.println(userOp + "---------------------------------------");
		if(!(userOp.get().equals(null))) {
			userOp.get().setUserName(u.getUserName());
			userOp.get().setCountry(u.getCountry());
			userOp.get().setEmail(u.getEmail());
			userOp.get().setPhoneNumber(u.getPhoneNumber());
			userOp.get().setPassword(u.getPassword());
			userRepo.save(userOp.get());
		}
		
	
		List<UserModel> userDetails = new ArrayList<UserModel>();
		userRepo.findAll().forEach(userDetails::add);
		return userDetails;
	}

	// --------------------PAGINATION METHODS/LIKE METHODS----------------

	public List<UserModel> findAllPagination(PageRequest pageRequest) {
		return userRepo.findAll(pageRequest);
	}

	public List<UserModel> findByName(String userName, PageRequest pageRequest) {
		return userRepo.findByUserName(userName, pageRequest);
	}

	public List<UserModel> findByNameContaininy(String userName,PageRequest pageRequest) {
		return userRepo.findByUserNameContaining(userName,pageRequest);
	}

	// -----SPRING SECURITY METHOD--------------------------------------
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
