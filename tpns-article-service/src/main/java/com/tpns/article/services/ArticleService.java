package com.tpns.article.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;

import com.tpns.article.converters.ArticleConverter;
import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.managers.ArticleManager;
import com.tpns.common.domain.errors.BusinessException;

@Stateless
public class ArticleService {

	@EJB
	private ArticleManager articleManager;

	@Inject
	private ArticleConverter articleConverter;

	public void save(@Valid final ArticleDTO article) throws BusinessException {
		final Article entity = articleConverter.toEntity(article);
		articleManager.save(entity);
	}

	public ArticleDTO find(final Long id) {
		return articleConverter.toDto(articleManager.find(id));
	}

	public List<ArticleDTO> findAll() {
		final List<Article> articles = articleManager.findAll();
		return articleConverter.toDtos(articles);
	}

	public List<ArticleDTO> findPublished() {
		final List<Article> articles = articleManager.findByStatus(ArticleStatus.PUBLISHED);
		return articleConverter.toDtos(articles);
	}

	public List<ArticleDTO> findByCategory(final String categoryName) {
		final List<Article> articles = articleManager.findByCategory(categoryName);
		return articleConverter.toDtos(articles);
	}

	public void delete(final Long id) {
		articleManager.delete(id);
	}

	public void update(final ArticleDTO article) throws BusinessException {
		articleManager.update(article.getId(), articleConverter.toEntity(article));
	}

	public List<ArticleDTO> search(final String key) {
		final List<Article> articles = articleManager.findByKey(key);
		return articleConverter.toDtos(articles);
	}

}
