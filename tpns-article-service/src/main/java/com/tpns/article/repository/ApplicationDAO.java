package com.tpns.article.repository;

import java.util.List;

import com.tpns.common.Application;
import com.tpns.repository.GenericDAO;

public interface ApplicationDAO extends GenericDAO<Application, Long> {

	List<Application> find(String... ids);

}
