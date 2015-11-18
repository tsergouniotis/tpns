package com.tpns.user.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tpns.repository.AbstractDAOImpl;
import com.tpns.user.domain.User;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {

	@PersistenceContext(unitName = "user")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

}
