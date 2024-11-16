package com.lecsures.section2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class EazyBankBackedApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackedApplication.class, args);
	}

}
