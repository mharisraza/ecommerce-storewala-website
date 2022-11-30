package com.storewala.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class SellerController {
	
	@GetMapping(value = {"/home", "/"})
	public String sellerHome(Model m) {
		m.addAttribute("title", "Seller Panel | StoreWala");
		return "seller/index";
	}
	


}
