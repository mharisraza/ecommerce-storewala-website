package com.storewala.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ErrorsController implements ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest request, @ModelAttribute("error") String error, Model m) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == 404) {
				m.addAttribute("title", "404 Not Found | StoreWala");
				return "errors/404";
			}

		}
		m.addAttribute("title", "Something Went Wrong | StoreWala");
		return "errors/500";

	}
}
