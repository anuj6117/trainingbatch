package com.example.demo.controller.userController;

import java.util.List;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.OrderModel;
import com.example.demo.services.TransactionServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class TransactionController {

@Autowired
TransactionServices order;

	@RequestMapping(value="/gettransaction" ,method = RequestMethod.GET)
	public ResponseEntity<Object> transaction()
	{
		OrderModel result=null;
		try {
			
			result=order.transactionChech();
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
		
	}
	@GetMapping(value="getbuyerdetails")
	public ResponseEntity<Object> getAllBuyer()
	{
		List<OrderModel> result=null;
		try {
			
			result=order.getAllBuyer();
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
		
	}
	@GetMapping(value="getsellerdetails")
	public ResponseEntity<Object> getAllSeller()
	{
		List<OrderModel> result=null;
		try {
			
			result=order.getAllSeller();
		}
		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
		
	}
}
	