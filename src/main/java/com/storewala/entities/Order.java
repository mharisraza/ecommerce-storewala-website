package com.storewala.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "added_date")
	private Date addedDate;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "order_by")
	private Integer orderBy;

	@Column(name = "order_to")
	private Integer orderTo;

	@Column(name = "total_price")
	private Integer totalPrice;
	
	@Column(name = "order_quantities")
	private Integer orderQuantities;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "product_id")
	private Integer productId;
	
	@ManyToMany
	@JoinTable(name = "order_products")
	private List<Product> products;

	public int getId() {
		return id;
	}

	public Integer getPincode() {
		return pincode;
	}

	public String getOrderId() {
		return orderId;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public Integer getOrderTo() {
		return orderTo;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public Integer getOrderQuantities() {
		return orderQuantities;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public void setOrderTo(Integer orderTo) {
		this.orderTo = orderTo;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setOrderQuantities(Integer orderQuantities) {
		this.orderQuantities = orderQuantities;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	

	
	
	
	

	// getters and setters.

	
	

}
