package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.WalletModel;

@Transactional
public interface WalletRepository extends JpaRepository<WalletModel, Integer> {
	List<WalletModel> findAllByUserIdW(Integer id);
	public WalletModel findByWalletTypeAndUserIdW(String walletType, Integer userIdW);
}
