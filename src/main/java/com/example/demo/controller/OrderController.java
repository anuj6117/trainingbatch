package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.OrderModel;
import com.example.demo.service.OrderService;
import com.example.demo.utils.ApiResponse;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	// To add a new user and a default is created
		@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
		public ResponseEntity<Object> addbuyorder(@RequestBody OrderModel orderModel) throws Exception {
			Object response = "";
			try {
				response = orderService.createbuyOrder(orderModel);
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);

		}
		
		@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
		public ResponseEntity<Object> addsellorder(@RequestBody OrderModel orderModel) throws Exception {
			Object response = "";
			try {
				response = orderService.createsellOrder(orderModel);
			} catch (Exception e) {
				return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
			}
			return ApiResponse.generateResponse(HttpStatus.OK, true, response.toString(), null);

		}
		
			@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
			public ResponseEntity<Object> allordersbyId(@RequestParam Integer userid) throws Exception {
				Object response ;
				try {
					response = orderService.getallordersbyId(userid);
				} catch (Exception e) {
					return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
				}
				return ApiResponse.generateResponse(HttpStatus.OK, true,"success", response);

			}
			
			@RequestMapping(value = "/getallorders", method = RequestMethod.GET)
			public ResponseEntity<Object> allorders() throws Exception {
				Object response ;
				try {
					response = orderService.getallorders();
				} catch (Exception e) {
					return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
				}
				return ApiResponse.generateResponse(HttpStatus.OK, true,"success", response);

			}
			@RequestMapping(value = "/getorderbytype/{orderType}", method = RequestMethod.GET)
			public ResponseEntity<Object> getorderbytype(@PathVariable(value = "orderType") String orderType) throws Exception {
				Object response ;
				try {
					response = orderService.getOrdersByType(orderType);
				} catch (Exception e) {
					return ApiResponse.generateResponse(HttpStatus.NOT_ACCEPTABLE, false, e.getMessage(), null);
				}
				return ApiResponse.generateResponse(HttpStatus.OK, true,"success", response);

			}
	
	
}
