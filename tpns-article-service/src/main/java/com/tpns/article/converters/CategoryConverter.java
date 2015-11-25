package com.tpns.article.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tpns.article.domain.Category;
import com.tpns.article.dto.CategoryDTO;
import com.tpns.utils.CollectionUtils;

public class CategoryConverter implements Serializable {

	public Category convert(CategoryDTO category) {
		if (null != category) {
			return Category.create(category.getName());
		} else {
			return null;
		}
	}

	public CategoryDTO convert(Category category) {
		CategoryDTO dto = null;
		if (null != category) {
			dto = new CategoryDTO();
			dto.setName(category.getName());
		}
		return dto;
	}

	public List<Category> convertToCategories(Collection<CategoryDTO> dtos) {

		final List<Category> result = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(dtos)) {
			dtos.forEach(dto -> result.add(convert(dto)));
		}

		return result;

	}

	public List<CategoryDTO> convertToDtos(Collection<Category> categories) {

		final List<CategoryDTO> dtos = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(categories)) {
			categories.forEach(category -> dtos.add(convert(category)));
		}

		return dtos;

	}
}
