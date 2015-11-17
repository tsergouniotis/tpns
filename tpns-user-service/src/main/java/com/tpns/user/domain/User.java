package com.tpns.user.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "user", namespace = "{urn:com.tpns}")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstname;

	private String surname;

	private String username;

	private String password;

	private ContactInfo contact;

	private Collection<Role> roles;

	@XmlTransient
	public Long getId() {
		return id;
	}

	@XmlElement(name = "name")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	public ContactInfo getContact() {
		return contact;
	}

	public void setContact(ContactInfo contact) {
		this.contact = contact;
	}

	public void update(User user) {
		this.firstname = user.getFirstname();
		this.surname = user.getSurname();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

}
