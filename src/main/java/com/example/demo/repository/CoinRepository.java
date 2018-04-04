package com.example.demo.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CoinModel;
import com.example.demo.model.UserModel;

@Transactional
public interface CoinRepository extends JpaRepository<CoinModel,Integer>{
	public CoinModel findByCoinName(String coinName);
	public CoinModel findByCoinSymbol(String coinSymbol);
}
