package com.crud.demo.conroller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.service.ServiceTransaction;

@RestController
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	/*@Autowired
	private TransactionService transactionService;
*/	
	@Autowired
	private ServiceTransaction serviceTransaction;
	
	
	@RequestMapping("/transaction")
public ResponseEntity<Object> approvetransaction() {
		
		LOGGER.info("OrderController (approvetransaction):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = serviceTransaction.approvetransaction();
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
