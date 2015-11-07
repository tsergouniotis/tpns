package com.tpns.article.validation.validators;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tpns.article.repository.CategoryDAO;
import com.tpns.article.validation.constraints.ValidArticle;
import com.tpns.domain.article.Article;
import com.tpns.domain.article.Category;

public class ArticleValidator implements ConstraintValidator<ValidArticle, Article> {

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
