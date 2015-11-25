package com.tpns.article.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.tpns.article.converters.CategoryConverter;
import com.tpns.article.domain.Category;
import com.tpns.article.dto.CategoryDTO;
import com.tpns.article.repository.CategoryDAO;

@Stateless
public class CategoryService {

	@EJB
	private CategoryDAO categoryDAO;

	@Inject
	@SessionScoped
	private CategoryConverter categoryConverter;

	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryDAO.findAll();
		return categoryConverter.convertToDtos(categories);
	}

	public CategoryDTO getByName(String name) {
		Category category = categoryDAO.find(name);
		if (null != category)
			return categoryConverter.convert(category);
		else
			return null;
	}

}
