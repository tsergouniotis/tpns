package com.tpns.article.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.repository.ApplicationDAO;
import com.tpns.common.Application;

@Stateless
public class ApplicationManager {

	@EJB
	private ApplicationDAO applicationDao;

	public List<Application> findAll() {
		return applicationDao.findAll();
	}
}
