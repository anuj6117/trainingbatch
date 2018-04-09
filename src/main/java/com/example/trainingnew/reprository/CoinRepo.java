package com.example.trainingnew.reprository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.Coinmodel;

public interface CoinRepo extends JpaRepository<Coinmodel, Integer>{

	Coinmodel findOneByCoinName(String coinName);

	Coinmodel findOneByCoinId(Integer coinid);

	Coinmodel findOneBySymbol(String symbol);

	Coinmodel findByCoinName(String coinName);

	Coinmodel findByCoinNameAndCoinId(String coinName, Integer coinId);

	Coinmodel findOneBySymbolAndCoinName(String symbol, String coinName);

	List<Coinmodel> findAllByCoinNameOrSymbol(String coinName, String symbol);

	List<Coinmodel> findByCoinNameOrSymbol(String coinName, String symbol);

	Coinmodel findByCoinNameAndSymbol(String coinName, String symbol);


}
