package com.crud.demo.jpaRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.demo.model.Orders;

public interface OrderJpaRepository extends JpaRepository<Orders,Integer>{
	
	public List<Orders> findAllByStatusAndOrderType(String status,String orderType);

}
