package com.storewala.entities;


import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "product_name")
	@NotBlank(message = "Product name is required.")
	@Size(min = 10, max = 40)
	private String name;

	@Column(name = "product_highlights", length = 200)
	@NotBlank(message = "Product highlights is required.")
	@Size(min = 10)
	private String highlights;

	@Column(name = "product_description", length = Integer.MAX_VALUE)
	@NotBlank(message = "Product description is required.")
	@Size(min = 10, message = "Product description should be have atleast 10 characters.")
	private String description;

	@Column(name = "product_price")
	@NotNull(message = "Product price is required.")
	private Integer price;

	@Column(name = "product_discount")
	@NotNull(message = "Product discount is required.")
	private Integer discount;

	@Column(name = "product_stocks")
	@NotNull(message = "Product stocks is required.")
	private Integer stocks;

	@ElementCollection
	@CollectionTable(name = "product_images")
	private Collection<String> images;

	@Column(name = "product_seller_name")
	private String sellerName;

	@Column(name = "product_seller_id")
	private Integer sellerId;

	@Column(name = "price_after_discount")
	private Integer priceAfterDiscount;

	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getStocks() {
		return stocks;
	}

	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	public Collection<String> getImages() {
		return images;
	}

	public void setImages(Collection<String> images) {
		this.images = images;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(Integer priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", highlights=" + highlights + ", description=" + description
				+ ", price=" + price + ", discount=" + discount + ", stocks=" + stocks + ", images=" + images
				+ ", sellerName=" + sellerName + ", sellerId=" + sellerId + ", priceAfterDiscount=" + priceAfterDiscount
				+ ", category=" + category + "]";
	}

	/* getting price after applying discount */
	
	public int getDiscountPrice() {
		int discount = (int) ((this.getDiscount()/100.0)*this.getPrice());
		return this.getPrice()-discount;
	}
	
	public String highlightShorter(String highlight) {
		
		String[] highlights = highlight.split(" ");
		
		if(highlights.length > 14) {
			
			String res = "";
			
			for(int i = 0; i < 14 ; i++) {
				
				res += highlights[i]+" ";	
			}
			
			return (res + "...");
			
		}
		
		
		return highlight + "...";
	}

}
