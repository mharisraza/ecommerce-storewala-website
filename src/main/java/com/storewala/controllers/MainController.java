package com.storewala.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.storewala.dao.UserRepository;
import com.storewala.entities.User;

@Controller
public class MainController {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String firstHomeView(Model m) {
		m.addAttribute("title", "StoreWala | Start Shopping Now!");
		return "index.html";
	}
	
	@GetMapping("/home")
	public String scnHomeView(Model m) {
		m.addAttribute("title", "StoreWala | Start Shopping Now!");
		return "index.html";
	}
	
	@GetMapping("/register")
	public String registerPage(Model m) {
		
		m.addAttribute("title", "Register | StoreWala");
		m.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/process-registration")
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam("user_role") String role, Model m, HttpSession httpSession) {
		
		try {
			
			if(result.hasErrors()) {
				m.addAttribute("user", user);
				return "register";
			}
			
			Integer checkIfEmailExist = this.userRepo.checkIfEmailExist(user.getEmail());
			
			if(checkIfEmailExist > 1) {
				httpSession.setAttribute("status", "email-exist");
				return "redidrect:/register";
			}
			
			if(role.equals("customer")) {
				user.setRole("ROLE_CUSTOMER");
			}
			else {
				user.setRole("ROLE_SELLER");
			}
			
			user.setEnable(true);
			user.setProfile("user.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			this.userRepo.save(user);
			
			
		} catch(Exception e) {
			httpSession.setAttribute("status", "went-wrong");
		      e.printStackTrace();
		}
		
		httpSession.setAttribute("status", "registered-success");
		return "redirect:/register";
	}

}
