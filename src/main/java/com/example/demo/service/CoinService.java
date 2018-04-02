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

	public Boolean addCoin(CoinModel coinModel) {
		if (coinModel.getCoinName().equals(null)) {
			return false;
		} else {
			coinRepo.save(coinModel);
			return true;
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

}
