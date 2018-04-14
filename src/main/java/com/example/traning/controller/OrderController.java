package com.example.traning.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.traning.domain.Order;
import com.example.traning.service.OrderService;
import com.example.traning.utill.CustomExceptionhandler;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderservice;
	
	//==========================================================================================
	
	//get single data by id
  	@RequestMapping(value="/getorderbyuserid",method=RequestMethod.GET)
  	public ResponseEntity<Object> getById(@RequestParam(value="userId") Long userId) {
  		Set<Order> obj=null;
  		if(userId==null)
  		{
  			return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "OrderId can't be null",obj);
  		}
  		else
  		{
  		try {
  	    obj= orderservice.getOrderByUserId(userId);
  	            }
  	    	catch(Exception e) {
  	    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
  	    	}
  	    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"Data Search  suceessfuly", obj);
  		}	
  	}
	
  //==========================================================================================
	
	
  	 //save all data by buy
    @RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
	public ResponseEntity<Object> savebuyuser(@RequestBody Order order) {
    	Order obj=null;
    	if(order.getTradingAmount()==null || order.getTradingAmount()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "tradingAmount can't be null",obj);
    	}
    	else if(order.getFee()==null || order.getFee()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "fee can't be null",obj);
    	}
    	else if(order.getQuoteValue()==null || order.getQuoteValue()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "quoteValue can't be null",obj);
    	}
    	else if(order.getCoinName().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "coinName can't be null",obj);
    	}
    	else {
    	 
    	try {
    		String type="buyer";
    	obj= orderservice.createBuyOrder(order,type);
            }
    	catch(Exception e) {
    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
    	}
    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
    	}
      }
    
    
    
	//================================================================================================================
	
	
    //save all data by sell
    @RequestMapping(value="/createsellorder",method=RequestMethod.POST)
	public ResponseEntity<Object> saveselluser(@RequestBody Order order) {
    	Order obj=null;
    	if(order.getTradingAmount()==null || order.getTradingAmount()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "tradingAmount can't be null",obj);
    	}
    	else if(order.getUserId()==null)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "ID can't be null",obj);
    	}
    	else if(order.getFee()==null || order.getFee()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "fee can't be null",obj);
    	}
    	else if(order.getQuoteValue()==null || order.getQuoteValue()==0)
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "quoteValue can't be null",obj);
    	}
    	else if(order.getCoinName().isEmpty())
    	{
    		return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, "coinName can't be null",obj);
    	}
    	else {
    	 
    	try {
    		String type="seller";
    		obj= orderservice.createBuyOrder(order,type);
            }
    	catch(Exception e) {
    		return CustomExceptionhandler.generateResponse(HttpStatus.OK, false,e.getMessage(), obj);
    	}
    	return CustomExceptionhandler.generateResponse(HttpStatus.OK, true,"messsage suceessfuly", obj);
    	}
      }
	//==================================================================================================================
	
    @RequestMapping(value = "/admincreatesellorder", method = RequestMethod.POST)
	public ResponseEntity<Object> admincreateSellOrder(@Valid @RequestBody Order order) {

		
		Order obj = null;
			try {
				obj = orderservice.admincreateSellOrder(order);
			} catch (Exception e) {
				return CustomExceptionhandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), obj);
			}

		return CustomExceptionhandler.generateResponse(HttpStatus.OK, true, "Successfull", obj);
	}
    //=====================================================================================================================
    
    @RequestMapping("/showallorder")
	 public List<Order> getAllOrders() {
	
		 return orderservice.showOrder();
		 
	 }
	
	
	
}	
	
	
	
	
	
	
	