package com.example.demo.controller.userController;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.services.CoinManagementServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class CoinmanagementController {

@Autowired
CoinManagementServices coinData;


	@RequestMapping(value="/addcurrency",method=RequestMethod.POST)
	
	private ResponseEntity<Object> addCoin(@RequestBody CoinManagementModel data)
	{
		Map<String, Object> result = null;
		try
		{
			result=coinData.addAllCoinData(data);
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false,e.getMessage() , result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("result").toString(), result);
		}
	
		
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	private ResponseEntity<Object> updateCoin(@RequestBody CoinManagementModel data)
	{
		Map<String, Object> result = null;
		try
		{
			result=coinData.updataCoinData(data);
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false,e.getMessage() , result);
		}
		System.out.println(result.get("message").toString());
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("result").toString(), result);
		
	}
	@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
	private ResponseEntity<Object> deleteCoin(@RequestParam("id") Long id)
	{
		Map<String, Object> result = null;
		try
		{
			result= coinData.deleteCoinById(id);
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false,e.getMessage() , result);
		}
		System.out.println(result.get("message").toString());
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("result").toString(), result);
	
	}
	@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
	private ResponseEntity<Object> getAllCoin()
	{
	List<CoinManagementModel> result=null;
		try
		{
			result=coinData.findAllCoin();
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false,e.getMessage() , result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}
}
