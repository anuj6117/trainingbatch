package com.example.demo.utils;

import java.util.Random;

public class Utility {

	public static int id;

	public static int generateId(int num) {
		Random randomNumber = new Random();
		id = randomNumber.nextInt(num);
		return id;
	}

	public static Boolean ifNull(Object obj) {
		if (obj.equals(null)) {
			return false;
		} else {
			return true;
		}
	}
}
