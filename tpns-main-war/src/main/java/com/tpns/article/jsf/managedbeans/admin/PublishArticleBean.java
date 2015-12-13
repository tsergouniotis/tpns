package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.jsf.utils.JSFConstants;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.services.ApplicationService;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.error.BusinessException;

@ManagedBean
@ViewScoped
public class PublishArticleBean extends BaseTpnsManagedBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublishArticleBean.class);

	private static final long serialVersionUID = -7958535950984588697L;

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;
	@EJB
	private ApplicationService applicationService;

	private ArticleDTO selectedArticle;

	// Cache variables
	private Map<String, String> availableCategories = new HashMap<String, String>();
	private Map<String, String> availableDestinations = new HashMap<>();

	@PostConstruct
	public void init() {
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(JSFConstants.PARAM_NAME_ARTICLE_ID);
		if (null == articleId) {
			selectedArticle = new ArticleDTO();
		} else {
			Long articleIdAsLong = Long.parseLong(articleId);
			selectedArticle = articleService.find(articleIdAsLong);
			if (null == selectedArticle) {
				LOGGER.error("Failed to load article with id " + articleId);
			}
		}
		for (String category : categoryService.getCategories()) {
			availableCategories.put(category, category);
		}
		for (String applicationClientId : applicationService.findAll()) {
			availableDestinations.put(applicationClientId, applicationClientId);
		}
	}

	/*
	 * JSF Actions
	 */
	public String publishArticle() {
		LOGGER.debug("Publishing article: " + selectedArticle.toString());
		try {
			selectedArticle.setStatus(ArticleStatus.PUBLISHED.toString());
			articleService.update(selectedArticle);
			return "/pages/admin/index.xhtml";
		} catch (BusinessException businessException) {
			LOGGER.error("Article validation failed: " + businessException.getMessage());
			JSFUtils.outputBusinessExceptionToComponent(businessException, FacesContext.getCurrentInstance(), "form");
		}
		return null;
	}

	public void reloadArticle() {
		selectedArticle = articleService.find(selectedArticle.getId());
	}

	/*
	 * Getter - setters
	 */
	public ArticleDTO getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(ArticleDTO selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public Map<String, String> getAvailableCategories() {
		return availableCategories;
	}

	public void setAvailableCategories(Map<String, String> availableCategories) {
		this.availableCategories = availableCategories;
	}

	public Map<String, String> getAvailableDestinations() {
		return availableDestinations;
	}

	public void setAvailableDestinations(Map<String, String> availableDestinations) {
		this.availableDestinations = availableDestinations;
	}

}
