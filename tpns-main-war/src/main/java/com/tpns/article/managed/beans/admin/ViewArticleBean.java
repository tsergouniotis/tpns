package com.tpns.article.managed.beans.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.tpns.article.domain.Article;
import com.tpns.article.services.ArticleService;

@ManagedBean
@RequestScoped
public class ViewArticleBean implements Serializable {

	private static final long serialVersionUID = 6512180737722350944L;

	@EJB
	private ArticleService articleService;

	private List<Article> availableArticles;

	@PostConstruct
	public void init() {
		this.availableArticles = articleService.findAll();
	}

	/*
	 * Getter - setters
	 * */
	public List<Article> getAvailableArticles() {
		return availableArticles;
	}

	public void setAvailableArticles(List<Article> availableArticles) {
		this.availableArticles = availableArticles;
	}

}
