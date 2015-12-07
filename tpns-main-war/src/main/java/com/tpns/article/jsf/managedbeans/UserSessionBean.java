package com.tpns.article.jsf.managedbeans;

import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {

	private static final long serialVersionUID = -5743630169383039037L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionBean.class);

	private Principal principal;

	@PostConstruct
	public void init() {
		principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
	}

	public String getUserName() {
		return principal.getName();
	}

	public String getWelcomeMessage() {
		return "Hello " + principal.getName();
	}

}
