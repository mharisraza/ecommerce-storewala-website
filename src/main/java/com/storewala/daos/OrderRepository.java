package com.storewala.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storewala.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value = "select * from orders where product_id = ?", nativeQuery = true)
	public Order findByProductId(@Param("id") Integer id);

}
