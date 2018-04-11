package com.crud.demo.conroller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.model.CoinManagement;
import com.crud.demo.service.CoinManagementService;

@RestController
public class CoinManagementController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CoinManagementController.class);
	@Autowired
	private CoinManagementService coinManagementService;

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> addCurrancy(@Validated @RequestBody CoinManagement coinManagement) {
		LOGGER.info("Coin management controller hit");
		Map<String, Object> map = null;
		try {
			map = coinManagementService.addCurreuncy(coinManagement);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));
		}
	}

	
	@RequestMapping("/getcurrencybyid")
	public ResponseEntity<Object> getCurrencyById(@RequestParam Integer coinId) {
		LOGGER.info("Coin management controller hit");
		Map<String, Object> map = null;
		try {
			map = coinManagementService.getCurrencyById(coinId);
			if ((boolean) map.get("isSuccess")) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else

			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}

	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("/getallcurrency")
	public ResponseEntity<Object> getAllCurrency() {
		LOGGER.info("Coin management controller hit");
		Map<String, Object> map = null;
		try {
			map = coinManagementService.getAllCurrency();
			if ((boolean) map.get("isSuccess")) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else

			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}

	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> updateCurrency(@RequestBody CoinManagement coinManagement) {
		LOGGER.info("Coin management controller hit");
		Map<String, Object> map = null;
		try {
			map = coinManagementService.updateCurrency(coinManagement);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
	return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));	
		}
	}

	@RequestMapping("/deletecurrency")
	public ResponseEntity<Object> deleteCurrency(@RequestParam Integer coinId) {
		LOGGER.info("Coin management controller hit");
		Map<String, Object> map = null;
		try {
			map = coinManagementService.deleteCurrency(coinId);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else
			{
	return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}
	}

}
