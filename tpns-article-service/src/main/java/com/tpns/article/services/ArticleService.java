package com.tpns.article.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

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
	private Validator validator;

	@Inject
	@SessionScoped
	private ArticleConverter articleConverter;

	public void save(@Valid ArticleDTO article) {
		Article persistent = articleConverter.convert(article);
		Set<ConstraintViolation<Article>> constraintViolations = validator.validate(persistent);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(constraintViolations));
		}
		articleDAO.save(persistent);
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
		Set<ConstraintViolation<Article>> constraintViolations = validator.validate(persistent);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(constraintViolations));
		}
		persistent.update(articleConverter.convert(article));
	}

}
