package com.tpns.article.validation.dto.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.validation.dto.constraints.ValidArticleDTO;

public class ArticleDTOValidator implements ConstraintValidator<ValidArticleDTO, ArticleDTO> {

	@Override
	public void initialize(final ValidArticleDTO constraintAnnotation) {
	}

	@Override
	public boolean isValid(final ArticleDTO article, final ConstraintValidatorContext context) {
		if (null == article) {
			return true;
		}

		return true;
	}

}
