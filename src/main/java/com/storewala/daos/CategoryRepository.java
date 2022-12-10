package com.storewala.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storewala.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query(value = "select * from categories", nativeQuery = true)
	public List<Category> getCategories();
	
	@Query(value = "select count(*) from categories where category_title = ?", nativeQuery = true)
	public Integer getCategoriesByTitle(@Param("category_title") String title);

}
