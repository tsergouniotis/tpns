package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.user.domain.User;

@ManagedBean
@ViewScoped
public class ProfileBean extends BaseTpnsBean implements Serializable {

	private static final long serialVersionUID = -4252130118113846407L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileBean.class);

	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;

	private User user;

	@PostConstruct
	public void init() {
		user = userSessionBean.getUser();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

}
