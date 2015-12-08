package com.tpns.article.jsf.managedbeans.admin;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tpns.article.services.CategoryService;
import com.tpns.user.domain.User;
import com.tpns.user.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {

	private static final long serialVersionUID = -5743630169383039037L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionBean.class);

	@EJB
	private UserService userService;

	private User user;

	@PostConstruct
	public void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (null == principal) {
			LOGGER.error("Could not retriece user from session");
		} else {
			user = userService.findByUsername(principal.getName());
			if (null == user) {
				LOGGER.error("Could not retriece user for username:" + principal.getName());
			}
		}
	}

	public void logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		try {
			externalContext.redirect(externalContext.getRequestContextPath());
		} catch (IOException e) {
			LOGGER.error("Redirect failed", e);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
