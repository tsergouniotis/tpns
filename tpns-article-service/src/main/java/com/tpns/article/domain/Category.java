package com.tpns.article.domain;

import com.tpns.utils.StringUtils;

public class Category {

	private Long id;

	private String name;

	private Category() {

	}

	private Category(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasValue() {
		return StringUtils.hasText(name);
	}

	public static boolean hasValue(Category category) {
		return null != category && category.hasValue();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	public static Category create(String name) {
		if (StringUtils.hasText(name)) {
			return new Category(name);
		}
		return null;
	}

}
