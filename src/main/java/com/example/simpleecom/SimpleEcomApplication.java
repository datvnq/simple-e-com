package com.example.simpleecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SimpleEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleEcomApplication.class, args);
	}

}
