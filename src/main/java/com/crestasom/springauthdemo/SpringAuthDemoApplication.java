package com.crestasom.springauthdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.crestasom.springauthdemo.entity.User;
import com.crestasom.springauthdemo.repo.UserRepo;

@SpringBootApplication
public class SpringAuthDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAuthDemoApplication.class, args);
	}
		
	
	

}
