package com.tpns.article.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.tpns.article.domain.ApplicationParameter;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
public class ApplicationParameterDAOImpl extends AbstractDAOImpl<ApplicationParameter, String> {

	@PersistenceContext(unitName = "article")
	protected EntityManager em;

	public String value(String key) {
		TypedQuery<String> query = em.createNamedQuery("ApplicationParameter.findByKey", String.class);
		query.setParameter("key", key);
		return query.getSingleResult();
	}

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
