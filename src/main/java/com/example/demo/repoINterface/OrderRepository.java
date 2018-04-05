package com.example.demo.repoINterface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.userModel.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, String> {

public List<OrderModel>	findAllByOrderType(String data);


@Query(value = "SELECT * FROM order_model WHERE coin_name = ?1 and order_type=?2 and quote<=?3", nativeQuery = true)
public List<OrderModel> findByCoin(String name,String ordertype,Integer quote);
}
