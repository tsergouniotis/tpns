package com.tpns.article.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;

import com.tpns.article.converters.ArticleConverter;
import com.tpns.article.domain.Article;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.utils.Assert;

@Stateless
public class ArticleService {

	@EJB
	private ArticleDAO articleDAO;

	@Inject
	private ArticleConverter articleConverter;

	public void save(@Valid ArticleDTO article) {
		articleDAO.save(articleConverter.convert(article));
	}

	public ArticleDTO find(Long id) {
		return articleConverter.convert(articleDAO.find(id));
	}

	public List<ArticleDTO> findAll() {
		List<Article> articles = articleDAO.findAll();
		return articleConverter.convertToDtos(articles);
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	public void update(ArticleDTO article) {
		Article persistent = articleDAO.find(article.getId());
		Assert.notNull(persistent);
		persistent.update(articleConverter.convert(article));
	}

}
