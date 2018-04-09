package com.crud.demo.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.demo.model.Transaction;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Integer>{

}
