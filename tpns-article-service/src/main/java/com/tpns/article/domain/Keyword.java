package com.tpns.article.domain;

import java.util.Set;

public class Keyword {

	private String key;

	private Set<Article> articles;

	private Long version;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Long getVersion() {
		return version;
	}

}
