package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.OrderModel;


@Transactional
public interface OrderRepository extends CrudRepository<OrderModel,Integer>{
	public List<OrderModel> findByOrderType(String orderType);
}
