package com.main;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class user {
	@Id
	
	@Column(name = "firstname",unique=true)
	public String firstName;
	
	@Column(name = "lastname")
	public String lastName;
	
	@Column(name = "email")
	public String email;
	
	@Column(name = "password")
	public String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}