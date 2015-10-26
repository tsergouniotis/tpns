package com.tpns.user.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.domain.User;
import com.tpns.domain.utils.Utils;
import com.tpns.user.repository.UserDAO;

@Stateless
public class UserService {

	@EJB
	private UserDAO userDAO;

	public void save(User User) {
		userDAO.save(User);
	}

	public User find(Long id) {
		return userDAO.find(id);
	}

	public void delete(Long id) {
		User User = userDAO.find(id);
		Utils.assertExistence(User);
		userDAO.delete(User);
	}

	public void update(User User) {
		User persistent = userDAO.find(User.getId());
		Utils.assertExistence(persistent);
		persistent.update(User);
	}

}
