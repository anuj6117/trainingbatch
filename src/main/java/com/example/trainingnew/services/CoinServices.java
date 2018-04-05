package com.example.trainingnew.services;

import java.util.List;
import java.util.Objects;

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
		
		if(coinobj1!=null || coinobj2!=null) {
				if(coinobj1!=null) {
//					true
				}
				else {
//					false
				}
				
		}else {
//			false
		}
		
		
		
		if(coinobj1!=null) {
			throw new CoinNotFoundException(coinobj1.getCoinName()+" coin already Exist");
		}
		else {
			
			 if(coinobj2!=null) {
					throw new CoinNotFoundException(coinobj1.getCoinName()+" Symbol already Exist");
			}
			 else {
			Coinmodel coinmodel=new Coinmodel();
			coinmodel.setCoinName(model.getCoinName());
			coinmodel.setSymbol(model.getSymbol());
			coinmodel.setInitialSupply(model.getInitialSupply());
			coinmodel.setPrice(model.getPrice());
			
			return coinrepo.save(coinmodel);
			 }
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
	
	public Coinmodel getDataById(Long id) throws CoinNotFoundException {
		Coinmodel model = coinrepo.findOneByCoinId(id);

		if (model == null) {
			throw new CoinNotFoundException("Coin with id " + id + " does not exist");
		} else {
			return model;
		}

	}

	//updateCoin
	public Coinmodel updateCoin(Long coinId, Coinmodel model) throws CoinNotFoundException {

		Coinmodel coinobj=coinrepo.findOneByCoinId(coinId);
		
		if(coinobj ==null) {
			throw new CoinNotFoundException("Coin does not exist at this id: "+coinId);
		}
		else {
			
		}
		
		coinobj.setCoinName(model.getCoinName());
		coinobj.setSymbol(model.getSymbol());
		coinobj.setInitialSupply(model.getInitialSupply());
		coinobj.setPrice(model.getPrice());
		
		Coinmodel updated=coinobj;
		return coinrepo.save(updated);
	}

	//deleteCoin
	public Coinmodel deleteCoin(Long coinId) throws CoinNotFoundException {
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
