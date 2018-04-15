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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.custom.responcsehandler.ResponseHandler;
import com.crud.demo.model.Orders;
import com.crud.demo.service.OrderService;

@RestController
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value="/createbuyorder",method = RequestMethod.POST)
	public ResponseEntity<Object> createBuyingOrder(@RequestBody Orders order) {
		
		LOGGER.info("OrderController (createBuyingOrder):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = orderService.createBuyingOrder(order);
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

	/*****************************************************************************************/
	@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
	public ResponseEntity<Object> createSellingOrder(@RequestBody Orders order) {

		LOGGER.info("OrderController (createSellingOrder):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");
		/* user.getRole().forEach(role->role.setUser(user)); important */
		try {
			map = orderService.createSellingOrder(order);
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
	/*****************************************************************************************/
	@RequestMapping(value = "/getorderbyuserid")
	public ResponseEntity<Object> getAllOrdersByUserId(@RequestParam Integer userId) {
		LOGGER.info("Message on OrderController (getAllSellingOrders):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");

		try {
			map = orderService.getAllOrdersByUserId(userId);
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


	/*****************************************************************************************/
	@RequestMapping(value = "/getallsellingorders")
	public ResponseEntity<Object> getAllSellingOrders() {
		LOGGER.info("Message on OrderController (getAllSellingOrders):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");

		try {
			map = orderService.getAllSellingOrders();
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

	/*****************************************************************************************/
	@RequestMapping(value = "/getallbuyingorders")
	public ResponseEntity<Object> getAllBuyingOrders() {
		LOGGER.info("Message on OrderController (getAllBuyingOrders):::::::::::::::::controller hit");
		Map<String, Object> map = null;
		System.out.println("controller hit");

		try {
			map = orderService.getAllBuyingOrders();
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
