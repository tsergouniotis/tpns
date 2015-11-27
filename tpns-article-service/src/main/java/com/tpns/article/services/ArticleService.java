package com.tpns.article.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import com.tpns.article.converters.ArticleConverter;
import com.tpns.article.domain.Article;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.article.services.interceptors.Dispatch;
import com.tpns.error.BusinessError;
import com.tpns.error.BusinessErrorCode;
import com.tpns.error.BusinessException;
import com.tpns.utils.Assert;

@Stateless
public class ArticleService {

	@EJB
	private ArticleDAO articleDAO;

	@Inject
	private Validator validator;

	@Inject
	private ArticleConverter articleConverter;

	// The ejb way @Interceptors({ DispatcherInterceptor.class })
	@Dispatch
	public void save(@Valid ArticleDTO article) throws BusinessException {
		Article persistent = articleConverter.convert(article);
		Set<ConstraintViolation<Article>> constraintViolations = validator.validate(persistent);
		if (!constraintViolations.isEmpty()) {
			throw createBusinessException(constraintViolations);
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

	public void update(ArticleDTO article) throws BusinessException {
		Article persistent = articleDAO.find(article.getId());
		Assert.notNull(persistent);
		Set<ConstraintViolation<Article>> constraintViolations = validator.validate(persistent);
		if (!constraintViolations.isEmpty()) {
			throw createBusinessException(constraintViolations);
		}
		persistent.update(articleConverter.convert(article));
	}

	private <T> BusinessException createBusinessException(Set<ConstraintViolation<T>> constraintViolations) {
		List<BusinessError> errors = new ArrayList<>();
		for (ConstraintViolation constraintViolation : constraintViolations) {
			errors.add(BusinessError.create(constraintViolation.getMessage(), BusinessErrorCode.VALIDATION));
		}
		return BusinessException.create(errors);
	}
}
