package com.tpns.article.filter;

import java.io.Serializable;

public class ArticleFilter implements Serializable {

	private static final long serialVersionUID = 5203973638752440082L;

	private String status;

	private Long authorId;

	public ArticleFilter() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

}
