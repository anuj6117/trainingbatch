package com.example.traning.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Coin;
import com.example.traning.service.CoinService;
import com.example.traning.utill.CustomExceptionhandler;

@RestController
//@RequestMapping("/tranapi")
public class CoinController {

	
	@Autowired
	CoinService coinservice;

	//================Curds Operations==========================
	
	//save all data
    @RequestMapping(value="/addcurrency",method=RequestMethod.POST)
	public ResponseEntity<Object> savecoin(@Valid @RequestBody Coin coin) {
    	
    	Coin obj=null;
    	if(coin.getCoinName().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinName can't be null",obj);
    	}
    	else if(coin.getInitialSupply()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Coin Initial Supply can't be null",obj);
    	}
    	else if(coin.getSymbol().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinSymbol can't be null",obj);
    	}
    	else if(coin.getPrice()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinPrice can't be null",obj);
    	}
    	else {
    	try {
    	obj= coinservice.createcoin(coin);
    	
            }
    	catch(Exception e) {
    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
    	}
    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
    	}
    }
    
    
    
  //*******************************************************************************************
    //get single data by id
  	@RequestMapping(value="/getcurrencybyid",method=RequestMethod.GET)
  	public ResponseEntity<Object> getById(@RequestParam(value="coinId") Long coinId) {
  		Coin obj=null;
  		try {
  	    	obj= coinservice.getDataById(coinId);
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Data Search  suceessfuly", obj);
  	}
  	
  	
  	
  	
  //*******************************************************************************************
  	
    //final all data 
    @RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
    
    public ResponseEntity<Object> getAllCoin(){
    	List<Coin> obj=null;
  		try {
  			obj= coinservice.getAllCoins();
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Table found suceessfuly", obj);
 
    }
    
    
    
    
  //*******************************************************************************************
    //update coin
    @RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
    public ResponseEntity<Object> updatecoin(@RequestParam(value = "coinId") Long coinId,
            @Valid @RequestBody Coin coin) {
    	Coin obj=null;
    	if(coin.getCoinName().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinName can't be null",obj);
    	}
    	else if(coin.getInitialSupply()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Coin Initial Supply can't be null",obj);
    	}
    	else if(coin.getSymbol().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinSymbol can't be null",obj);
    	}
    	else if(coin.getPrice()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinPrice can't be null",obj);
    	}
    	else {
 	   try {
 	    	obj= coinservice.updatecoin(coinId,coin);
 	        }
 	    	catch(Exception e) {
 	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
 	    	}
 	   return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Update suceessfuly", obj);
 	   
    }
    	}
    
    
    
  //*******************************************************************************************
    //delete coin
    @RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
    public ResponseEntity<Object> deleteCoin(@RequestParam(value = "coinId") Long coinId) {
    	String obj=null;
  	   try {
  		   System.out.println("<===============>");
  	    	obj=coinservice.deletecoin(coinId);
  	        }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	   return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Deleted Succesfully", obj);
    }
  //================Curds Operations==========================

}
