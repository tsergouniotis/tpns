package com.tpns.article.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.repository.ArticleDAO;
import com.tpns.domain.Article;

@Stateless
public class ArticleService {

	@EJB
	private ArticleDAO articleDAO;

	public void save(Article article) {
		articleDAO.save(article);
	}

	public Article find(Long id) {
		return articleDAO.find(id);
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		assertExistence(article);
		articleDAO.delete(article);
	}

	public void update(Article article) {
		Article persistent = articleDAO.find(article.getId());
		assertExistence(persistent);
		persistent.update(article);
	}

	private void assertExistence(Article article) {
		if (null == article) {
			throw new IllegalArgumentException("No article could be founded!!!");
		}
	}

}
