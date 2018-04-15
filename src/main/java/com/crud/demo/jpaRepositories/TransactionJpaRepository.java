package com.crud.demo.jpaRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.demo.model.Transaction;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Integer>{

	List<Transaction> findByBuyerId(Integer buyerId);
}
