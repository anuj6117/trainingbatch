package com.example.traning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.traning.domain.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}