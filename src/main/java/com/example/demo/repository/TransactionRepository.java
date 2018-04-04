package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.TransactionModel;

public interface TransactionRepository extends CrudRepository<TransactionModel,Integer>{

}
