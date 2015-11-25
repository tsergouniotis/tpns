package com.tpns.article.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;

import com.tpns.article.domain.Article;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.utils.Assert;

@Stateless
public class ArticleService {

	@EJB
	private ArticleDAO articleDAO;

	public void save(@Valid ArticleDTO article) {
		articleDAO.save(article.getArticle());
	}

	public ArticleDTO find(Long id) {
		return new ArticleDTO(articleDAO.find(id));
	}

	public List<ArticleDTO> findAll() {
		return ArticleDTO.convert(articleDAO.findAll());
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	public void update(ArticleDTO article) {
		Article persistent = articleDAO.find(article.getId());
		Assert.notNull(persistent);
		persistent.update(article.getArticle());
	}

}
