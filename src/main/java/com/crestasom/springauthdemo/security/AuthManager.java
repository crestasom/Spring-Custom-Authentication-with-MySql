package com.crestasom.springauthdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.crestasom.springauthdemo.service.UserService;

@Component
public class AuthManager implements AuthenticationProvider {
	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		if (userService.checkUser(authentication.getPrincipal().toString())) {
			return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
					authentication.getCredentials());
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
