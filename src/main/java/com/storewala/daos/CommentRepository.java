package com.storewala.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storewala.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query(value = "select * from comments where comment_related_to = ?", nativeQuery = true)
	public List<Comment> getAllComments(@Param("id") Integer id);

}
