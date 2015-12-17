package com.tpns.article.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.domain.Category;
import com.tpns.article.managers.CategoryManager;
import com.tpns.utils.CollectionUtils;

@Stateless
public class CategoryService {

	@EJB
	private CategoryManager categoryManager;

	public List<String> getCategories() {

		List<Category> categories = categoryManager.getCategories();

		final List<String> result = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(categories)) {
			categories.forEach(category -> result.add(category.getName()));
		}

		return result;
	}
}
