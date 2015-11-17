package com.tpns.article.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Category;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CategoryDAOImpl extends AbstractDAOImpl<Category, Long> implements CategoryDAO {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryDAOImpl.class);

	@PersistenceContext(unitName = "article")
	protected EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public Category find(String categoryName) {
		TypedQuery<Category> query = entityManager().createNamedQuery("Category.findByName", Category.class);
		query = query.setParameter("name", categoryName);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			LOG.debug("Could not find Category by name due to the following error " + e.getMessage(), e);
			return null;
		}
	}

}
