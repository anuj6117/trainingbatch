package com.crud.demo.conroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.model.CoinManagement;
import com.crud.demo.service.CoinManagementService;

@RestController
public class CoinManagementController {
	@Autowired
	private CoinManagementService coinManagementService;
	
	
	@RequestMapping(value="/addcurrancy",method=RequestMethod.POST)
	public String addCurrancy(@RequestBody CoinManagement  coinManagement){
		return coinManagementService.addCurreuncy(coinManagement);
	}
	
	@RequestMapping("/getallcurrency")
	public List<CoinManagement> getAllCurrency() {
		return coinManagementService.getAllCurrency();
	}
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	public String updateCurrency(@RequestBody CoinManagement  coinManagement) {
		return coinManagementService.updateCurrency(coinManagement);
	}
	@RequestMapping("/deletecurrency")
	public String deleteCurrency(@RequestParam Integer currency_id) {
		return coinManagementService.deleteCurrency(currency_id);
	}

}
