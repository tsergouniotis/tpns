package com.tpns.user.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "role", namespace = "{urn:com.tpns}")
public enum Role {

	ADMIN,
	AUTHOR,
	CHIEF_EDITOR,
	APPLICATION;

}
