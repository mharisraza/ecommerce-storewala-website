package com.storewala.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.storewala.dao.UserRepository;
import com.storewala.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		
		User user = this.userRepo.loadUserByUserEmail(userEmail);
		
		if(user==null) {
			throw new UsernameNotFoundException("No user is available for this email address.");
		}
		return new CustomUserDetails(user);
	}

}
