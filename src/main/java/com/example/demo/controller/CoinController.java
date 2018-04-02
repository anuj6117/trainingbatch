package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CoinModel;
import com.example.demo.service.CoinService;
import com.example.demo.utils.ApiResponse;

@RestController
@RequestMapping("/coin")
public class CoinController {

	@Autowired
	private CoinService coinService;

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody CoinModel coinModel,HttpServletResponse httpResponse) {
		Boolean res = coinService.addCoin(coinModel);

		if (res) {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,  "failure", null);
		}
	}

	@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllDetail(HttpServletResponse httpResponse) {
		Object obj = coinService.getAllCoinDetail();
		if (obj != "No data found") {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", obj);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, "failure", null);
		}
	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> update(@RequestBody CoinModel coinDetails,HttpServletResponse httpResponse) {
		Object obj = coinService.updateCoin(coinDetails);
		if (obj == "success") {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,"failure", null);
		}
	}

	@RequestMapping(value = "/deletecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> delete(@RequestBody CoinModel coinDetails,HttpServletResponse httpResponse) {
		String obj=coinService.deleteCoin(coinDetails);
		if (obj.equals("success")) {
			return ApiResponse.generateResponse(HttpStatus.OK, true, "success", null);
		} else {
			return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false,"failure", null);
		}
	}

}
