package com.crestasom.springauthdemo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crestasom.springauthdemo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String userName);
}
