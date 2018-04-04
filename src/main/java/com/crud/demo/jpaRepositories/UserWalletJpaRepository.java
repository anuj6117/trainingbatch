package com.crud.demo.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.demo.model.UserWallet;



public interface UserWalletJpaRepository extends JpaRepository<UserWallet, Integer>{
/*@Query("select u from UserWallet u where u.wallet_id=:id and u.walletType=:walletType")
UserWallet findByIdAndWalletType(@Param("id") Integer id,@Param("walletType") String walletType);*/
  
	@Query("select u from UserWallet u where u.walletId=:walletId and u.walletType=:walletType")
	UserWallet findByWalletIdAndWalletType(@Param("walletId") Integer walletId,@Param("walletType") String walletType);	



UserWallet findByWalletType(String walletType);

}
