package com.example.adarsh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.domain.CoinManagement;
import com.example.adarsh.service.CoinManagementService;

@RestController
public class CoinManagementController {

	@Autowired
	private CoinManagementService coinManagementService;

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)

	private ResponseEntity<Object> addCoin(@RequestBody CoinManagement data) {
		CoinManagement result = null;
		try {
			result = coinManagementService.addAllCoinData(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);

	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	private ResponseEntity<Object> updateCoin(@RequestBody CoinManagement data) {
		Map<String, Object> result = null;
		try {
			result = coinManagementService.updataCoinData(data);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "failure", result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}

	@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
	private ResponseEntity<Object> deleteCoin(@RequestParam("id") Long id) {
		Map<String, Object> result = null;
		try {
			result = coinManagementService.deleteCoinById(id);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "failure", result);

		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "", result);
	}

	@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
	private ResponseEntity<Object> getAllCoin() {
		List<CoinManagement> obj = null;
		try {
			obj = coinManagementService.findAllCoin();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, e.getMessage(), obj);
		}

		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Table found suceessfuly", obj);

	}

	@RequestMapping(value = "/getcurrencybyid", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@RequestParam("id") Long coinId) {
		CoinManagement obj = null;
		try

		{
			obj = coinManagementService.getCoinById(coinId);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, e.getMessage(), obj);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Data Search suceessfuly", obj);
	}
}
