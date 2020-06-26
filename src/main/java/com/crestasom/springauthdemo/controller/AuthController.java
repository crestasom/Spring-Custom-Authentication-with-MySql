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
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public RedirectView logout(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "You are now Logged out.");
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
			redirectAttributes.addFlashAttribute("message", "Login Successful");
			return new RedirectView("hello");

		}
		redirectAttributes.addFlashAttribute("message", "Invalid Username or Password");
		return new RedirectView("login");
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello(Model model) {
		String msg = model.getAttribute("message") == null ? "" : model.getAttribute("message") + "";

		return "Hello from Secure Page\n" + msg;
	}

	@GetMapping("/add-user")
	@ResponseBody
	public void addDemoUser(@RequestParam("username") String userName, @RequestParam String password) {
		repo.save(new User(userName, encoder.encode(password)));
	}

}
