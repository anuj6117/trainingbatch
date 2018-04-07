package com.example.demo.repoINterface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.userModel.CoinManagementModel;

public interface CoinManagementRepository extends JpaRepository<CoinManagementModel, Long>{

	public CoinManagementModel findByCoinIdAndCoinName(Long id,String coinName);
	public CoinManagementModel findByCoinNameOrSymbol(String coinName,String symble);
	public CoinManagementModel findByCoinName(String coinName);
	public List<CoinManagementModel> findBySymbol(String symbol);
	}
