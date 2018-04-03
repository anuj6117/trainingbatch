package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.OrderModel;
import com.example.demo.service.OrderService;
import com.example.demo.utils.ApiResponse;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	// To add a new user and a default is created
		@RequestMapping(value = "/createorder", method = RequestMethod.POST)
		public ResponseEntity<Object> add(@RequestBody OrderModel orderModel) throws Exception {
			Object response = "";
			try {
				response = orderService.createOrder(orderModel);
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);

		}
	
	
}
