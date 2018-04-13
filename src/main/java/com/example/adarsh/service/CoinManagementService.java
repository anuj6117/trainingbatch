package com.example.adarsh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.adarsh.domain.CoinManagement;
import com.example.adarsh.repository.CoinManagementRepository;

@Service

public class CoinManagementService {

	@Autowired
	CoinManagementRepository coinManagementRepository;

	public CoinManagement addAllCoinData(@Validated CoinManagement data) {

		CoinManagement model = coinManagementRepository.findByCoinName(data.getCoinName());

		if (model != null) {
			throw new NullPointerException("this coin already exist");
		} else {
			System.out.println(data.getCoinName() + "");

			return coinManagementRepository.save(data);
		}

	}

	public Map<String, Object> deleteCoinById(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		CoinManagement coinresult = coinManagementRepository.findOne(id);
		if (coinresult == null)
			throw new NullPointerException("id not matched");
		coinManagementRepository.delete(id);
		result.put("result", "success");
		return result;

	}

	public Map<String, Object> updataCoinData(CoinManagement data) {
		Map<String, Object> result = new HashMap<String, Object>();
		CoinManagement model = coinManagementRepository.findByCoinIdAndCoinName(data.getCoinId(), data.getCoinName());
		if (model == null)
			throw new NullPointerException("currency not matched");
		model.setPrice(data.getPrice());
		model.setInitialSupply(data.getInitialSupply());
		coinManagementRepository.save(model);

		result.put("result", "success");
		return result;

	}

	public List<CoinManagement> findAllCoin() {
		List<CoinManagement> data = coinManagementRepository.findAll();
		if (data.isEmpty())
			throw new NullPointerException("data not found");
		return coinManagementRepository.findAll();
	}
	
	
	public CoinManagement getCoinById(Long coinId) {
		CoinManagement coinid= coinManagementRepository.findByCoinId(coinId);
		 if(coinid!=null)
		 {
			return coinid; 
		 }
		 else {
			 throw new NullPointerException("Id doen't be exist");
		 }
	}

}
