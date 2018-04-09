package com.crud.demo.conroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	
	@RequestMapping("/applogin")
	public void test()
	{
		System.out.println(":::::::::::::::::::::::::::::::Security controller hit");
	}

}
