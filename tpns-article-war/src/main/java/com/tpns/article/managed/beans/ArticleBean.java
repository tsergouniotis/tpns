package com.tpns.article.managed.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.tpns.article.domain.Article;
import com.tpns.article.services.ArticleService;

@ManagedBean
@SessionScoped
public class ArticleBean {

	@EJB
	private ArticleService articleService;

	private String name;

	public List<Article> getArticles() {
		return articleService.findAll();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
