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
		
		
//		if(model.getCoinName().isEmpty()) {
//			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinName can't be empty", obj);
//		}
//		else if(model.getSymbol().isEmpty()) {
//			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "CoinSymbol can't be empty", obj);
//		}
//		else {
			try {
				obj = services.addCoin(model);
			} catch (CoinNotFoundException e) {
				
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
			return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Created Successfully", obj);
//		}
		
		
		
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
	@RequestMapping(value = "/getbycurrencyid/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id) {
		Coinmodel obj = null;

		try {
			obj = services.getDataById(id);
		} catch (CoinNotFoundException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}
	
	//updateCoinApi
	@RequestMapping(value="/updatecurrency/{id}",method=RequestMethod.POST)
	public ResponseEntity<Object> updatecoin(@PathVariable("id") Long coinid ,@RequestBody Coinmodel model) {
		Coinmodel obj = null;
		try {
			obj = services.updateCoin(coinid,model);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Updated Successfully", obj);

	}
	
	//deleteCoinApi
	@RequestMapping(value="/deletecurrency/{id}",method=RequestMethod.GET)
	public ResponseEntity<Object> delete(@PathVariable(value="id") Long id){
		Coinmodel obj = null;
		try {
			obj = services.deleteCoin(id);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Deleted Successfully", obj);

	}
	

}
