package com.tpns.article.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.domain.Category;
import com.tpns.article.repository.CategoryDAO;

@Stateless
public class CategoryService {

	@EJB
	private CategoryDAO categoryDAO;

	public List<CategoryDTO> getCategories() {
		return CategoryDTO.convert(categoryDAO.findAll());
	}

	public CategoryDTO getByName(String name) {
		Category category = categoryDAO.find(name);
		if (null != category)
			return new CategoryDTO(category);
		else
			return null;
	}

}
