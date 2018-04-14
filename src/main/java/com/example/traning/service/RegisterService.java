package com.example.traning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Otp;
import com.example.traning.domain.Register;
import com.example.traning.domain.Role;
import com.example.traning.domain.Wallet;
import com.example.traning.exception.UserNotFoundException;
import com.example.traning.repository.OtpRepository;
import com.example.traning.repository.RegisterRepository;
import com.example.traning.repository.RoleRepository;
import com.example.traning.repository.WalletRepository;
@Service
public class RegisterService implements UserDetailsService {
	
	@Autowired
	RegisterRepository registerrepository;
	
	@Autowired
	RoleRepository rolerepository;
	
	@Autowired
	OtpRepository otprepository;
	
	@Autowired
	EmailService emailservice;
	
	@Autowired
	WalletRepository walletrepository;

	
	
	//========================OPERATIONS==========================
	String body = "hi your otp is";
	Random random = new Random();
	int otp = 100000 + random.nextInt(900000);
	
	
	public List<Register> getAllUse() {
		List<Register> lcoin= registerrepository.findAll();
		if(lcoin.isEmpty()) {
			throw new NullPointerException("Table is null");
		}else {
			return lcoin;
		}
	}
		
	
	 //*******************************************************************************************
	

	public List<Register> getAllUsers(String userName,int page){	
		PageRequest request = PageRequest.of(page,10, Sort.Direction.ASC, "userName");
		List<Register> list= registerrepository.findByUserName(userName,request);
		if(list.isEmpty()) {
			throw new NullPointerException("Table is null");
		}else {
			return list;
		}
	}
	
	
	 //*******************************************************************************************
	
	public Register getUserById(Long userId) {
		 Register greg= registerrepository.findByUserId(userId);
		 if(greg!=null)
		 {
			return greg; 
		 }
		 else {
			 throw new NullPointerException("Id doen't be exist");
		 }
	}
	
	
	
	 //*******************************************************************************************
	
	
	public Register createUser(Register register) throws UserNotFoundException {
		Register usermodel =new Register();   
		Role rolemodel=rolerepository.findByRoleType("user");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(register.getPassword());
		
		Register reg=registerrepository.findByEmail(register.getEmail());
       
      
        if(reg!=null)
     {
    	   throw new UserNotFoundException("this user already exist");
    	
     }
		else {
			
		usermodel.setUserName(register.getUserName());
		usermodel.setEmail(register.getEmail());
		usermodel.setStatus(false);
		usermodel.setPassword(hashedPassword);
		usermodel.setPhoneNo(register.getphoneNumber());
		usermodel.setCountry(register.getCountry());
		usermodel.setCreatedOn(register.getCreatedOn());
		usermodel.getRoles().add(rolemodel);
		
		//registerrepository.save(usermodel);
		
		
		
				
		
		
		
		
		Wallet wall=new Wallet();
		
		wall.setAmount(wall.getAmount());
		wall.setShadowBalance(wall.getShadowBalance());
		wall.setWalletType(wall.getWalletType());
		System.out.println("**************************");
		wall.setRegister(usermodel);
		
		usermodel.getWall().add(wall);
//		System.out.println(usermodel.getWall().add(wall));
	//	walletrepository.save(wall);
		registerrepository.save(usermodel);
		
		
		Otp Gotp=new Otp();
		Gotp.setTokenOTP(otp);
		Gotp.setEmail(register.getEmail());
		otprepository.save(Gotp);
		
		emailservice.sendSimpleMessage(register.getEmail(),otp);
		System.out.println(register.getEmail()+"  what otp is gone: "+otp);
		} 
	    return usermodel;
	}
	
	//*******************************************************************************************
	
	public Register UpdateUser(Long userId,
             Register RegDetails) {

    Register note = registerrepository.findByUserId(userId);
    if(note!=null)
    {
       note.setUserName(RegDetails.getUserName());
       note.setEmail(RegDetails.getEmail());
       note.setPassword(RegDetails.getPassword());
       note.setStatus(RegDetails.getStatus());
       note.setCountry(RegDetails.getCountry());
       note.setPhoneNo(RegDetails.getphoneNumber());

     Register updatedNote = registerrepository.save(note);
     
     return updatedNote;
     }
    else {
    	throw new NullPointerException("Id doen't be exist");
    }
     }
	 //*******************************************************************************************
	
	
	public String deleteUser( Long userId) {
	    Register note = registerrepository.findByUserId(userId);  
	    if(note!=null) {
	    registerrepository.delete(note);
	    return "succesfully delete "+note.getuserId()+" "+note.getUserName();
	    
	    }
	    else {
	    return "User Doesnt exist";
	}
	    }


	//========================OPERATIONS==========================
	
	
//==================Security=====================================
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Register reg=registerrepository.findByUserName(userName);
		if(reg==null) {
			throw new UsernameNotFoundException("User " + userName + " not found.");
		}
			 List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		     List<Role> authStrings = reg.getRoles();
		     
		     for(Role authString:authStrings)
		     {
		    	 authorities.add(new SimpleGrantedAuthority("ROLE_"+authString.getRoleType()));
		     }
		     UserDetails ud = new User(reg.getUserName(), reg.getPassword(), authorities);
		        return ud;
	}

	
	
	}
	
