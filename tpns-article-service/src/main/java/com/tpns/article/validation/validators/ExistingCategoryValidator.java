package com.tpns.article.validation.validators;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tpns.article.repository.CategoryDAO;
import com.tpns.article.validation.constraints.ExistingCategory;
import com.tpns.domain.article.Category;
import com.tpns.domain.utils.StringUtils;

public class ExistingCategoryValidator implements ConstraintValidator<ExistingCategory, String> {

	@EJB
	private CategoryDAO categoryDAO;

	@Override
	public void initialize(ExistingCategory constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {

		if (!StringUtils.hasText(name)) {
			return true;
		}

		Category persistent = categoryDAO.find(name);

		if (null == persistent) {
			context.buildConstraintViolationWithTemplate("category.does.not.exist").addConstraintViolation();
			return false;
		}

		return true;
	}

}
