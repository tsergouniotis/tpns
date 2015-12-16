package com.tpns.article.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ArticleDAOImpl extends AbstractDAOImpl<Article, Long> implements ArticleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDAOImpl.class);

	@PersistenceContext(unitName = "article")
	protected EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public List<Article> findByStatus(ArticleStatus status) {
		TypedQuery<Article> query = entityManager().createNamedQuery("Article.findByStatus", Article.class);
		query = query.setParameter("status", status);
		try {
			return query.getResultList();
		} catch (Exception e) {
			LOGGER.debug("Could not find Article for status due to the following error " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<Article> findByCategory(String categoryName) {
		TypedQuery<Article> query = entityManager().createNamedQuery("Article.findByCategoryName", Article.class);
		query = query.setParameter("categoryName", categoryName);
		try {
			return query.getResultList();
		} catch (Exception e) {
			LOGGER.debug("Could not find Article for status due to the following error " + e.getMessage(), e);
			return null;
		}
	}

}
