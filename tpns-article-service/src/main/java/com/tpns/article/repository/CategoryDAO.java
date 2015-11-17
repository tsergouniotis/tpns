package com.tpns.article.repository;

import com.tpns.article.domain.Category;
import com.tpns.repository.GenericDAO;

public interface CategoryDAO extends GenericDAO<Category, Long> {

	Category find(String categoryName);

}
