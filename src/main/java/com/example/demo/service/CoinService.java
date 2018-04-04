package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoinModel;
import com.example.demo.repository.CoinRepository;

@Service
public class CoinService {

	@Autowired
	private CoinRepository coinRepo;

	public Object addCoin(CoinModel coinModel)throws Exception {
		if(coinModel.getCoinName().equals("")&&coinModel.getCoinSymbol().equals("")) {
			return "invalid input";
		}else {
			CoinModel coin = coinRepo.findByCoinName(coinModel.getCoinName());
			CoinModel coinsymbol = coinRepo.findByCoinSymbol(coinModel.getCoinSymbol());
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
			coinOptionalObject.get().setCoinSymbol(coinModel.getCoinSymbol());
			coinOptionalObject.get().setInitialSupply(coinModel.getInitialSupply());
			coinOptionalObject.get().setPrice(coinModel.getPrice());
			coinRepo.save(coinOptionalObject.get());
			return "success";
		} else {
			return "No Coin Exist";
		}
	}

	public String deleteCoin(CoinModel coinModel) {
		try {
			if (coinModel.getCoinId() > 0) {
				coinRepo.deleteById(coinModel.getCoinId());
				return "success";
			} else {
				return "Enter the Coin Id first";
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public Object getCoinById(CoinModel coinModel) {
		int flag=0;
		Optional<CoinModel> coinData;
			if (coinModel.getCoinId() > 0) {
				coinData=coinRepo.findById(coinModel.getCoinId());
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
