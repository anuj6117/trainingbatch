package com.example.demo.utils;

import java.util.Random;

import com.example.demo.constant.Constant;

public class Utility {

	public static int id;

	public static int generateId(int num) {
		Random randomNumber = new Random();
		id = randomNumber.nextInt(num);
		return id;
	}

	public static Boolean isStringNull(String obj) {
		obj = obj.trim();
		if (obj.equals(Constant.EMPTY)) {
			return false;
		} else {
			return true;
		}
	}
	public static Boolean isIntegerNull(Integer obj) {
		if (obj<=0) {
			return false;
		} else {
			return true;
		}
	}
	public static Boolean isLongNull(Long obj) {
		if (obj>0) {
			System.out.println("Long---------------------");
			return true;
		} else {
			return false;
		}
	}
}
