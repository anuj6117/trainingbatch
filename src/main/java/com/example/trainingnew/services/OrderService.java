package com.example.trainingnew.services;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.OrderModel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.reprository.OrderReprository;
import com.example.trainingnew.reprository.UserRepo;
import com.example.trainingnew.util.CustomErrorType;

@Service
public class OrderService {

	@Autowired
	OrderReprository orderRepo;
	
	@Autowired
	UserRepo userRepo;
	
	public static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	public ResponseEntity<List<OrderModel>> getAllData() {
		
		
	
		List<OrderModel> find = orderRepo.findAll();
		if (find.isEmpty()) {
			return (new ResponseEntity(new CustomErrorType("No any data exist"), HttpStatus.NOT_FOUND));
		}

		else {
			return new ResponseEntity<List<OrderModel>>(find, HttpStatus.OK);
		}
	}

	//CreateBuyOrder
	public OrderModel createBuyOrder(OrderModel ordermod,String type) throws UserNotFoundException{
		logger.error("let see whats coming in createBuyOrder "+ordermod.getUserId());
		
		System.out.println(ordermod.getUserId());
		UserModel model=userRepo.findByUserId(ordermod.getUserId());
		
//		logger.error("let see whats coming in createBuyOrder "+model.getUserId());
		
		if(model== null){
			throw new UserNotFoundException("user doesn't exist");
		}
		else {
			OrderModel orderModel=new OrderModel();
			
			orderModel.setAmount(ordermod.getAmount());
			orderModel.setFee(ordermod.getFee());
			orderModel.setQuote(ordermod.getQuote());
			orderModel.setOrderType(type);
			orderModel.setOrderCreatedOn(new Date());
			orderModel.setStatus("pending");
			orderModel.setCoinName(ordermod.getCoinName());
			orderModel.setUserModelInOrderModel(model);
		
//			model.getOrders().add(orderModel);
			
			return orderRepo.save(orderModel);
		}
		
	}
	
	
	public OrderModel getDataById(Integer orderId) throws UserNotFoundException {
		OrderModel model = orderRepo.findOneByOrderId(orderId);

		if (model == null) {
			// return new ResponseEntity(new CustomErrorType("User with id " + id + " does
			// not exist"),
			// HttpStatus.NOT_FOUND);
			throw new UserNotFoundException("User with id " + orderId + " does not exist");
		} else {
			return model;
		}
	}

	public OrderModel updateOrderData(Integer orderId, OrderModel order) throws UserNotFoundException {
		OrderModel orderModel = orderRepo.findOneByOrderId(orderId);

		if (orderModel == null) {

			throw new UserNotFoundException("Order id " + orderId + " does not exist");

		} else {
			
			orderModel.setAmount(order.getAmount());
			orderModel.setOrderType(order.getOrderType());
			orderModel.setFee(order.getFee());
			orderModel.setStatus(order.getStatus());
			orderModel.setCoinName(order.getCoinName());

			OrderModel updatedOrder = orderRepo.save(orderModel);
			return updatedOrder;
		}		
	}
}
