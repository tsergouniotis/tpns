package com.tpns.article.jsf.managedbeans.admin;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTpnsBean implements Serializable {

	private static final long serialVersionUID = 1255148156797900591L;

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseTpnsBean.class);

	public void goToPortalPage() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath());
		} catch (IOException e) {
			LOGGER.error("Redirect to portal page failed", e);
		}
	}

	public void goToProfilePage() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/pages/admin/profile.xhtml");
		} catch (IOException e) {
			LOGGER.error("Redirect to profile page failed", e);
		}
	}

	public void goToArticlePage() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/pages/admin/index.xhtml");
		} catch (IOException e) {
			LOGGER.error("Redirect to admin page failed", e);
		}
	}

	public void goToCreateArticlePage() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(externalContext.getRequestContextPath() + "/pages/admin/editArticle.xhtml");
		} catch (IOException e) {
			LOGGER.error("Redirect to admin page failed", e);
		}
	}

}
