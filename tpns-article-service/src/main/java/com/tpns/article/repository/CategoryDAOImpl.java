package com.tpns.article.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Category;
import com.tpns.repository.AbstractDAOImpl;

public class CategoryDAOImpl extends AbstractDAOImpl<Category, Long> implements CategoryDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDAOImpl.class);

	@PersistenceContext(unitName = "article")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public Category find(final String categoryName) {
		TypedQuery<Category> query = em.createNamedQuery("Category.findByName", Category.class);
		query = query.setParameter("name", categoryName);
		try {
			return query.getSingleResult();
		} catch (final Exception e) {
			LOGGER.debug("Could not find Category by name due to the following error " + e.getMessage(), e);
			return null;
		}
	}

}
