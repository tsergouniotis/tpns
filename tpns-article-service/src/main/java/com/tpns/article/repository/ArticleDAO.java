package com.tpns.article.repository;

import com.tpns.article.domain.Article;
import com.tpns.article.filter.ArticleFilter;
import com.tpns.repository.GenericDAO;

public interface ArticleDAO extends GenericDAO<Article, Long> {

	Article find(ArticleFilter articleFilter);

}
