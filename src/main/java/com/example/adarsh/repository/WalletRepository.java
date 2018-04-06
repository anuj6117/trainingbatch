package com.example.adarsh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adarsh.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Serializable> {

}
