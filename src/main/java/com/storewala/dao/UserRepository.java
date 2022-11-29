package com.storewala.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storewala.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "select count(*) from users where user_email = ?", nativeQuery = true)
	public Integer checkIfEmailExist(@Param("email") String email);
	
	@Query(value = "select * from users where user_email = ?", nativeQuery = true)
	public User loadUserByUserEmail(@Param("email") String email);

}
	