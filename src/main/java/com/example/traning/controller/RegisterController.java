package com.example.traning.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Register;
import com.example.traning.service.RegisterService;
import com.example.traning.utill.CustomExceptionhandler;

@RestController
//@RequestMapping("/tranapi")
public class RegisterController {
	
	@Autowired
	RegisterService registerservice;
	
	//========================CURD OPERATIONS==========================
	
	 //final all data 
    @RequestMapping(value="/getallusers",method=RequestMethod.GET)
    public ResponseEntity<Object> getAllCoin(){
    	List<Register> obj=null;
  		try {
  			obj= registerservice.getAllUse();
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Table found suceessfuly", obj);
 
    }
	
	
	

	
	 //*******************************************************************************************
  

	//	@PreAuthorize("hasAnyRole('ADMIN')")
    //get all data by name
    @RequestMapping(value="/getallusersbyname",method=RequestMethod.GET)
	public ResponseEntity<Object> show(String userName,int page) {
    	List<Register> obj=null;
  		try {
  			obj= registerservice.getAllUsers(userName,page);
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Table found suceessfuly", obj);
    	
    	
    }
     
    
    
    //*******************************************************************************************
   
    
    //save all data
    @RequestMapping(value="/signup",method=RequestMethod.POST)
	public ResponseEntity<Object> saveuser(@Validated @RequestBody Register register) {
    	Register obj=null;
    	if(register.getUserName().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Username can't be null",obj);
    	}
    	else if(register.getEmail().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Email can't be null",obj);
    	}
    	else if(register.getPassword().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Password can't be null",obj);
    	}
    	else if(register.getphoneNumber().isEmpty())
    	{
      		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "PhoneNumber should be in 10 digit",obj);
    	}
    	else if(register.getCountry().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "country can't be null",obj);
    	}
    	else {
    	 
    	try {
    	obj= registerservice.createUser(register);
            }
    	catch(Exception e) {
    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
    	}
    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
    	}
      }

    
    
    //*******************************************************************************************
    
    
    
    //get single data by id
  	@RequestMapping(value="/getbyuserid",method=RequestMethod.GET)
  	public ResponseEntity<Object> getById(@RequestParam(value="userId") Long userId) {
  		Register obj=null;
  		try {
  	    	obj= registerservice.getUserById(userId);
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Data Search  suceessfuly", obj);
  	    	
  	}
    
    
    //***********************************************************************************
 /*   
  //update data
  	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
  	public ResponseEntity<?> update() {
  		Register obj=null;
  		return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"ID Cant be null", obj);
  	}
  	
  	*/
  	
   @RequestMapping(value="/updateuser",method=RequestMethod.POST)
   public ResponseEntity<?> updatedata(@RequestParam(value = "userId")  Long userId,
           @Valid @RequestBody Register RegDetails) {
	   Register obj=null;
	   System.out.println("hello");
	   try {
	    	obj= registerservice.UpdateUser(userId,RegDetails);
	        }
	    	catch(Exception e) {
	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
	    	}
	   return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Update suceessfuly", obj);
	   
   }
   
   
   //*******************************************************************************************
   
   
    //delete data
    @RequestMapping(value="/deleteuser",method=RequestMethod.GET)
    public String deletedata(@RequestParam(value = "userId") Long userId) {
	  if(userId!=null)
    	return registerservice.deleteUser(userId);
	  else
		  return "id is not exist";
    }
   
    //*******************************************************************************************  
    
    
 //========================CURD OPERATIONS==========================
    
    
    
    @RequestMapping(value="/sample1",method=RequestMethod.GET)
    public String sample1()
    {
    	return "hello I am sample1";
    }
    @RequestMapping(value="/sample2",method=RequestMethod.GET)
    public String sample2()
    {
    	return "hello I am sample2";
    }
    @RequestMapping(value="/sample3",method=RequestMethod.GET)
    public String sample3()
    {
    	return "hello I am sample3";
    }
    
    
    
    
    
    
    
    
}