package com.tpns.article.validation.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;
import com.tpns.article.validation.constraints.ExistingCategory;

public class ExistingCategoryValidator implements ConstraintValidator<ExistingCategory, String> {

	@Inject
	private CategoryDAO categoryDAO;

	@Override
	public void initialize(final ExistingCategory constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(final String name, final ConstraintValidatorContext context) {

		final Category persistent = categoryDAO.find(name);
		if (null == persistent) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
			return false;
		}

		return true;
	}

}
