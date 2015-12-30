package com.tpns.article.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.managers.ApplicationManager;
import com.tpns.common.domain.Application;
import com.tpns.utils.CollectionUtils;

@Stateless
public class ApplicationService {

	@EJB
	private ApplicationManager applicationManager;

	public List<String> findAll() {
		final List<Application> applications = applicationManager.findAll();

		final List<String> result = new ArrayList<>();
		if (CollectionUtils.isNonEmpty(applications)) {
			applications.forEach(application -> result.add(application.getClientId()));
		}
		return result;
	}
}
