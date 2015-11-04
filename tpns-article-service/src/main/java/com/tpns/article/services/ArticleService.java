package com.tpns.article.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;

import com.tpns.article.repository.ArticleDAO;
import com.tpns.domain.article.Article;
import com.tpns.domain.utils.Assert;

@Stateless
public class ArticleService {

	@EJB
	private ArticleDAO articleDAO;

	public void save(@Valid Article article) {
		articleDAO.save(article);
	}

	public Article find(Long id) {
		return articleDAO.find(id);
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	public void update(Article article) {
		Article persistent = articleDAO.find(article.getId());
		Assert.notNull(persistent);
		persistent.update(article);
	}

}
