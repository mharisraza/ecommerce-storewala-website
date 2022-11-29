package com.storewala.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.storewala.dao.UserRepository;
import com.storewala.entities.User;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserRepository userRepo;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsServiceImpl getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.antMatchers("/customer/**")
		.hasRole("CUSTOMER")
		.antMatchers("/seller/**")
		.hasRole("SELLER")
		.antMatchers("/**")
		.permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		.successHandler(new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				User user = userRepo.loadUserByUserEmail(authentication.getName());
				
				String redirectURL = request.getContextPath();
				
				if(user.getRole().equals("CUSTOMER")) {
					redirectURL = "/customer/home";
				}
				if(user.getRole().equals("SELLER")) {
					redirectURL = "/seller/home";
				}
				if(user.getRole().equals("ADMIN")) {
					redirectURL = "/admin/home";
				}
				
				response.sendRedirect(redirectURL);
				
				
			}
		})
		.failureHandler(new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				// TODO Auto-generated method stub
				
			}
		})
		.and()
		.csrf()
		.disable();
	}
	
	
}
