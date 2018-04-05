package com.example.demo.repoINterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.userModel.TransactionModel;


public interface TransactionRepository extends JpaRepository<TransactionModel, Long>{

	
}
