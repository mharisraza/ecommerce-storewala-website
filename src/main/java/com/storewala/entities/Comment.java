package com.storewala.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Column(name = "comment_related_to")
	private int commentRelatedTo;
	
	@Column(name = "comment")
	@NotBlank(message = "Comment cannot be empty.")
	private String comment;
	
	@Column(name = "comment_added")
	private java.util.Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCommentRelatedTo() {
		return commentRelatedTo;
	}

	public void setCommentRelatedTo(int commentRelatedTo) {
		this.commentRelatedTo = commentRelatedTo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}
	

}
