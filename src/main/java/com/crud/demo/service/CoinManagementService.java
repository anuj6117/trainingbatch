package com.crud.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.model.CoinManagement;

@Service
public class CoinManagementService {
@Autowired
private CoinManagementJpaRepository coinManagementJpaRepository;



public String addCurreuncy(CoinManagement coinManagement) {
	coinManagementJpaRepository.save(coinManagement);
	return "Coin details saved successfully ";
}

 public List<CoinManagement> getAllCurrency() {
	
	return coinManagementJpaRepository.findAll();
}

public String updateCurrency(CoinManagement  coinManagement) {
	String result="Sorry You don't have  result to update";
	CoinManagement existingCoinManagement=coinManagementJpaRepository.findOne(coinManagement.getC_id());
	if(existingCoinManagement!=null)
	{    existingCoinManagement.setInitialSupply(coinManagement.getInitialSupply());
	existingCoinManagement.setCoinName(coinManagement.getCoinName());
	existingCoinManagement.setPrice(coinManagement.getPrice());
	existingCoinManagement.setSymbol(coinManagement.getSymbol());
		coinManagementJpaRepository.save(existingCoinManagement);
	 result="CoinManagement record updated successfully";
	}
	return result;
}

public String deleteCurrency(Integer currency_id) 
{
	String result="Not have this result";
	if(coinManagementJpaRepository.findOne(currency_id)!=null)
	{
	coinManagementJpaRepository.delete(currency_id);
	 result="Corrency record deleted successfully";
	}
	return result;
}
}
