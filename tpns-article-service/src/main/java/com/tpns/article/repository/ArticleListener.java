package com.tpns.article.repository;

import com.tpns.article.domain.Article;
import com.tpns.utils.CalendarUtils;

public class ArticleListener {

	public void prePersist(final Article entity) {
		entity.audit();
	}

	public void preUpdate(final Article entity) {
		entity.setUpdatedAt(CalendarUtils.createTpnsCalendar());
	}

}
