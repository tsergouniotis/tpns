package com.tpns.user.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;

import com.tpns.user.domain.User;
import com.tpns.user.repository.UserDAO;
import com.tpns.utils.Assert;

@Stateless
public class UserService {

	@EJB
	private UserDAO userDAO;

	public void save(@Valid User User) {
		userDAO.save(User);
	}

	public User find(Long id) {
		return userDAO.find(id);
	}

	public void delete(Long id) {
		User user = userDAO.find(id);
		Assert.notNull(user);
		userDAO.delete(user);
	}

	public void update(@Valid User user) {
		User persistent = userDAO.find(user.getId());
		Assert.notNull(persistent);
		persistent.update(user);
	}

}
