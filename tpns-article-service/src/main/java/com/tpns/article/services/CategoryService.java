package com.tpns.article.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tpns.article.converters.CategoryConverter;
import com.tpns.article.domain.Category;
import com.tpns.article.dto.CategoryDTO;
import com.tpns.article.managers.CategoryManager;

@Stateless
public class CategoryService {

	@EJB
	private CategoryManager categoryManager;

	@Inject
	private CategoryConverter categoryConverter;

	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryManager.getCategories();
		return categoryConverter.toDtos(categories);
	}

	public CategoryDTO getByName(String name) {
		Category category = categoryManager.getByName(name);
		if (null != category)
			return categoryConverter.toDto(category);
		else
			return null;
	}

}
