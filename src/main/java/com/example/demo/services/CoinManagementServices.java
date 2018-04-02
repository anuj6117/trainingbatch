package com.example.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.CoinManagementModel;
import com.example.demo.repoINterface.CoinManagementRepository;

@Service
public class CoinManagementServices {

@Autowired
CoinManagementRepository coinDate;

public Map<String,Object>  addAllCoinData(CoinManagementModel data)
{
	if(data.getCoinName().equals("")||data.getSymble().equals(""))
		throw new NullPointerException("null data found");
		Map<String,Object> result= new HashMap();
	CoinManagementModel model=coinDate.findByCoinName(data.getCoinName());
	if(model!=null)
		throw new NullPointerException("duplicated found");
	CoinManagementModel coinresult=coinDate.save(data);
		 result.put("result","success");
		return result;
}
public Map<String,Object>  deleteCoinById(Long id)
{Map<String,Object> result= new HashMap();
	CoinManagementModel coinresult=coinDate.findOne(id);
	if(coinresult==null) 
		throw new NullPointerException("id not matched");
	coinDate.delete(id);
	result.put("result","success");
	return result;
	
	
}

public Map<String,Object>  updataCoinData(CoinManagementModel data)
{Map<String,Object> result= new HashMap();
	CoinManagementModel model=coinDate.findByIdAndCoinName(data.getId(), data.getCoinName());
	if(model==null)
		throw new NullPointerException("currency not matched");
	model.setPrice(data.getPrice());
	CoinManagementModel coinresult= coinDate.save(model);
	
		result.put("result","success");
	return result;

	
}
public List<CoinManagementModel> findAllCoin()
{
	List<CoinManagementModel> data=coinDate.findAll();
	if(data.isEmpty())
		throw new NullPointerException("data not....");
	System.out.println(data);
	return coinDate.findAll();
}
}
