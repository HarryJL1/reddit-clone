package com.hjlowe.harryspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HarrySpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarrySpringProjectApplication.class, args);
	}

}
