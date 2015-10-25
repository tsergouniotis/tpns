package com.tpns.article.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tpns.domain.Article;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
public class ArticleDAOImpl extends AbstractDAOImpl<Article, Long> implements ArticleDAO {

	@PersistenceContext(unitName = "article")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
