package com.storewala;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.storewala.daos.UserRepository;
import com.storewala.entities.User;


@SpringBootApplication
@ComponentScan(basePackages = "com.storewala.*")
public class StoreWalaApplication implements CommandLineRunner {
	
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(StoreWalaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User user = new User();
			
		user.setId(1);
		user.setEmail("admin@storewala.com");
		user.setEnable(true);
		user.setName("Muhammad Haris");
		user.setPhone("1234567890");
		user.setRole("ROLE_ADMIN");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setProfile("admin.png");
		user.setDate(new Date());
		
		this.userRepo.save(user);
		
		
		
	}

}
