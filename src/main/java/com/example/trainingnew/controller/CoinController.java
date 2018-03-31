package com.example.trainingnew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.exception.CoinNotFoundException;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.services.CoinServices;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class CoinController {
	
	@Autowired
	CoinServices services;
	
	//----------------------------------------------------------------------------------create coin api
	@RequestMapping(value="/addcoin",method=RequestMethod.POST)
	public ResponseEntity<Object> addcoin(@RequestBody Coinmodel model) {
		Coinmodel obj = null;
		try {
			obj = services.addCoin(model);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Created Successfully", obj);
 
	}
	
	//----------------------------------------------------------------------------------get all coins api
	@RequestMapping(value="/getallcoin",method=RequestMethod.GET)
	public ResponseEntity<Object> showCoin() {
		List<Coinmodel>obj = null;
		try {
			obj = services.showAllCoins();
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Details Successfully", obj);
		
	}
	
	//--------------------------------------------------------------------------------------update coin api
	@RequestMapping(value="/updatecoin/{id}",method=RequestMethod.POST)
	public ResponseEntity<Object> updatecoin(@PathVariable("id") Long coinid ,@RequestBody Coinmodel model) {
		Coinmodel obj = null;
		try {
			obj = services.updateCoin(coinid,model);
		} catch (CoinNotFoundException e) {
			
			return ExceptionHandler.generateResponse(HttpStatus.NOT_FOUND, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true,"Coin Updated Successfully", obj);

	}
	
	//----------------------------------------------------------------------------------------delete coin api
	@RequestMapping(value="/deletecoin/{id}",method=RequestMethod.DELETE)
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
