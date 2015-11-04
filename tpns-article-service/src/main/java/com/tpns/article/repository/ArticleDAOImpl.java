package com.tpns.article.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tpns.domain.article.Article;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ArticleDAOImpl extends AbstractDAOImpl<Article, Long> implements ArticleDAO {

	@PersistenceContext(unitName = "article")
	protected EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
