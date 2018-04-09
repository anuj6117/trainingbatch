package com.example.trainingnew.reprository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.OrderModel;

public interface OrderReprository extends JpaRepository<OrderModel, Integer>{

	OrderModel findOneByOrderId(Integer orderId);

	List<OrderModel> findByOrderTypeAndStatus(String string, String string2);



}
