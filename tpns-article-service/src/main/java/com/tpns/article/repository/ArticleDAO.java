package com.tpns.article.repository;

import java.util.List;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.repository.GenericDAO;

public interface ArticleDAO extends GenericDAO<Article, Long> {

	List<Article> findByStatus(ArticleStatus status);

	List<Article> findByCategory(String categoryName);

}
