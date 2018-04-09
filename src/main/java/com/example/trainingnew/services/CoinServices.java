package com.example.trainingnew.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainingnew.exception.CoinNotFoundException;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.reprository.CoinRepo;


@Service
public class CoinServices {
	
	public static final Logger logger = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	CoinRepo coinrepo;
	
	//addCoinService
	public Coinmodel addCoin(Coinmodel model) throws CoinNotFoundException {	
		
		

		Coinmodel coinobj1=coinrepo.findOneByCoinName(model.getCoinName());
		
		Coinmodel coinobj2=coinrepo.findOneBySymbol(model.getSymbol());
		
		
		if(coinobj1!=null) {
			throw new NullPointerException(" coin already Exist");
		}
		
		else if(coinobj2!=null)
			throw new NullPointerException(" Symbol already Exist");
	
		else {
			Coinmodel coinmodel=new Coinmodel();
			coinmodel.setCoinName(model.getCoinName());
			coinmodel.setSymbol(model.getSymbol());
			coinmodel.setInitialSupply(model.getInitialSupply());
			coinmodel.setFee(model.getFee());
			coinmodel.setProfit(0.0);
			coinmodel.setPrice(model.getPrice());
			
			Double coinInInr=model.getInitialSupply()*model.getPrice();
			
			coinmodel.setCoinInINR(coinInInr);
			
			return coinrepo.save(coinmodel);
		}
	}
	
	//getAllCoinService
	public List<Coinmodel> showAllCoins() throws CoinNotFoundException {
		List<Coinmodel> coinobj=coinrepo.findAll();
		
		if(coinobj.isEmpty())
		{
			throw new CoinNotFoundException("Oops! No any coin is present.");
		}
	
		return coinrepo.findAll();
	}
	
	//
	
	public Coinmodel getDataById(Integer id) throws CoinNotFoundException {
		Coinmodel model = coinrepo.findOneByCoinId(id);

		if (model == null) {
			throw new CoinNotFoundException("Coin with id " + id + " does not exist");
		} else {
			return model;
		}

	}

	//updateCoin
	public Coinmodel updateCoin(Coinmodel model) throws CoinNotFoundException {

		Coinmodel coinobj=coinrepo.findOneByCoinId(model.getCoinId());
		
		if(coinobj ==null) {
			throw new CoinNotFoundException("Coin does not exist at this id: "+model.getCoinId());
		}
	//	Coinmodel coinObj11=coinrepo.findByCoinNameAndCoinId(model.getCoinName(),model.getCoinId());
		
		
		coinobj.setCoinName(model.getCoinName());
		coinobj.setSymbol(model.getSymbol());
		coinobj.setInitialSupply(coinobj.getInitialSupply()+model.getInitialSupply());
		coinobj.setPrice(model.getPrice());
		List<Coinmodel> allCoins=coinrepo.findByCoinNameOrSymbol(coinobj.getCoinName(),coinobj.getSymbol());
		Coinmodel allCoins1=coinrepo.findByCoinNameAndSymbol(coinobj.getCoinName(),coinobj.getSymbol());
		if(allCoins.size()>1 && allCoins1==null)
			throw new NullPointerException("This coin or symbol already exist");
		
		Coinmodel updated=coinobj;
		
		return coinrepo.save(updated);
	}

	//deleteCoin
	public Coinmodel deleteCoin(Integer coinId) throws CoinNotFoundException {
		Coinmodel coindetails=coinrepo.findOneByCoinId(coinId);
		logger.error(coindetails+" Checking in the coindetails "+coinId);
		if(coindetails == null) {
			throw new CoinNotFoundException("Coin does not exist at this id: "+coinId);
		}
		else {
			coinrepo.delete(coindetails);
			return coindetails;
		}   
	}
}
