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
import com.tpns.core.errors.BusinessException;

@Stateless
public class ArticleService {

	@EJB
	private ArticleManager articleManager;

	@Inject
	private ArticleConverter articleConverter;

	public void save(@Valid ArticleDTO article) throws BusinessException {
		Article entity = articleConverter.toEntity(article);
		articleManager.save(entity);
	}

	public ArticleDTO find(Long id) {
		return articleConverter.toDto(articleManager.find(id));
	}

	public List<ArticleDTO> findAll() {
		List<Article> articles = articleManager.findAll();
		return articleConverter.toDtos(articles);
	}

	public List<ArticleDTO> findPublished() {
		List<Article> articles = articleManager.findByStatus(ArticleStatus.PUBLISHED);
		return articleConverter.toDtos(articles);
	}

	public List<ArticleDTO> findByCategory(String categoryName) {
		List<Article> articles = articleManager.findByCategory(categoryName);
		return articleConverter.toDtos(articles);
	}

	public void delete(Long id) {
		articleManager.delete(id);
	}

	public void update(ArticleDTO article) throws BusinessException {
		articleManager.update(article.getId(), articleConverter.toEntity(article));
	}

	public List<ArticleDTO> search(String key) {
		List<Article> articles = articleManager.findByKey(key);
		return articleConverter.toDtos(articles);
	}

}
