package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;

@ManagedBean
@ViewScoped
public class ViewArticleBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewArticleBean.class);

	private static final long serialVersionUID = 6512180737722350944L;

	@EJB
	private CategoryService categoryService;

	@EJB
	private ArticleService articleService;

	private List<ArticleDTO> availableArticles;

	private ArticleDTO selectedArticle;

	@PostConstruct
	public void init() {
		this.availableArticles = articleService.findAll();
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

}
