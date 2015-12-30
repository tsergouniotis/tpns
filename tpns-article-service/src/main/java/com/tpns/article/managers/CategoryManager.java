package com.tpns.article.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;

@Stateless
public class CategoryManager {

	@Inject
	private CategoryDAO categoryDAO;

	public List<Category> getCategories() {
		return categoryDAO.findAll();
	}

	public Category getByName(final String name) {
		return categoryDAO.find(name);
	}

}
