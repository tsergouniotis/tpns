package com.tpns.article.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;

@Stateless
public class CategoryManager {

	@EJB
	private CategoryDAO categoryDAO;

	public List<Category> getCategories() {
		return categoryDAO.findAll();
	}

	public Category getByName(String name) {
		return categoryDAO.find(name);
	}

}
