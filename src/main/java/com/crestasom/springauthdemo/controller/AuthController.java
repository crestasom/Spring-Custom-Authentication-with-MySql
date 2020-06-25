package com.crestasom.springauthdemo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.crestasom.springauthdemo.entity.User;
import com.crestasom.springauthdemo.repo.UserRepo;
import com.crestasom.springauthdemo.service.UserService;

@Controller
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UserRepo repo;

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "login";
	}

	@GetMapping("/logout")
	public RedirectView logout(RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("message", "You are now Logged out.");
		// redirectAttributes.addAttribute("attribute", "redirectWithRedirectView");
		return new RedirectView("login");
	}
	@PostMapping("/check-login")
	@ResponseBody
	public RedirectView checkLogin(HttpServletRequest request, @RequestParam("username") String userName,
			@RequestParam String password, RedirectAttributes redirectAttributes) {

		if (userService.checkLogin(userName, password)) {
			System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
			if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext()
					.getAuthentication().getClass().equals(AnonymousAuthenticationToken.class)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password,
						new ArrayList<>());
				SecurityContextHolder.getContext().setAuthentication(token);
			}
			redirectAttributes.addAttribute("message", "Login Successful");
			// redirectAttributes.addAttribute("attribute", "redirectWithRedirectView");
			return new RedirectView("hello");

		}
		redirectAttributes.addAttribute("message", "Invalid Username or Password");
		// redirectAttributes.addAttribute("attribute", "redirectWithRedirectView");
		return new RedirectView("login");
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello(@RequestParam(required = false) String message) {
		return "Hello from Secure Page\n" + message;
	}

	@GetMapping("/add-user")
	@ResponseBody
	public void addDemoUser(@RequestParam("username") String userName, @RequestParam String password) {
		repo.save(new User(userName, encoder.encode(password)));
	}

}
