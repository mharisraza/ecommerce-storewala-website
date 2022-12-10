package com.storewala.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id; // user id is auto increment.
	
	@Column(name="user_name")
	@NotBlank(message = "Full Name is required.")
	private String name;  // user full name 
	
	@Column(name="user_email", unique = true)
	@NotBlank(message = "Email is required.")
	private String email; // user email
	
	@Column(name = "user_pwd")
	@NotBlank(message = "Password is required.")
	private String password; // user password
	
	@Column(name = "user_role")
	private String role; // user role

	@Column(name = "user_phone")
	@NotBlank(message = "Phone Number is required.")
	private String phone; // user phone (String)
	
	@Column(name = "user_suspension_status")
	private boolean isEnable; // user Suspension status, true if yes and false if no.
	
	@Column(name="user_profile")
	private String profile;	// user profile image link (No actual image)
	
	
	@Column(name = "regisered_date")
	private Date date;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", phone=" + phone + ", isEnable=" + isEnable + ", profile=" + profile + "]";
	}

	
	
	
	
	
	
	

}
