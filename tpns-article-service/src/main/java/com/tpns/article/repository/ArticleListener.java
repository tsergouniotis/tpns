package com.tpns.article.repository;

import java.util.Calendar;

import com.tpns.domain.article.Article;

public class ArticleListener {

	public void preUpdate(Article entity) {
		entity.setUpdatedAt(Calendar.getInstance());
	}

}
