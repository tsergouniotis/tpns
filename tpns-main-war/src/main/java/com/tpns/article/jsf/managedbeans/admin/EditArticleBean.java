package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.services.ArticleDTO;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryDTO;
import com.tpns.article.services.CategoryService;

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
		// TODO
		// check in faces context and if there is an article load it ()
		LOGGER.info("New Article");
		selectedArticle = new ArticleDTO();
		for (CategoryDTO category : categoryService.getCategories()) {
			availableCategories.put(category.getName(), category);
		}
	}

	/*
	 * JSF Actions
	 * */
	public String saveArticle() {
		articleService.save(selectedArticle);
		return "/pages/admin/index.xhtml";
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
	 * */
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
