package com.tpns.article.repository;

import java.util.Calendar;

import com.tpns.domain.Article;

public class ArticleListener {

	public void prePersist(Article entity) {
		entity.setCreatedAt(Calendar.getInstance());
	}

	public void preUpdate(Article entity) {
		entity.setUpdatedAt(Calendar.getInstance());
	}

}
