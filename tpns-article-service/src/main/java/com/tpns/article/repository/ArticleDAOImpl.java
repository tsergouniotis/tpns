package com.tpns.article.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.filter.ArticleFilter;
import com.tpns.repository.AbstractDAOImpl;
import com.tpns.utils.StringUtils;

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
	public Article find(ArticleFilter articleFilter) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> article = criteriaQuery.from(Article.class);
		if (!StringUtils.isEmptyString(articleFilter.getStatus())) {
			criteriaQuery.where(criteriaBuilder.equal(article.get("status"), articleFilter.getStatus()));
		}
		criteriaQuery.select(article);
		TypedQuery<Article> query = em.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			LOGGER.debug("Could not find Article for filter due to the following error " + e.getMessage(), e);
			return null;
		}
	}
}
