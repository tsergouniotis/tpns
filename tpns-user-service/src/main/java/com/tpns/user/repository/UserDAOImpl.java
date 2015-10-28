package com.tpns.user.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tpns.domain.user.User;
import com.tpns.repository.AbstractDAOImpl;

@Stateless
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {

	@PersistenceContext(unitName = "user")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
