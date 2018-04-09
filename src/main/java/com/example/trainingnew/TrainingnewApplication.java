package com.example.trainingnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
public class TrainingnewApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingnewApplication.class, args);
	}
}
