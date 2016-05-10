package com.tms.Entity;



import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
*/
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



	@Entity
	@Table(name="user")
	public class User implements UserDetails {

	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	@Id
	@Column(name="userid",nullable=false)
	long userId;
	
	@Column(name="name")
	String name;

	
	@Column(name="username")
	@JsonIgnore
	String userName;

	
	@Column(name="password")
	 @JsonIgnore
	String password;

	@Column(name="image")
	@Lob
    private String image;	

	 public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}



	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	List<UserRoles> role;
	
	@JsonIgnore
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	List<Task> task;

	public List<Task> getTask() {
		return task;
	}


	public void setTask(List<Task> task) {
		this.task = task;
	}


	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="userProjects",
	joinColumns=@JoinColumn(name="userId",referencedColumnName="userId"),
	inverseJoinColumns=@JoinColumn(name="projectName", referencedColumnName="projectName"))
	List<Project> project;

	


	public List<UserRoles> getRole() {
		return role;
	}


	public void setRole(List<UserRoles> role) {
		this.role = role;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return userName;
	}

	

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setUsername(String username) {
		this.userName = username;
	}


	public String getPassword() {
		return password;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setPassword(String password) {
        this.password = password;
	}

/*
	public List<UserRoles> getRole() {
		return role;
	}


	public void setRole(List<UserRoles> role) {
		this.role = role;
	}*/


	public List<Project> getProject() {
		return project;
	}


	public void setProject(List<Project> project) {
		this.project = project;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.role;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		
		return true;
	}





}
