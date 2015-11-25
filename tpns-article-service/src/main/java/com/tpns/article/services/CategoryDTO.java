package com.tpns.article.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tpns.article.domain.Category;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = -6871812558625914557L;

	private Long id;
	private String name;

	public CategoryDTO() {

	}

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}

	public Category getCategory() {
		return Category.create(name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static List<CategoryDTO> convert(List<Category> entityList) {
		List<CategoryDTO> dtoList = new ArrayList<CategoryDTO>();
		for (Category category : entityList) {
			dtoList.add(new CategoryDTO(category));
		}
		return dtoList;
	}
}
