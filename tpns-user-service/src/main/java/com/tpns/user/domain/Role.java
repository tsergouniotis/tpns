package com.tpns.user.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "role", namespace = "{urn:com.tpns}")
public class Role {

	private Long id;

	private Roles role;

	@XmlTransient
	public Long getId() {
		return id;
	}

	@XmlElement(name = "role")
	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

}
