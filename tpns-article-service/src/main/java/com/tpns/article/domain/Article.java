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

	private String shortDescription;

	private String content;

	private Category category;

	private List<MediaResource> resources = new ArrayList<MediaResource>();

	private Set<Keyword> keywords;

	private Calendar createdAt;

	private Calendar updatedAt;

	private Calendar postedAt;

	private Long version;

	private transient Set<String> destinations;

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		this.content = article.getContent();
		this.subject = article.getSubject();
	}

}
