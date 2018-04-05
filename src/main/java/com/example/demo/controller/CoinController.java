package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CoinModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.CoinService;
import com.example.demo.utils.ApiResponse;

@RestController
//@RequestMapping("/coin")
public class CoinController {

	@Autowired
	private CoinService coinService;

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody CoinModel coinModel) {
		
		Object response = "";
		try {
			response = coinService.addCoin(coinModel);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
	}

	@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllDetail() {
		 
		
		Object response ;
		try {
			response = coinService.getAllCoinDetail();
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", response);
		
	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> update(@RequestBody CoinModel coinDetails) {
		Object obj = coinService.updateCoin(coinDetails);
		if (obj == "success") {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,"failure", null);
		}
	}

	@RequestMapping(value = "/deletecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> delete(@RequestParam(value = "coinId") Integer coinId) {
		String obj=coinService.deleteCoin(coinId);
		if (obj.equals("success")) {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,"failure", null);
		}
	}
	
	@RequestMapping(value = "/getcurrencybyid", method = RequestMethod.POST, produces = { "application/JSON" })
	public ResponseEntity<Object> add1(@RequestParam(value = "coinId") Integer coinId) throws Exception {
		Object response = "";
		try {
			response = coinService.getCoinById(coinId);
		} catch (Exception e) {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,"false", null);
		}
		return ApiResponse.generateResponse(HttpStatus.OK, true, "success", response);

	}

}
