package com.tpns.article.repository;

import java.util.Calendar;

import com.tpns.article.domain.Article;

public class ArticleListener {

	public void preUpdate(final Article entity) {
		entity.setUpdatedAt(Calendar.getInstance());
	}

}
