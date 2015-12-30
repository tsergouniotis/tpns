package com.tpns.article.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tpns.article.repository.ApplicationDAO;
import com.tpns.common.domain.Application;

@Stateless
public class ApplicationManager {

	@Inject
	private ApplicationDAO applicationDao;

	public List<Application> findAll() {
		return applicationDao.findAll();
	}
}
