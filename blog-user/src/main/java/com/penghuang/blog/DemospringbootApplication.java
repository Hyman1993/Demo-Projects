package com.penghuang.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.penghuang.blog.*")
public class DemospringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemospringbootApplication.class, args);
	}

}

