package com.tpns.article.jsf.managedbeans.admin;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.conf.ApplicationParameter;
import com.tpns.article.jsf.model.User;

@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {

	private static final long serialVersionUID = -5743630169383039037L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionBean.class);
	
	@PersistenceContext(unitName = "article")
	private EntityManager em;

	private User user;

	@PostConstruct
	public void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (null == principal) {
			LOGGER.error("Could not retriece user from session");
		} else {
			user = getUserByUsername(principal.getName());
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


	private User getUserByUsername(String username) {
		try {
			LOGGER.info("Check if user exists");
			String sqlQuery = "select u.user_id, u.username, u.password, u.firstname, u.surname, ur.role from users u, users_roles ur where ur.user_id=u.user_id and u.username=?";
			Query q = em.createNativeQuery(sqlQuery);
			q.setParameter( 1, username );			
			Object[] userprops = (Object[]) q.getSingleResult();
			return new User((BigInteger)userprops[0], (String)userprops[1], (String)userprops[2], (String)userprops[3], (String)userprops[4], (String)userprops[5]);
		} catch (Exception e) {
			LOGGER.error("Could not validate author using author service ", e);
			return null;
		}
	}
}
