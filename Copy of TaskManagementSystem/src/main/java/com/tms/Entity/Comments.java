package com.tms.Entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



@Entity
@Table(name="comments")
public class Comments {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	public String getCommentText() {
		return commentText;
	}


	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}


	@Column(name="comment")
	public String commentText;
	
	@Column(name="user")
	public String user;
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="task")
	Task task;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getComment() {
		return commentText;
	}


	public void setComment(String comment) {
		this.commentText = comment;
	}


	public Task getTask() {
		return task;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setTask(Task task) {
		this.task = task;
	}

	
	

	
}
