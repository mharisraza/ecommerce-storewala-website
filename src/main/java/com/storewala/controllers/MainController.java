package com.storewala.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
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

}
