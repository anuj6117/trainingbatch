package com.example.trainingnew.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.trainingnew.exception.CoinNotFoundException;
import com.example.trainingnew.exception.ResourcesNotFoundException;
import com.example.trainingnew.exception.UserNotFoundException;
import com.example.trainingnew.model.Coinmodel;
import com.example.trainingnew.reprository.CoinRepo;


@Service
public class CoinServices {
	
	public static final Logger logger = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	CoinRepo coinrepo;
	
	//-----------------------------------------------------------------------------addCoinService
	public Coinmodel addCoin(Coinmodel model) throws CoinNotFoundException {			
		Coinmodel coinobj=coinrepo.findOneByCoinName(model.getCoinName());
		if(coinobj!=null) {
			throw new CoinNotFoundException(coinobj.getCoinName()+" coin already Exist");
		}
		else {
		Coinmodel coinmodel=new Coinmodel();
		coinmodel.setCoinName(model.getCoinName());
		coinmodel.setSymbol(model.getSymbol());
		coinmodel.setIntialSupply(model.getIntialSupply());
		coinmodel.setPrice(model.getPrice());
		return coinrepo.save(coinmodel);
		}
	}
	
	//-----------------------------------------------------------------------------getAllCoinService
	public List<Coinmodel> showAllCoins() throws CoinNotFoundException {
		List<Coinmodel> coinobj=coinrepo.findAll();
		
		if(coinobj.isEmpty())
		{
			throw new CoinNotFoundException("Oops! No any coin is present.");
		}
	
		return coinrepo.findAll();
	}

//-------------------------------------------------------------------------------------------
	public Coinmodel updateCoin(Long coinId, Coinmodel model) throws CoinNotFoundException {

		Coinmodel coinobj=coinrepo.findOneByCoinId(coinId);
		
		if(coinobj ==null) {
			throw new CoinNotFoundException("Coin does not exist at this id: "+coinId);
		}
		else {
			
		}
		
		coinobj.setCoinName(model.getCoinName());
		coinobj.setSymbol(model.getSymbol());
		coinobj.setIntialSupply(model.getIntialSupply());
		coinobj.setPrice(model.getPrice());
		
		Coinmodel updated=coinobj;
		return coinrepo.save(updated);
	}

//--------------------------------------------------------------------------------------------
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
