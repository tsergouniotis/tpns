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

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.dto.CategoryDTO;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.error.BusinessException;

@ManagedBean
@ViewScoped
public class EditArticleBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditArticleBean.class);

	private static final long serialVersionUID = -7958535950984588697L;

	@EJB
	private CategoryService categoryService;

	@EJB
	private ArticleService articleService;

	private Map<String, CategoryDTO> availableCategories = new HashMap<String, CategoryDTO>();

	private ArticleDTO selectedArticle;

	@PostConstruct
	public void init() {
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		if (null == articleId) {
			selectedArticle = new ArticleDTO();
		} else {
			Long articleIdAsLong = Long.parseLong(articleId);
			selectedArticle = articleService.find(articleIdAsLong);
			if (null == selectedArticle) {
				LOGGER.error("Failed to load article with id " + articleId);
			}
		}
		for (CategoryDTO category : categoryService.getCategories()) {
			availableCategories.put(category.getName(), category);
		}
	}

	/*
	 * JSF Actions
	 */
	public String saveArticle() {
		try {
			articleService.save(selectedArticle);
			return "/pages/admin/index.xhtml";
		} catch (BusinessException businessException) {
			LOGGER.error("Article validation failed: " + businessException.getMessage());
			JSFUtils.outputBusinessExceptionToComponent(businessException, FacesContext.getCurrentInstance(), "form");
		}
		return null;
	}

	public void reloadArticle() {
		selectedArticle = new ArticleDTO();
	}

	public void addImage() {
		selectedArticle.addImage("");
	}

	public void addVideo() {
		selectedArticle.addVideo("");
	}

	public void addAudio() {
		selectedArticle.addAudio("");
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

	public Map<String, CategoryDTO> getAvailableCategories() {
		return availableCategories;
	}

	public void setAvailableCategories(Map<String, CategoryDTO> availableCategories) {
		this.availableCategories = availableCategories;
	}

}
