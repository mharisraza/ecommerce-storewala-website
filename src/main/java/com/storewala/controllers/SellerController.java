package com.storewala.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.storewala.daos.CategoryRepository;
import com.storewala.daos.ProductRepository;
import com.storewala.daos.UserRepository;
import com.storewala.entities.Category;
import com.storewala.entities.Product;
import com.storewala.entities.User;

@Controller
@RequestMapping("/seller")
public class SellerController {

	static String usingRandomUUID() {

		UUID randomUUID = UUID.randomUUID();

		return randomUUID.toString().replaceAll("_", "");

	}

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private UserRepository userRepo;

	@GetMapping(value = { "/home", "/" })
	public String sellerHome(Model m, Principal principal) {

		User user = this.userRepo.loadUserByUserName(principal.getName());
		List<Product> sellerProducts = this.productRepo.getSellerAllProducts(user.getId());

		List<Category> categories = this.categoryRepo.getCategories();

		m.addAttribute("title", "Seller Panel | StoreWala");
		m.addAttribute("product", new Product());
		m.addAttribute("categories", categories);
		m.addAttribute("user", user);
		m.addAttribute("products", sellerProducts);
		return "seller/index";
	}

	@PostMapping("/product/new")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,
			@RequestParam("product_images") List<MultipartFile> images,
			@RequestParam(value = "product_category", required = false) Integer selectedProductCategory, Model m,
			HttpSession httpSession, Principal principal,
			RedirectAttributes redirectAttribute) {

		User user = this.userRepo.loadUserByUserName(principal.getName());

		if (result.hasErrors()) {
			httpSession.setAttribute("status", "product-has-errors");
			m.addAttribute("categories", this.categoryRepo.getCategories());
			m.addAttribute("product", product);
			m.addAttribute("products", this.productRepo.getSellerAllProducts(user.getId()));
			return "seller/index";
		}

		if (images.size() > 5) {
			httpSession.setAttribute("status", "images-exceed");
			return "redirect:/seller/home";
		}

		List<String> productImages = new ArrayList<>();

		try {
			for (MultipartFile image : images) {
				String imageName = usingRandomUUID() + "_" + image.getOriginalFilename();
				File savedFile = new ClassPathResource("static/product_upload").getFile();
				Path path = Paths.get(savedFile.getAbsolutePath() + File.separator + imageName);
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				productImages.add(imageName);
			}

			product.setSellerId(user.getId());
			product.setSellerName(user.getName());
			product.setPriceAfterDiscount(product.getDiscountPrice());
			product.setCategory(this.categoryRepo.getById(selectedProductCategory));
			product.setImages(productImages);
			this.productRepo.save(product);
			httpSession.setAttribute("status", "product-added");
			return "redirect:/seller/home?productAdded";

		} catch (Exception e) {
			httpSession.setAttribute("status", "went-wrong");
			e.printStackTrace();
			return "redirect:/seller/home?internalError";

		}

	}
	
	/* 
	 *                         Explanation of add new product method
	 *                         
	 * This above method is a  that handles requests to add a new product.
	 It has several parameters, including the product to be added, the result of validating the product,
	 a list of images for the product, the selected product category, a model object, the current HTTP session,
	 the authenticated user's principal, and a redirect attributes object.

	 The method first checks if the product has any validation errors, and if it does,
	 it adds the product and a list of categories to the model and returns the "seller/index" view.

	 If the number of images for the product exceeds 5, the method sets an attribute in the HTTP session
	 and redirects the user to the home page for sellers.

	 Otherwise, the method loops through the list of images, saves each one to a file,
	 and adds the image name to a list of product images.
	 The method then sets the seller ID, seller name, and category for the product, sets the images list for the product,
	 and saves the product to the database. If any exceptions occur during this process,
	 the method sets an attribute in the HTTP session and redirects the user to the home page for sellers.
	 Finally, the method sets an attribute in the HTTP session, redirects the user to the home page for sellers,
	 and appends a query parameter to the URL indicating that a product was added.
	 
	 */

}
