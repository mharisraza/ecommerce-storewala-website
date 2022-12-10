package com.storewala.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.storewala.daos.OrderRepository;
import com.storewala.daos.ProductRepository;
import com.storewala.entities.Order;
import com.storewala.entities.Product;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ProductRepository productRepo;

	@PostMapping("/processing-order")
	public String processOrder(@RequestParam(name = "pincode", required = false) Integer pinCode,
			@RequestParam("total_price") Integer totalPrice,
			@RequestParam(name = "user_id", required = false) Integer userId,
			@RequestParam(value = "address", name = "address", required = false) String orderAddress,
			@RequestParam("product_ids") List<Integer> products,
			@RequestParam("product_quantities") Integer productQuantities,
			HttpServletResponse response,
			HttpSession httpSession) {
		
		List<Product> allProducts = this.productRepo.findAllById(products);
		
		
		
		
		Order order = new Order();
		
		boolean flag = false;
		
		allProducts.forEach(product -> {
			
			Order existingOrder = this.orderRepo.findByProductId(product.getId());
			
			if(existingOrder != null && existingOrder.getOrderStatus().equals("Pending")) {
				httpSession.setAttribute("status", "already-ordered");
				try {
					response.sendRedirect("/checkout?already-ordered");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	
			order.setOrderTo(product.getSellerId());
		});
		
		


		if (orderAddress == null || orderAddress.equals("") || orderAddress.isEmpty()) {
			httpSession.setAttribute("status", "address-empty");
			return "redirect:/checkout";
		}

		String orderId = "SW" + new Random().nextInt(9999);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 7);

		order.setDeliveryDate(calendar.getTime()); // assigning expected delivery date. (7 days)
		order.setAddedDate(new Date());
		order.setOrderBy(userId);
		order.setPincode(pinCode);
		order.setOrderId(orderId);
		order.setOrderStatus("Pending");
		order.setTotalPrice(totalPrice);
		order.setOrderQuantities(productQuantities);
		order.setProducts(allProducts);
		
		this.orderRepo.save(order);
		flag = true;
		
		if(flag) {
			httpSession.setAttribute("status", "ordered-successfully");
			return "redirect:/MyOrders?itemOrdered";
		}


		httpSession.setAttribute("status", "went-wrong");
		return "redirect:/MyOrders?something-went-wrong";
	}

}
