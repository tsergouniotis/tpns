package com.tpns.article.validation.validators;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;
import com.tpns.article.validation.constraints.ValidArticle;

public class ArticleValidator implements ConstraintValidator<ValidArticle, Article> {

	private final static Logger LOGGER = LoggerFactory.getLogger(ArticleValidator.class);

	@EJB
	private CategoryDAO categoryDAO;

	@Override
	public void initialize(ValidArticle constraintAnnotation) {
	}

	@Override
	public boolean isValid(Article article, ConstraintValidatorContext context) {
		if (null == article) {
			return true;
		}
		Category category = article.getCategory();
		if (Category.hasValue(category)) {
			Category persistent = categoryDAO.find(category.getName());
			if (null == persistent) {
				context.buildConstraintViolationWithTemplate("category.does.not.exist").addPropertyNode("category").addConstraintViolation();
				return false;
			}
			article.setCategory(persistent);
		}
		return true;
	}

}
