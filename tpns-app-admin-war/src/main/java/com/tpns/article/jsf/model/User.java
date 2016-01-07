package com.tpns.article.jsf.model;

import java.util.Collection;


public class User {

	private Long id;

	private String firstname;

	private String surname;

	private String username;

	private String password;

	private Collection<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}	

	public boolean hasRole(Role theRole) {
		for (Role role : roles) {
			if (role.equals(theRole))
				return true;
		}
		return false;
	}
	
}
