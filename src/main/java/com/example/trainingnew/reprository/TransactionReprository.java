package com.example.trainingnew.reprository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.TransactionModel;

public interface TransactionReprository extends JpaRepository<TransactionModel, Integer>{

}
