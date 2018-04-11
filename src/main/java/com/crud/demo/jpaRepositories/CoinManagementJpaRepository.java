package com.crud.demo.jpaRepositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.demo.model.CoinManagement;

public interface CoinManagementJpaRepository  extends JpaRepository<CoinManagement, Integer>{
	
	/*@Query("Select DISTINCT c.coinName from CoinManagement c where c.coinName=:coinName")
	Set<String> findByCoinName(@Param("coinName") String coinName);*/
	@Query("Select DISTINCT c.coinName from CoinManagement c ")
	Set<String> findByCoinName();
	
	CoinManagement findByCoinName(String coinName);
	
	@Query("Select DISTINCT c.symbol from CoinManagement c ")
	Set<String> findBySymbol();

}
