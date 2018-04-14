package com.example.traning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.traning.domain.Transaction;

public interface TransectionRepository extends JpaRepository<Transaction, Long> {

}
