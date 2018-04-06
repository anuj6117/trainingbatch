package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.OrderModel;


@Transactional
public interface OrderRepository extends CrudRepository<OrderModel,Integer>{
	public List<OrderModel> findByOrderType(String orderType);
	@Query(value = "SELECT * FROM order_model WHERE coin_name = ?1 and order_type=?2 and quote_value<?3 and quantity>=?4", nativeQuery = true)
	public List<OrderModel> findSeller(String coinName,String orderType,Integer quoteValue,Long quantity);
}
