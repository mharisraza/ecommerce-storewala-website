package com.storewala;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.storewala.*")
public class StoreWalaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StoreWalaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
