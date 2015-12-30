package com.tpns.article.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.tpns.common.domain.Application;
import com.tpns.repository.AbstractDAOImpl;

public class ApplicationDAOImpl extends AbstractDAOImpl<Application, Long> implements ApplicationDAO {

	@PersistenceContext(unitName = "article")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public List<Application> find(final List<String> ids) {
		final TypedQuery<Application> query = em.createNamedQuery("Application.findByIds", Application.class).setParameter("clientIds", ids);
		return query.getResultList();
	}

}
