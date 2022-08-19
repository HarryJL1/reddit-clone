package com.hjlowe.harryspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableAsync
@EnableSwagger2
public class HarrySpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarrySpringProjectApplication.class, args);
	}

}
