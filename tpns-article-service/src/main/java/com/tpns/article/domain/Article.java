package com.tpns.article.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Article implements Serializable {

	private static final long serialVersionUID = 2595509613398948593L;

	private Long id;

	private String subject;

	private Long authorId;

	private String shortDescription;

	private String content;

	private Category category;

	private ArticleStatus status;

	private List<MediaResource> resources = new ArrayList<MediaResource>();

	private Set<Keyword> keywords;

	private Calendar createdAt;

	private Calendar updatedAt;

	private Calendar postedAt;

	private Long version;

	private transient Set<String> destinations;

	/**
	 * JPA constructor
	 */
	protected Article() {
		this.status = ArticleStatus.CREATED;
	}

	private Article(Long id, String subject, String content) {
		this();
		this.id = id;
		this.subject = subject;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public List<MediaResource> getResources() {
		return resources;
	}

	public void setResources(List<MediaResource> resources) {
		this.resources = resources;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Calendar getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Calendar postedAt) {
		this.postedAt = postedAt;
	}

	public Long getVersion() {
		return version;
	}

	public Set<String> getDestinations() {
		if (null == this.destinations) {
			this.destinations = new HashSet<>();
		}
		return destinations;
	}

	public void setDestinations(Set<String> destinations) {
		this.destinations = destinations;
	}

	public void update(Article article) {
		this.status = article.getStatus();
		this.content = article.getContent();
		this.subject = article.getSubject();
		this.shortDescription = article.getShortDescription();
		this.category = article.getCategory();
		this.resources = article.getResources();
	}

	public static Article create(Long id, String title, String content) {
		return new Article(id, title, content);

	}

	public static Article create(String subject, String shortDescription, String content, Category category, Long authorId, ArticleStatus status, Calendar createdAt,
			Calendar updatedAt, Calendar postedAt, Set<String> destinations, List<MediaResource> mediaResources) {
		Article article = new Article();
		article.setSubject(subject);
		article.setShortDescription(shortDescription);
		article.setContent(content);
		article.setCategory(category);
		article.setAuthorId(authorId);
		article.setStatus(status);
		article.setCreatedAt(createdAt);
		article.setUpdatedAt(updatedAt);
		article.setPostedAt(postedAt);
		article.setDestinations(destinations);
		article.setResources(mediaResources);
		return article;
	}

}
