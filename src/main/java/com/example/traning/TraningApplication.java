package com.example.traning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TraningApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraningApplication.class, args);
	}
}
