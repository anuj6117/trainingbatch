package com.example.trainingnew.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.OrderModel;
import com.example.trainingnew.services.OrderService;
import com.example.trainingnew.util.ExceptionHandler;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderservice;
	
	//showAllOrderByUserId
	@RequestMapping(value = "/getallorders", method = RequestMethod.GET)
	public ResponseEntity<List<OrderModel>> showData() {

		return orderservice.getAllData();
	}
	
	//createBuyOrder
	@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
	public ResponseEntity<Object> createBuyOrder(@Valid @RequestBody OrderModel order) {

		OrderModel obj = null;
			try {String type="buy";
				obj = orderservice.createBuyOrder(order,type);
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}

		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
	}
	
	//createSellOrder
		@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
		public ResponseEntity<Object> createSellOrder(@Valid @RequestBody OrderModel order) {

			OrderModel obj = null;
				try {String type="sell";
					obj = orderservice.createBuyOrder(order,type);
				} catch (Exception e) {
					return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
				}

			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
		}
	
	
	@RequestMapping(value = "/showorders/{id}", method = RequestMethod.POST)
	public ResponseEntity<Object> getById(@PathVariable(value = "id") Integer orderId) {
		OrderModel obj = null;

		try {
			obj = orderservice.getDataById(orderId);
		} catch (UserNotFoundException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}
	
	//getOrderByUserId
	@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
	public ResponseEntity<Object> getOrderByUserId(@RequestParam("userId") long userId) {
		List<OrderModel> obj = null;

		try {
			obj = orderservice.getOrderByUserId(userId);
		} catch (NullPointerException e) {
			return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
		}
		return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Fetch Data Successfully", obj);
	}
	
	
	//updateUserApi
		@RequestMapping(value = "/updateOrder/{id}", method = RequestMethod.PUT)
		public  ResponseEntity<Object> update(@PathVariable(value = "id") Integer orderId, @RequestBody OrderModel allDetails) {
			OrderModel obj = null;
			try {
				obj = orderservice.updateOrderData(orderId, allDetails);
			} catch (Exception e) {
				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}
			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Updated", obj);
		}

		//deleteUserApi
//		@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
//		public ResponseEntity<Object> delete(@PathVariable(value = "id") Integer orderId) {
//			OrderModel obj = null;
//			try {
//				obj = orderservice.deleteOrderData(orderId);
//			} catch (Exception e) {
//				return ExceptionHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
//			}
//			return ExceptionHandler.generateResponse(HttpStatus.OK, true, "Successfully Deleted", obj);
//
//		}

}
