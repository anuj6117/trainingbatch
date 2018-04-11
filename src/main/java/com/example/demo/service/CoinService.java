package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

	public Object addCoin(CoinModel coinModel) throws Exception {
		if (coinModel.getSymbol() == null || coinModel.getSymbol() == "") {
			throw new Exception("Invalid!! Symbol field cannot be null");
		}
		if (coinModel.getCoinName() == null || coinModel.getCoinName() == "") {
			throw new Exception("Invalid!! coin name field cannot be null");
		}
		if (coinModel.getInitialSupply() == null) {
			throw new Exception("Invalid!! Initial Supply field cannot be null");
		}
		if (coinModel.getPrice() == null) {
			throw new Exception("Invalid!! Price field cannot be null");
		}
		if (Objects.isNull(coinModel.getInitialSupply())) {
			throw new Exception("Invalid!! Initial Supply cannot be null");
		}
		if ((coinModel.getInitialSupply() <= 0) || coinModel.getPrice() <= 0) {
			throw new Exception("Invalid!! Input field should be greater than zero");
		}
		CoinModel coin = coinRepo.findByCoinName(coinModel.getCoinName());
		CoinModel coinsymbol = coinRepo.findBySymbol(coinModel.getSymbol());
		if (coin == null) {
			if (coinsymbol == null) {
				coinRepo.save(coinModel);
			} else {
				throw new Exception("Symbol cannot be same");
			}
		} else {
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

	public Object updateCoin(CoinModel coinModel) throws Exception {
		if (coinModel.getSymbol() == null || coinModel.getSymbol() == "") {
			throw new Exception("Invalid!! Symbol field cannot be null");
		}
		if (coinModel.getCoinName() == null || coinModel.getCoinName() == "") {
			throw new Exception("Invalid!! coin name field cannot be null");
		}
		if (coinModel.getInitialSupply() == null) {
			throw new Exception("Invalid!! Initial Supply field cannot be null");
		}
		if (coinModel.getPrice() == null) {
			throw new Exception("Invalid!! Price field cannot be null");
		}
		if ((coinModel.getInitialSupply() <= 0)) {
			throw new Exception("Invalid!! Input field should be greater than zero");
		}
		if (coinModel.getPrice() <= 0) {
			throw new Exception("Invalid!! Price field should be greater than zero");
		}

		logger.info(coinModel.getCoinName() + "---coinName----");
		logger.info(coinModel.getSymbol() + "---coinsymbol----");
		logger.info(coinModel.getFees() + "---fees----");
		logger.info(coinModel.getInitialSupply() + "---initial supply----");
		logger.info(coinModel.getPrice() + "---price----");
		Optional<CoinModel> coinOptionalObject = coinRepo.findById(coinModel.getCoinId());
	
		if (coinOptionalObject != null) {
		CoinModel coin = coinRepo.findByCoinName(coinModel.getCoinName());
		CoinModel coinsymbol = coinRepo.findBySymbol(coinModel.getSymbol());

		logger.info("------------abcefgj----------------------");
			coinOptionalObject.get().setCoinName(coinModel.getCoinName());
			coinOptionalObject.get().setSymbol(coinModel.getSymbol());
			coinOptionalObject.get().setInitialSupply(coinOptionalObject.get().getInitialSupply()+coinModel.getInitialSupply());
			coinOptionalObject.get().setPrice(coinModel.getPrice());
			coinOptionalObject.get().setFees(coinModel.getFees());
			coinOptionalObject.get().setINRConvergent(coinModel.getINRConvergent());
			coinOptionalObject.get().setProfit(coinModel.getProfit());
			
		    if(coin==null) {
		    	if(coinsymbol==null) {
		    		coinRepo.save(coinOptionalObject.get());
		    	}
		    	else {
		    		if(coinOptionalObject.get().getCoinId()==coinsymbol.getCoinId()) {
		    			coinRepo.save(coinOptionalObject.get());
		    		}
		    		else {
		    			throw new Exception("Cannot Update Symbol already exists");
		    		}
		    	}
		    }
		    else {
		    	if(coinsymbol==null) {
		    		if(coinOptionalObject.get().getCoinId()==coin.getCoinId()) {
		    			coinRepo.save(coinOptionalObject.get());
		    		}
		    		else {
			    			throw new Exception("Cannot Update Name already exists");
			    		}
		    		}
		    	else {
		    		if(coinOptionalObject.get().getCoinId()==coin.getCoinId()&&coinOptionalObject.get().getCoinId()==coinsymbol.getCoinId()) {
		    			coinRepo.save(coinOptionalObject.get());
		    		}
		    		else {
		    			throw new Exception("Cannot update duplicate record exist!!");
		    		}
		    		
		    		
		    	}
		    }
		    	
		    

			return "success";
		} else {
			throw new Exception("No coin Exist");
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
		int flag = 0;
		Optional<CoinModel> coinData;
		if (coinId > 0) {
			coinData = coinRepo.findById(coinId);
			if (coinData.get() != null) {
				flag = 1;
			}
		} else {
			return "Enter valid Coin Id first";
		}
		if (flag == 1) {
			return coinData.get();
		} else {
			return "Coin Id does not exist";
		}

	}

}
