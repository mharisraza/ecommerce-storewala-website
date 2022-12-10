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
			HttpSession httpSession, Principal principal) {

		User user = this.userRepo.loadUserByUserName(principal.getName());

		List<Category> categories = this.categoryRepo.getCategories();
		List<Product> sellerProducts = this.productRepo.getSellerAllProducts(user.getId());

		if (result.hasErrors()) {
			httpSession.setAttribute("status", "product-has-errors");
			m.addAttribute("categories", categories);
			m.addAttribute("product", product);
			m.addAttribute("products", sellerProducts);
			return "seller/index";
		}

		if (images.size() > 5) {
			httpSession.setAttribute("status", "images-exceed");
			return "redirect:/seller/home";
		}

		List<String> productImages = new ArrayList<>();

		try {

			boolean flag = false;

			images.forEach(image -> {

				String imageName = usingRandomUUID() + "_" + image.getOriginalFilename();

				try {

					File savedFile = new ClassPathResource("static/product_upload").getFile();

					Path path = Paths.get(savedFile.getAbsolutePath() + File.separator + imageName);

					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

					productImages.add(imageName);

				} catch (Exception e) {
					httpSession.setAttribute("status", "went-wrong");
					e.printStackTrace();
					return;
				}

			});

			product.setSellerId(user.getId());
			product.setSellerName(user.getName());
			product.setPriceAfterDiscount(product.getDiscountPrice());
			product.setCategory(this.categoryRepo.getById(selectedProductCategory));
			product.setImages(productImages);

			this.productRepo.save(product);
			flag = true;

			if (flag) {
				httpSession.setAttribute("status", "product-added");
				return "redirect:/seller/home?productAdded";
			}

			httpSession.setAttribute("status", "went-wrong");
			return "redirect:/seller/home?internalError";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/seller/home";
	}

}
