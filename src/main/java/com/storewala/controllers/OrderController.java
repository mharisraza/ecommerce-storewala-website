package com.storewala.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	
	@PostMapping("/processing-order")
	public String processOrder() {
		return "redirect:/orderStatus?itemOrdered";
	}

}
