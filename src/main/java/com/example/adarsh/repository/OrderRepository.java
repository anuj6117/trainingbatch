package com.example.adarsh.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adarsh.domain.OrderTabel;

public interface OrderRepository extends JpaRepository<OrderTabel, Serializable> {

	List<OrderTabel> findAllByOrderTypeAndStatus(String type, String status);

}
