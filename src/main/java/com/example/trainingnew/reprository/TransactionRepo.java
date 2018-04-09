package com.example.trainingnew.reprository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.TransactionModel;

public interface TransactionRepo extends JpaRepository<TransactionModel, Integer>{

}
