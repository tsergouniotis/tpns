package com.tpns.article.domain;

import java.util.Calendar;

public class Audit {

	private Long id;

	private String who;

	private Calendar when;

	private ArticleStatus what;

	private Audit() {

	}

	private Audit(String who, ArticleStatus what) {
		this.who = who;
		this.what = what;
	}

	public Long getId() {
		return id;
	}

	public String getWho() {
		return who;
	}

	public Calendar getWhen() {
		return when;
	}

	public ArticleStatus getWhat() {
		return what;
	}

	public static Audit create(String who, ArticleStatus what) {
		return new Audit(who, what);
	}

}
