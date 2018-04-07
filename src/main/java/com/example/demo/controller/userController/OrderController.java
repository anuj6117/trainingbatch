package com.example.demo.controller.userController;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userModel.OrderModel;
import com.example.demo.services.OrderServices;
import com.example.demo.utils.ResponseHandler;

@RestController
public class OrderController {

@Autowired
OrderServices orderdata;
	
@RequestMapping(value="/createbuyorder" ,method = RequestMethod.POST)
public ResponseEntity<Object> buycoin(@RequestBody OrderModel data)
{
	OrderModel result=null;
	try {
		String type="buyer";
		result=orderdata.buycurrency(data,type);
	}
	catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
	}
	return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	
}
@RequestMapping(value="/createsellorder" ,method = RequestMethod.POST)
public ResponseEntity<Object> sellcoin(@RequestBody OrderModel data)
{
	OrderModel result=null;
	try {
		String type="seller";
		result=orderdata.buycurrency(data,type);
	}
	catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
	}
	return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	
}

@RequestMapping(value="/getorderbyuserid" ,method = RequestMethod.GET)
public ResponseEntity<Object> showhistory(@RequestParam Long id)
{
	List<OrderModel> result=null;
	try {
		result=orderdata.showhistory(id);
	}
	catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
	}
	return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	
}
@RequestMapping(value="/admincreatesellorder" ,method = RequestMethod.POST)
public ResponseEntity<Object> sellcoinbyadmin(@RequestBody OrderModel data)
{
	OrderModel result=null;
	try {
		String type="seller";
		result=orderdata.sellbyadmin(data);
	}
	catch (Exception e) {
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
	}
	return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	
}

}
