package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constant.Constant;
import com.example.demo.model.CoinModel;
import com.example.demo.repository.CoinRepository;
import com.example.demo.utils.Utility;

@Service
public class CoinService {

	@Autowired
	private CoinRepository coinRepo;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Object addCoin(CoinModel coinModel)throws Exception {
		
	logger.info(Utility.isStringNull(coinModel.getSymbol())+"-------symbol------");
	logger.info(Utility.isStringNull(coinModel.getCoinName())+"------coin name------");
	logger.info(Utility.isLongNull(coinModel.getInitialSupply())+"-------intial supply-----");
	logger.info(Utility.isLongNull(coinModel.getFees())+"-------fees-----");
		
	if(!(Utility.isStringNull(coinModel.getSymbol()))||!(Utility.isStringNull(coinModel.getCoinName()))) {
			throw new Exception("Invalid!! Input field cannot be null");
		}
		if(coinModel.getInitialSupply().equals(null)||coinModel.getFees().equals(null)) {
			throw new Exception("Invalid!! Input field cannot be null");
		}
		if(coinModel.getInitialSupply().equals(0)||coinModel.getFees().equals(0)) {
			throw new Exception("Invalid!! Input field cannot be null");
		}
		if((coinModel.getInitialSupply()<0)||(coinModel.getFees()<0)) {
			throw new Exception("Invalid!! Input field cannot be null");
		}
			CoinModel coin = coinRepo.findByCoinName(coinModel.getCoinName());
			CoinModel coinsymbol = coinRepo.findBySymbol(coinModel.getSymbol());
			if(coin==null) {
				if(coinsymbol==null) {
					coinRepo.save(coinModel);
				}
				else {
					throw new Exception("Symbol cannot be same");
				}
			}
			else {
				throw new Exception("Coin Already exists");
			}
			return "success";
		
	
			
	}

	public Object getAllCoinDetail() {
		List<CoinModel> coinDetails = new ArrayList<CoinModel>();
		coinRepo.findAll().forEach(coinDetails::add);
		if (coinDetails != null) {
			return coinDetails;
		} else {
			return "No Data Found";
		}
	}

	public Object updateCoin(CoinModel coinModel) {

		Optional<CoinModel> coinOptionalObject = coinRepo.findById(coinModel.getCoinId());
		if (coinOptionalObject != null) {
			coinOptionalObject.get().setCoinName(coinModel.getCoinName());
			coinOptionalObject.get().setSymbol(coinModel.getSymbol());
			coinOptionalObject.get().setInitialSupply(coinModel.getInitialSupply());
			coinOptionalObject.get().setPrice(coinModel.getPrice());
			coinOptionalObject.get().setFees(coinModel.getFees());
			coinOptionalObject.get().setINRConvergent(coinModel.getINRConvergent());
			coinOptionalObject.get().setProfit(coinModel.getProfit());
			coinRepo.save(coinOptionalObject.get());
			//addCoin(coinModel);
			return "success";
		} else {
			return "No Coin Exist";
		}
	}

	public String deleteCoin(Integer coinId) {
		try {
			if (coinId > 0) {
				coinRepo.deleteById(coinId);
				return "success";
			} else {
				return "Enter the Coin Id first";
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public Object getCoinById(Integer coinId) {
		int flag=0;
		Optional<CoinModel> coinData;
			if (coinId > 0) {
				coinData=coinRepo.findById(coinId);
				if(coinData.get()!=null) {
					flag=1;
				}
			} else {
				return "Enter valid Coin Id first";
			}
			if(flag==1) {
				return coinData.get();
			}
			else {
				return "Coin Id does not exist";
			}
		
		
	}

}
