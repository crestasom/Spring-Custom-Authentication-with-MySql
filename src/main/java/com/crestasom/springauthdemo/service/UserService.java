package com.crestasom.springauthdemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crestasom.springauthdemo.entity.User;
import com.crestasom.springauthdemo.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	@Autowired
	PasswordEncoder encoder;

	public boolean checkUser(String username) {
		return true;
	}

	public boolean checkLogin(String userName, String password) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepo.findByUserName(userName);
		if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
			return true;
		}
		return false;
	}
}
