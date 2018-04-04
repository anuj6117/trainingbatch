package com.crud.demo.conroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.dto.UserWalletDTO;
/*import com.crud.demo.dto.UserWalletDTO;*/
import com.crud.demo.model.User;
import com.crud.demo.service.UserWalletService;

@RestController
public class UserWalletController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWalletController.class);
	@Autowired
	private UserWalletService userWalletService;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public ResponseEntity<Object> createWallet(@RequestBody UserWalletDTO userWalletDTO) {
		LOGGER.info("Message on UserWalletController :::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = userWalletService.createWallet(userWalletDTO);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}

	}

	@RequestMapping(value = "/withdrawamount", method = RequestMethod.POST)
	public ResponseEntity<Object> withdrawAmount(@RequestBody UserWalletDTO userWalletDTO) {
		LOGGER.info("Message on UserWalletController :::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = userWalletService.withdrawAmount(userWalletDTO);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}
	}

	/*
	 * @RequestMapping("/withdrawamount") public String withdrawAmount(@RequestBody
	 * UserWalletDTO userWalletDTO){
	 * 
	 * return userWalletService.withdrawAmount(userWalletDTO); }
	 */

	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public ResponseEntity<Object> depositAmount(@RequestBody UserWalletDTO userWalletDTO) {
		LOGGER.info("Message on UserWalletController :::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = userWalletService.depositAmount(userWalletDTO);
			if (map.get("isSuccess").equals(true)) {
				map.remove("isSuccess", true);
				return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", map.get("Result"));
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", map.get("Result"));
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Failure", map.get("Result"));

		}

	}
}
