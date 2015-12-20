package com.tpns.article.repository;

import com.tpns.article.domain.ApplicationParameter;
import com.tpns.repository.GenericDAO;

public interface ApplicationParameterDAO extends GenericDAO<ApplicationParameter, String> {

	String value(String key);

}
