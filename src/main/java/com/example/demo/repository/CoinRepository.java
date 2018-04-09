package com.example.demo.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CoinModel;
import com.example.demo.model.UserModel;

@Transactional
public interface CoinRepository extends JpaRepository<CoinModel,Integer>{
	public CoinModel findByCoinName(String coinName);
	public CoinModel findBySymbol(String symbol);
	@Query(value = "SELECT * FROM coin_model WHERE coin_name = ?1 and initial_supply>?2 and price>=?3", nativeQuery = true)
	public CoinModel findByCurrency(String coinName,Long initialSupply,Integer quoteValue);
}
