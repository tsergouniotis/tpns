package com.tpns.article.jsf.managedbeans.newsclient;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.dto.CategoryDTO;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;

@ManagedBean
@RequestScoped
public class ArticleBean implements Serializable {

	private static final long serialVersionUID = 4408694165033655293L;

	@EJB
	private ArticleService articleService;

	@EJB
	private CategoryService categoryService;

	private String name;

	public List<ArticleDTO> getArticles() {
		return articleService.findAll();
	}

	public List<CategoryDTO> getCategories() {
		return categoryService.getCategories();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
