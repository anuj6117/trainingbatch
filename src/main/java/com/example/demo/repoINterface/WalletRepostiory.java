package com.example.demo.repoINterface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.userModel.WalletModel;

public interface WalletRepostiory extends JpaRepository<WalletModel, Long>{

	//public List<WalletModel> findAllByUser_model_id(Long id);
	public WalletModel findOneByWalletType(String walletType);
	public WalletModel findByWalletIdAndWalletType(Long id,String Type);
}
