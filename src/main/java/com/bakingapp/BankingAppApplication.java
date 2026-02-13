package com.bakingapp;

import com.bakingapp.mapper.AccountMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingAppApplication {

	@Bean
	public AccountMapper accountMapper(){
		return new AccountMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

}
