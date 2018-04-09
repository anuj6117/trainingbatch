package com.example.trainingnew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.exception.CoinNotFoundException;
import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.services.CoinServices;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class CoinController {
	
	@Autowired
	CoinServices services;
	
	//createCoinApi
	@RequestMapping(value="/addcurrency",method=RequestMethod.POST)
	public ResponseEntity<Object> addcoin(@Validated @RequestBody Coinmodel model) {
		Coinmodel obj = null;
		
		 if(model.getInitialSupply()==0) {
			 return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Intial Supply can't be zero", obj);
		 }
		 else if(model.getPrice()==0) {
			 return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "price can't be zero", obj);
		 }
		 else {
	
			try {
				obj = services.addCoin(model);
			} catch (CoinNotFoundException e) {
				
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
		 }
			return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Created Successfully", obj);
	}
	
	//getAllCoinsApi
	@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
	public ResponseEntity<Object> showCoin() {
		List<Coinmodel>obj = null;
		try {
			obj = services.showAllCoins();
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Details Successfully", obj);
		
	}
	
	
	//getCurrencyById
	@RequestMapping(value = "/getcurrencybyid/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@PathVariable(value = "id") Integer id) {
		Coinmodel obj = null;

		try {
			obj = services.getDataById(id);
		} catch (CoinNotFoundException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}
	
	//updateCoinApi
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	public ResponseEntity<Object> updatecoin(@RequestBody Coinmodel model) {
		Coinmodel obj = null;
		try {
			obj = services.updateCoin(model);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Updated Successfully", obj);

	}
	
	//deleteCoinApi
	@RequestMapping(value="/deletecurrency/{id}",method=RequestMethod.GET)
	public ResponseEntity<Object> delete(@PathVariable(value="id") Integer id){
		Coinmodel obj = null;
		try {
			obj = services.deleteCoin(id);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Deleted Successfully", obj);

	}
	

}
