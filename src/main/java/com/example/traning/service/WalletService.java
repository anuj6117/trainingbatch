package com.example.traning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Coin;
import com.example.traning.domain.Register;
import com.example.traning.domain.Wallet;
import com.example.traning.dto.UserWalletDto;
import com.example.traning.repository.CoinRepository;
import com.example.traning.repository.RegisterRepository;
import com.example.traning.repository.WalletRepository;
@Service
public class WalletService {
	
	@Autowired
	WalletRepository walletrepository;
	@Autowired
	RegisterRepository registerrepository;
	
	@Autowired
	CoinRepository coinrepository;
	
	
	// Create a new Wallet
	public Wallet createwallet(UserWalletDto userwalletdto) 
	{
		
		Register userData=registerrepository.findByUserId(userwalletdto.getUserId());
		if(userData==null)
		{
			throw new NullPointerException("Invalid User Id!");
		}
		boolean	flag=true;
		Wallet wall =new Wallet();
		Wallet will=null;
        List<Wallet> listwalle=userData.getWall();
        for(Wallet walletModel:listwalle) {
        	System.out.println("--------7");
        	System.out.println(walletModel.getWalletType());
        	System.out.println(userwalletdto.getWalletType());
			if(walletModel.getWalletType().equalsIgnoreCase((userwalletdto.getWalletType())))
					{
					throw new NullPointerException("This Wallet type Already Exist");
					}
				wall.setWalletType(userwalletdto.getWalletType());
				wall.setRegister(userData); 	
		}
        List<Coin> allCoinsList=coinrepository.findAll();
		for(Coin allCoins:allCoinsList) {
			if(allCoins.getCoinName().equalsIgnoreCase(userwalletdto.getWalletType())) {
				flag=false;
				break;
			}
		}	
        if(flag)
			throw new NullPointerException("This walletType-Currency Doesn't exist;");
        
		return walletrepository.save(wall);
	}


	public Wallet depositwallet(UserWalletDto userwalletdto) {
		Wallet mod = null;
		Register userData=registerrepository.findByUserId(userwalletdto.getUserId());
		
		List<Wallet> listmodel=userData.getWall();
		
		boolean flag=false;
		
		String newWalletType=userwalletdto.getWalletType();
		
        for(Wallet model:listmodel) {
        	
        	System.out.println("all wallets"+model.getWalletType());
		
        	if(model.getWalletType().equalsIgnoreCase(newWalletType)) {
//				System.out.println(model.getWalletType()+"-----"+newWalletType);
				flag=true;
//				System.out.println("hello");
//				System.out.println(" asdfafd"+userwalletdto.getAmount()+"asdf "+model.getAmount());
				Long newBalance=userwalletdto.getAmount()+ model.getAmount();
//				System.out.println("hit 987");
				Long newShadowbal=newBalance;
				model.setAmount(newBalance);
				model.setShadowBalance(newShadowbal);
				System.out.println(" "+newShadowbal+" "+newBalance);
				return walletrepository.save(model);
			}
		}
			if(flag==false) {
				 throw new NullPointerException("This Wallet Type Doesn't exist");
			}
		return null;
	}


	
	
	public Wallet withdrawallet(UserWalletDto userwalletdto) {
		Register userData=registerrepository.findByUserId(userwalletdto.getUserId());
		List<Wallet> listmodel=userData.getWall();
		boolean flag=false;
		Wallet  wall =null;
		String newWalletType=userwalletdto.getWalletType();
		for(Wallet model:listmodel) 
		{
			if(model.getWalletType().equalsIgnoreCase(newWalletType))
			{
			if(model.getAmount()>=userwalletdto.getAmount()) {
			flag=true;
		    long newBalnce=model.getAmount()-userwalletdto.getAmount();
		    model.setAmount(newBalnce);
		    model.setShadowBalance(newBalnce);
		   return  walletrepository.save(model);}
		    else 
		    {
			throw new UsernameNotFoundException("Not Enough Balance");
		    }
		  }
			
		}
		 if(flag==false){
			 throw new NullPointerException("This Wallet Type Doesn't exist");
		}	
		return null;
		}
		
	}