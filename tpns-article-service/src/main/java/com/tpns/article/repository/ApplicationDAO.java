package com.tpns.article.repository;

import java.util.List;

import com.tpns.common.domain.Application;
import com.tpns.repository.GenericDAO;

public interface ApplicationDAO extends GenericDAO<Application, Long> {

	List<Application> find(List<String> ids);

}
