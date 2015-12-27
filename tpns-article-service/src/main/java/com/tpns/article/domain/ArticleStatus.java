package com.tpns.article.domain;

public enum ArticleStatus {
	CREATED,
	POSTED,
	REVIEWED,
	PUBLISHED;

	public static ArticleStatus nextStatus(ArticleStatus currentStatus) {
		switch (currentStatus) {
		case CREATED:
			return POSTED;
		case POSTED:
			return REVIEWED;
		case PUBLISHED:
			throw new IllegalStateException("Next status called for published article");
		case REVIEWED:
			return PUBLISHED;
		default:
			throw new IllegalArgumentException("Next status was called for null status input");
		}
	}
}
