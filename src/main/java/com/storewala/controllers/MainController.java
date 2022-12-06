/* This is Main Controller, here are every view that related to public url. */

package com.storewala.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

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

import com.storewala.dao.CategoryRepository;
import com.storewala.dao.ProductRepository;
import com.storewala.dao.UnbanRequestRepository;
import com.storewala.dao.UserRepository;
import com.storewala.entities.Category;
import com.storewala.entities.Product;
import com.storewala.entities.UnbanRequest;
import com.storewala.entities.User;


@Controller
public class MainController {
	
	/* Autowired */

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UnbanRequestRepository unbanRequestRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	/* ==================== */

	
	
	// home page view.
	
	@GetMapping(value = { "/", "/home" })
	public String firstHomeView(Model m, Principal principal) {
		
		if(principal!=null) {
			m.addAttribute("user", this.userRepo.loadUserByUserName(principal.getName()));
		}

		List<Product> products = this.productRepo.getProducts();
		List<Category> categories = this.categoryRepo.getCategories();
		

		m.addAttribute("title", "StoreWala | Start Shopping Now!");
		m.addAttribute("categories", categories);
		m.addAttribute("products", products);
		return "index.html";
	}
	
	
	// register page view.

	@GetMapping("/register")
	public String registerPage(Model m) {

		m.addAttribute("title", "Register | StoreWala");
		m.addAttribute("user", new User());
		return "register";
	}
	
	
	
	// register processing logic.

	@PostMapping("/process-registration")
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redir,
			@RequestParam("confirm_password") String confirmPassword, @RequestParam("user_role") String role, Model m,
			HttpSession httpSession) {

		try {

			if (result.hasErrors()) {
				m.addAttribute("user", user);
				return "register";
			}

			if (role.equals("non-selected")) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "role-not-select");
				return "redirect:/register";
			}

			if (confirmPassword.equals("") || confirmPassword == null) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "cp-empty");
				return "redirect:/register";
			}

			if (!user.getPassword().equals(confirmPassword)) {
				m.addAttribute("user", user);
				httpSession.setAttribute("status", "cp-not-match");
				return "redirect:/register";
			}

			if (role.equals("customer")) {
				user.setRole("ROLE_CUSTOMER");
			} else {
				user.setRole("ROLE_SELLER");
			}

			user.setEnable(true);
			user.setProfile("user.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setDate(new Date());

			this.userRepo.save(user);

		} catch (DataIntegrityViolationException e) {
			httpSession.setAttribute("status", "email-exist");
			m.addAttribute("user", user);
			return "redirect:/register";

		} catch (Exception e) {
			httpSession.setAttribute("status", "went-wrong");
			e.printStackTrace();
		}

		httpSession.setAttribute("status", "registered-success");
		return "redirect:/register";
	}
	
	
	
	// login view [Logic already done by Spring Security internally]

	@GetMapping("/login")
	public String loginPage(Model m) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {

			User user = this.userRepo.loadUserByUserName(auth.getName());

			if (user.getRole().equals("ROLE_CUSTOMER")) {
				return "redirect:/customer/home";
			}
			if (user.getRole().equals("ROLE_ADMIN")) {
				return "redirect:/admin/home";
			}
			if (user.getRole().equals("ROLE_SELLER")) {
				return "redirect:/seller/home";
			}

		}

		m.addAttribute("title", "Login | StoreWala");
		return "login";
	}
	
	
	
	// search view + logic.

	@GetMapping("/search")
	public String searchProducts(@RequestParam(value = "category", required = false) Integer categoryType,
			@RequestParam(name = "query", required = false) String query) {

		return "search_product";
	}
	
	
	// unban request view

	@GetMapping("/unban-request")
	public String unbanRequestView(Model m) {
		m.addAttribute("title", "Unban Request | StoreWala");
		m.addAttribute("unbanRequest", new UnbanRequest());
		return "unban";
	}
	
	// unban request processing logic.

	@PostMapping("/processing-unban-request")
	public String processUnbanRequest(@Valid @ModelAttribute("unbanRequest") UnbanRequest unbanRequest,
			BindingResult result, HttpSession httpSession) {

		if (result.hasErrors()) {
			return "unban";
		}

		boolean flag = false;

		User user = this.userRepo.loadUserByUserName(unbanRequest.getEmail());

		if (user == null) {
			httpSession.setAttribute("status", "user-not-exist");
			return "redirect:/unban-request?emailNotValid";
		}

		if (user.isEnable()) {
			httpSession.setAttribute("status", "user-not-suspend");
			return "redirect:/unban-request?userIsNotBanned";
		}


		this.unbanRequestRepo.save(unbanRequest);
		flag = true;

		if (flag) {
			httpSession.setAttribute("status", "message-send-successfully");
		}

		return "redirect:/unban-request";
	}
	
	@GetMapping("/profile")
	public String showProfile(Model m, Principal principal, HttpSession httpSession) {
		
		if(principal==null) {
			httpSession.setAttribute("status", "not-login");
			return "redirect:/home";
		}
		
		User user = this.userRepo.loadUserByUserName(principal.getName());
		
		m.addAttribute("title", user.getName()+" | StoreWala");
		m.addAttribute("user", user);
		return "profile";
	}
	
	@GetMapping("/checkout")
	public String checkOut(Model m, Principal principal, HttpSession httpSession) {
		
		if(principal==null) {
			httpSession.setAttribute("status", "not-login");
			return "redirect:/home";
		}
		
		User user = this.userRepo.loadUserByUserName(principal.getName());
		

		
		if(user.getRole().equals("ROLE_SELLER") || user.getRole().equals("ROLE_ADMIN")) {
			httpSession.setAttribute("status", user.getRole().equals("ROLE_SELLER") ? "seller-not-allow" : "admin-not-allow");
			return user.getRole().equals("ROLE_SELLER") ? "redirect:/seller/home" : "redirect:/admin/home";
		}
		
	
		

		m.addAttribute("user", user);
		m.addAttribute("title", "Checkout | StoreWala");
		return "checkout";
	}
	
	@GetMapping("/showProduct")
	public String productDetail(@RequestParam(value = "product_id", required = false) Integer id) {
		
		Product product = this.productRepo.getById(id);
		
		return "show_product";
	}

}
