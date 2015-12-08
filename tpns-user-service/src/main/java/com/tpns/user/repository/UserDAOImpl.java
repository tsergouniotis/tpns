package com.tpns.user.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.repository.AbstractDAOImpl;
import com.tpns.user.domain.User;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

	@PersistenceContext(unitName = "user")
	private EntityManager em;

	@Override
	protected EntityManager entityManager() {
		return em;
	}

	@Override
	public User findByUsername(String username) {
		TypedQuery<User> query = entityManager().createNamedQuery("User.findByUsername", User.class);
		query = query.setParameter("username", username);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			LOGGER.debug("Could not find User by username due to the following error " + e.getMessage(), e);
			return null;
		}
	}
}
