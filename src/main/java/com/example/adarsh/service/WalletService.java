package com.example.adarsh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adarsh.Dto.WalletDto;
import com.example.adarsh.domain.User;
import com.example.adarsh.domain.Wallet;
import com.example.adarsh.repository.UserRepository;
import com.example.adarsh.repository.WalletRepository;

@Service

public class WalletService {

	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	UserRepository userRepository;

	/*
	 * public Wallet addWallet(Wallet wallet) {
	 * 
	 * Wallet wallet1 = new Wallet(); wallet1.setBalance(wallet.getBalance());
	 * wallet1.setRandomId(wallet.getRandomId());
	 * wallet1.setShadowBalance(wallet.getShadowBalance());
	 * wallet1.setWalletType(wallet.getWalletType());
	 * 
	 * return walletRepository.save(wallet); }
	 */

	public String addWalletToUser(WalletDto walletDto) {

		boolean check = true;

		User user = userRepository.findOne(walletDto.getUserId());
		if (user == null)
			throw new NullPointerException("id not found");
		List<Wallet> walletTypeOfuser = user.getWallet();
		for (Wallet getwallettype : walletTypeOfuser) {
			if (walletDto.getWalletType().equals(getwallettype.getWalletType())) {
				check = false;
				throw new RuntimeException("wallet allready exist");
			}
		}
		if (check) {
			Wallet wallet1 = new Wallet();
			wallet1.setBalance(walletDto.getBalance());
			wallet1.setWalletType(walletDto.getWalletType());
			wallet1.setUser(user);
			/* wallet.setRandomId(randemId); */
			user.getWallet().add(wallet1);
			User result = userRepository.save(user);
			if (result != null)
				return "success";
		}
		return "error";
	}

	/*
	 * public String depositMoney(Wallet wallet) { Wallet money =
	 * walletRepository.findOne(wallet.getWalletId()); if (money == null) throw new
	 * NullPointerException("id not found"); int moneyBalance = money.getBalance() +
	 * wallet.getBalance(); money.setBalance(moneyBalance);
	 * money.setShadowBalance(moneyBalance);
	 * 
	 * Wallet result = walletRepository.save(money); if (result != null) return
	 * "success"; return "error"; }
	 */

	public String depositMoney(WalletDto walletDto) {

		User user = userRepository.findOne(walletDto.getUserId());
		if (user == null)
			throw new NullPointerException("id not found");
		List<Wallet> walletTypeOfuser = user.getWallet();
		for (Wallet getwallettype : walletTypeOfuser) {
			if (walletDto.getWalletType().equals(getwallettype.getWalletType())) {

				Double moneyBalance = getwallettype.getBalance() + walletDto.getBalance();
				getwallettype.setBalance(moneyBalance);
				getwallettype.setShadowBalance(moneyBalance);
				// getwallettype.setBalance(walletDto.getBalance());
				Wallet result = walletRepository.save(getwallettype);
				if (result != null) {
					return "success";
				}
			}

		}
		return "error";
	}

	/*
	 * public String withdrawMoney(int id, int balance) {
	 * 
	 * Wallet money2 = walletRepository.findOne(id); if (money2 == null) throw new
	 * NullPointerException("id not found"); int money2Balance =
	 * money2.getBalance(); money2Balance = money2Balance - balance;
	 * money2.setBalance(money2Balance); money2.setShadowBalance(money2Balance);
	 * Wallet result = walletRepository.save(money2); if (result != null) return
	 * "success"; return "error"; }
	 */

	public String withdrawMoney(WalletDto walletDto) {

		User user = userRepository.findOne(walletDto.getUserId());
		if (user == null)
			throw new NullPointerException("id not found");
		List<Wallet> walletTypeOfuser = user.getWallet();
		for (Wallet getwallettype : walletTypeOfuser) {
			if (walletDto.getWalletType().equals(getwallettype.getWalletType())) {

				Double moneyBalance = getwallettype.getBalance() - walletDto.getBalance();
				getwallettype.setBalance(moneyBalance);
				getwallettype.setShadowBalance(moneyBalance);
				// getwallettype.setBalance(walletDto.getBalance());
				Wallet result = walletRepository.save(getwallettype);
				if (result != null) {
					return "success";
				}
			}

		}
		return "error";
	}

}
