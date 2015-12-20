package com.tpns.article.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.tpns.article.domain.ApplicationParameter;
import com.tpns.repository.AbstractDAOImpl;

public class ApplicationParameterDAOImpl extends AbstractDAOImpl<ApplicationParameter, String> implements ApplicationParameterDAO {

	@PersistenceContext(unitName = "article")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public String value(String key) {
		TypedQuery<String> query = em.createNamedQuery("ApplicationParameter.findByKey", String.class);
		query.setParameter("key", key);
		return query.getSingleResult();
	}

}
