package com.tpns.article.validation.domain.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;
import com.tpns.article.validation.domain.constraints.ValidArticle;

public class ArticleValidator implements ConstraintValidator<ValidArticle, Article> {

	@Inject
	private CategoryDAO categoryDAO;

	@Override
	public void initialize(final ValidArticle constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Article article, final ConstraintValidatorContext context) {
		if (null == article) {
			return true;
		}
		final Category category = article.getCategory();
		if (Category.hasValue(category)) {
			final Category persistent = categoryDAO.find(category.getName());
			if (null == persistent) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("category").addConstraintViolation();
				return false;
			}
			article.setCategory(persistent);
		}
		return true;
	}

}
