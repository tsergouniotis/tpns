package com.tpns.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "user", namespace = "{urn:com.tpns}")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String surname;

	private String username;

	private String password;

	private Collection<Role> roles;

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "surname")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@XmlElement(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void update(User user) {
		this.name = user.getName();
		this.surname = user.getSurname();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

}
