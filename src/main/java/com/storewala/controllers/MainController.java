package com.storewala.controllers;



import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.storewala.dao.UserRepository;
import com.storewala.entities.User;

@Controller
public class MainController {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping(value = {"/", "/home"})
	public String firstHomeView(Model m) {
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
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redir, @RequestParam("confirm_password") String confirmPassword, @RequestParam("user_role") String role, Model m, HttpSession httpSession)  {
		
		try {
			
			if(result.hasErrors()) {
				redir.addFlashAttribute("user", user);
				return "redirect:/register";
			}
			
			if(role.equals("non-selected")) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "role-not-select");
				return "redirect:/register";
			}
			
			if(confirmPassword.equals("") || confirmPassword==null) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "cp-empty");
				return "redirect:/register";
			}
			
			if(!user.getPassword().equals(confirmPassword)) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "cp-not-match");
				return "redirect:/register";
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
			
			

		} catch(DataIntegrityViolationException e) {
			httpSession.setAttribute("status", "email-exist");
			m.addAttribute("user", user);
			return "redirect:/register";
			
		} catch(Exception e) {
			httpSession.setAttribute("status", "went-wrong");
			e.printStackTrace();
		}
		
		
		
		httpSession.setAttribute("status", "registered-success");
		return "redirect:/register";
	}
	
	@GetMapping("/login")
	public String loginPage(Model m) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			
			User user = this.userRepo.loadUserByUserName(auth.getName());
			
			if(user.getRole().equals("ROLE_CUSTOMER")) {
				return "redirect:/customer/home";
			}
			if(user.getRole().equals("ROLE_ADMIN")) {
				return "redirect:/admin/home";
			}
			if(user.getRole().equals("ROLE_SELLER")) {
				return "redirect:/seller/home";
			}
			
		}
		
		m.addAttribute("title", "Login | StoreWala");
		return "login";
	}

}
