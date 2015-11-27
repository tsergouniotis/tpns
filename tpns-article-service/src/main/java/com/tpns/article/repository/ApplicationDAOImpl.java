package com.tpns.article.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.tpns.common.Application;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
public class ApplicationDAOImpl extends AbstractDAOImpl<Application, Long> implements ApplicationDAO {

	@PersistenceContext(unitName = "article")
	protected EntityManager em;

	@Override
	public List<Application> find(String... ids) {
		TypedQuery<Application> query = em.createNamedQuery("Application.findByIds", Application.class).setParameter("clientIds", ids);
		return query.getResultList();
	}

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
