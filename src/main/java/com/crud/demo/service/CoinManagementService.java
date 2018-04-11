package com.crud.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.conroller.RoleController;
import com.crud.demo.jpaRepositories.CoinManagementJpaRepository;
import com.crud.demo.model.CoinManagement;

@Service
public class CoinManagementService {
	@Autowired
	private CoinManagementJpaRepository coinManagementJpaRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CoinManagementService.class);

	public Map<String, Object> addCurreuncy(CoinManagement coinManagement) {
		Boolean isSuccess = false;
		Map<String, Object> map = new HashMap<>();
		Set<String> alreadyAddedCurrency=coinManagementJpaRepository.findByCoinName();
		Set<String> alreadyAddedSymbol=coinManagementJpaRepository.findBySymbol();
		if(coinManagement.getInitialSupply()!=null&&coinManagement.getPrice()!=null) {
		if(!alreadyAddedCurrency.contains(coinManagement.getCoinName())&&!alreadyAddedSymbol.contains(coinManagement.getSymbol())&&(coinManagement.getInitialSupply()>0)&&(coinManagement.getPrice()>0)) 
		{
			coinManagementJpaRepository.save(coinManagement);
			map.put("Result", "Coin details saved successfully ");
			isSuccess = true;
			map.put("isSuccess", isSuccess);
			LOGGER.info("Method on service:::::::::::::Coin details saved successfully");
		}} else {
			map.put("Result", "Coin details unable to save");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Method on service:::::::::::::Coin details unable to save");
		}
		return map;
	}

	public Map<String, Object> getAllCurrency() {
		Boolean isSuccess = false;
		Map<String, Object> map = new HashMap<>();
		
			List<CoinManagement> coinManagementList = coinManagementJpaRepository.findAll();
			if (!coinManagementList.isEmpty()) {
				isSuccess = true;
				map.put("Result", coinManagementList);
				map.put("isSuccess", isSuccess);
				LOGGER.info("Method on service:::::::::::::All Coin details send successfully");
			} else {
				map.put("Result", coinManagementList);
				map.put("isSuccess", false);
				LOGGER.error("Method on service:::::::::::::unable to fetch all user details ");
			}
		
		return map;
	}

	public Map<String, Object> updateCurrency(CoinManagement coinManagement) {
		Boolean isSuccess = false;
		Map<String, Object> map = new HashMap<>();
			
		Set<String> alreadyAddedCurrency=coinManagementJpaRepository.findByCoinName();
		Set<String> alreadyAddedSymbol=coinManagementJpaRepository.findBySymbol();
		CoinManagement existingCoinManagement = coinManagementJpaRepository.findOne(coinManagement.getCoinId());
		if(coinManagement.getInitialSupply()!=null&&coinManagement.getPrice()!=null&&alreadyAddedCurrency.contains(coinManagement.getCoinName())&&alreadyAddedSymbol.contains(coinManagement.getSymbol())) {
			System.out.println(":::::"+existingCoinManagement);
			if (existingCoinManagement != null&&!alreadyAddedCurrency.contains(coinManagement.getCoinName())&&!alreadyAddedSymbol.contains(coinManagement.getSymbol())&&(coinManagement.getInitialSupply()>0)) 
			{
			Integer updatedSupply=existingCoinManagement.getInitialSupply()+coinManagement.getInitialSupply();
				existingCoinManagement.setInitialSupply(updatedSupply);
				existingCoinManagement.setCoinName(coinManagement.getCoinName());
				existingCoinManagement.setPrice(coinManagement.getPrice());
				existingCoinManagement.setSymbol(coinManagement.getSymbol());
				coinManagementJpaRepository.save(existingCoinManagement);
				map.put("Result", "CoinManagement record updated successfully");
				isSuccess = true;
				map.put("isSuccess", isSuccess);
				LOGGER.info("Method on service:::::::::::::Coin details updated successfully");
			}}
			 else {
			map.put("Result", "Sorry user doesnot exist for updation");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Method on service:::::::::::::Sorry user doesnot exist for updation  or something went wrong");	
			 }
			return map;
		}
	

	public Map<String, Object> deleteCurrency(Integer currencyId)

	{
		Map<String, Object> map = new HashMap<>();
		Boolean isSuccess = false;
		
			if (coinManagementJpaRepository.findOne(currencyId) != null) {
				coinManagementJpaRepository.delete(currencyId);
				map.put("Result", "Corrency record deleted successfully");
				isSuccess = true;
				map.put("isSuccess", isSuccess);
				LOGGER.info("Method on service:::::::::::::Corrency record deleted successfully ");
			}
			 else {
			map.put("Result", "Currency records not deleted ");
			map.put("isSuccess", isSuccess);
			LOGGER.error("Method on service:::::::::::::Currency records not deleted ");
			 }
			return map;
		}

	public Map<String, Object> getCurrencyById(Integer coinId) {
		Map<String, Object> map = new HashMap<>();
		Boolean isSuccess = false;
		CoinManagement coinManagement=coinManagementJpaRepository.findOne(coinId);
		if(coinManagement!=null)
		{
			map.put("Result", coinManagement);
			map.put("isSuccess", true);
			LOGGER.info("Message on service::::::::::::::::::Successfully fetched coin by id");
		}
		else
		{
			map.put("Result", null);
			map.put("isSuccess", isSuccess);
			LOGGER.error("Message on service::::::::::::::::::something went wrong in finding coin based on id");
		}
		return map;
	}
	
	/*******************************************************************************************/
	
public void testUpdateCurrency(CoinManagement newCoinManagement)
{
	Set<String> alreadyAddedCurrency=coinManagementJpaRepository.findByCoinName();
	Set<String> alreadyAddedSymbol=coinManagementJpaRepository.findBySymbol();
	CoinManagement existingCoinManagement = coinManagementJpaRepository.findOne(newCoinManagement.getCoinId());
	if(existingCoinManagement!=null)
	{
		if(alreadyAddedCurrency.contains(newCoinManagement.getCoinName())&&alreadyAddedSymbol.contains(newCoinManagement.getSymbol()))
		{
			
		}
		else if(alreadyAddedCurrency.contains(newCoinManagement.getCoinName()) || alreadyAddedSymbol.contains(newCoinManagement.getSymbol()))
		{
			
		}
	}
	else
	{
		System.out.println("sorry user does not exist");
	}
	
}
}

