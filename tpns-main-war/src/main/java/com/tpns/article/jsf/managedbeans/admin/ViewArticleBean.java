package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.user.domain.Roles;
import com.tpns.utils.StringUtils;

@ManagedBean
@ViewScoped
public class ViewArticleBean extends BaseTpnsBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewArticleBean.class);

	private static final long serialVersionUID = 6512180737722350944L;

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;

	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;

	private ArticleDTO selectedArticle;

	private List<ArticleDTO> availableArticles;
	private Map<String, String> articleStatusDisplay;

	@PostConstruct
	public void init() {
		this.availableArticles = articleService.findAll();
		initStatuses();
	}

	private void initStatuses() {
		this.articleStatusDisplay = new HashMap<String, String>();
		this.articleStatusDisplay.put(ArticleStatus.POSTED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforreview"));
		this.articleStatusDisplay.put(ArticleStatus.REVIEWED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforpublish"));
		this.articleStatusDisplay.put(ArticleStatus.PUBLISHED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.publish"));
	}

	public String getArticleStatus(String status) {
		String statusString = articleStatusDisplay.get(status);
		return StringUtils.isEmptyString(statusString) ? status : statusString;
	}

	public boolean isEditAllowed(Long authorId, String status) {
		return (authorId.equals(userSessionBean.getUser().getId()) && ArticleStatus.POSTED.toString().equals(status)) || userSessionBean.getUser().hasRole(Roles.CHIEF_EDITOR);
	}

	public boolean isDeleteAllowed() {
		return userSessionBean.getUser().hasRole(Roles.CHIEF_EDITOR);
	}

	/*
	 * JSF Actions
	 * */

	public String editArticle() {
		return "/pages/admin/editArticle.xhtml";
	}

	public String deleteArticle() {
		if (null == selectedArticle) {
			LOGGER.error("Unexpectidely asked to delete with no article selected");
		} else {
			articleService.delete(selectedArticle.getId());
			availableArticles.remove(selectedArticle);
			selectedArticle = null;
		}
		return null;
	}

	/*
	 * Getter - setters
	 * */
	public List<ArticleDTO> getAvailableArticles() {
		return availableArticles;
	}

	public void setAvailableArticles(List<ArticleDTO> availableArticles) {
		this.availableArticles = availableArticles;
	}

	public ArticleDTO getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(ArticleDTO selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

}
