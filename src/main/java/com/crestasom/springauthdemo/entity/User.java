package com.crestasom.springauthdemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String userName;
	private String password;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
}
