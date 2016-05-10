package com.tms.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public long id;
	
	@Column(name="projectname")
	public String projectName;
	

	@JsonIgnore
	@ManyToMany
	@JoinTable(name="userProjects",
    joinColumns= @JoinColumn(name="projectName",referencedColumnName="projectName"),
    inverseJoinColumns= @JoinColumn(name="userId", referencedColumnName="userId"))
	List<User> user;

	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<User> getUser() {
		return user;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setUser(List<User> user) {
		this.user = user;
	}
	
	
}
