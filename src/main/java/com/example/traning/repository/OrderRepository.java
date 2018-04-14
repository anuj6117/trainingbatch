package com.example.traning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traning.domain.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {


	Order findByOrderId(Integer orderId);

	Order findOneByOrderId(Integer orderId);
	//List<Order> findallByOrderType(String orderType, String status);

	List<Order> findByOrderTypeAndStatus(String string, String string2);





	

}
