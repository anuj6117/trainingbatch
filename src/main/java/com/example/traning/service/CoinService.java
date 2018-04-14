package com.example.traning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Coin;
import com.example.traning.exception.UserNotFoundException;
import com.example.traning.repository.CoinRepository;
@Service
public class CoinService {
	
	@Autowired
	CoinRepository coinrepository;
	
	//================Curds Operations==========================
	
	
	public Coin createcoin(Coin coin) throws UserNotFoundException {
		Coin co=coinrepository.findByCoinName(coin.getCoinName());
		Coin vo=coinrepository.findBySymbol(coin.getSymbol());
		
		System.out.println("hello 2"+co);
		if(co!=null) {
			throw new UserNotFoundException("this coin already exist");
		}else {
			if(vo!=null) {throw new UserNotFoundException("this coin symbol already exist");}
			System.out.println("hello 3"+co);
			coinrepository.save(coin);
		
		}
		return coin;
	}

	 //******************************getallCoin*************************************************************
	public List<Coin> getAllCoins() {
		List<Coin> lcoin= coinrepository.findAll();
		if(lcoin.isEmpty()) {
			throw new NullPointerException("Table is null");
		}else {
			return lcoin;
		}
	}
	 //********************************update***********************************************************

	public Coin updatecoin(Long coinId,Coin coin ) throws UserNotFoundException {
		Coin coinref =coinrepository.findByCoinId(coinId);
		Coin coiname=coinrepository.findByCoinName(coin.getCoinName());
		Coin coinsymbol=coinrepository.findBySymbol(coin.getSymbol());
		if(coinref!=null)
		{
		coinref.setCoinName(coin.getCoinName());
		coinref.setInitialSupply(coin.getInitialSupply());
		coinref.setSymbol(coin.getSymbol());
		coinref.setPrice(coin.getPrice());
		Coin CoinObj=coinrepository.save(coinref);
		return CoinObj;	
		
		
		}
		else
		{
			throw new NullPointerException("Id doen't be exist");
		}
	}

	 //*****************************delete**************************************************************
	public String deletecoin(Long coinId) {
		Coin coin=coinrepository.findByCoinId(coinId);
		if(coinId==null) {throw new NullPointerException("Id null");}
		coinrepository.delete(coin);
		return "Success";
	}

	
	
	 //**************************getCoinById****************************************************************
	public Coin getDataById(Long coinId) {
		 Coin col=coinrepository.findByCoinId(coinId);
		 if(col!=null)
		 {
			return col; 
		 }
		 else {
			 throw new NullPointerException("Id doen't be exist");
		 }
	}
	
	
	//================Curds Operations==========================
	
}
