package com.example.demo.services.deleteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.userModel.RoleModel;
import com.example.demo.model.userModel.UserModel;
import com.example.demo.model.userModel.WalletModel;
import com.example.demo.repoINterface.RoleRepository;
import com.example.demo.repoINterface.UserRepository;
import com.example.demo.repoINterface.WalletRepostiory;

@Service
public class DeleteService {

	@Autowired
	UserRepository userData;
	
	@Autowired 
	WalletRepostiory walletData; 
	
	@Autowired
	RoleRepository roalData;
	public String deleteById(Long id)
	{
		UserModel record=userData.findOne(id);
		if(record==null){
			throw new NullPointerException("id not found");
		}
		
		userData.delete(record.getId());	
		return "success";
		
		}

}
