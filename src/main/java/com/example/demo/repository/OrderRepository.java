package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.OrderModel;

@Transactional
public interface OrderRepository extends CrudRepository<OrderModel,Integer>{

}
