package com.example.adarsh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adarsh.Handller.ResponseHandler;
import com.example.adarsh.domain.OrderTabel;
import com.example.adarsh.service.OrderServices;

@RestController
public class OrderController {

	@Autowired
	private OrderServices orderServices;

	@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
	public ResponseEntity<Object> buyorder(@RequestBody OrderTabel data) {
		OrderTabel result = null;
		try {
			String type = "buyer";
			result = orderServices.buycurrency(data, type);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "SUCCESS", result);
	}

	@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
	public ResponseEntity<Object> sellorder(@RequestBody OrderTabel data) {
		OrderTabel result = null;
		try {
			String type = "seller";
			result = orderServices.buycurrency(data, type);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "SUCCESS", result);
	}

	@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
	public ResponseEntity<Object> showalldata(@RequestParam("id") Long userId) {
		List<OrderTabel> result = null;
		try {
			result = orderServices.showalldata(userId);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "SUCCESS", result);
	}

	@RequestMapping(value = "/admincreatesellorder", method = RequestMethod.POST)
	public ResponseEntity<Object> admincreateSellOrder(@Valid @RequestBody OrderTabel order) {
		OrderTabel obj = null;
		try {
			obj = orderServices.admincreateSellOrder(order);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
	}
}
