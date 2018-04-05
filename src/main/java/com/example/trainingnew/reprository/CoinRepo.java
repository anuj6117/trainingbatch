package com.example.trainingnew.reprository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.Coinmodel;

public interface CoinRepo extends JpaRepository<Coinmodel, Long>{

	Coinmodel findOneByCoinName(String coinName);

	Coinmodel findOneByCoinId(Long coinid);

	Coinmodel findOneBySymbol(String symbol);


}
