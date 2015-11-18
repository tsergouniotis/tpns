package com.tpns.article.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "article", namespace = "{urn:com.tpns}")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String subject;

	private String shortDescription;

	private String content;

	private Category category;

	private Set<MediaResource> resources = new HashSet<MediaResource>();

	private Calendar createdAt;

	private Calendar updatedAt;

	private Calendar postedAt;

	@XmlElement(name = "id")
	public Long getId() {
		return id;
	}

	@XmlElement(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@XmlElement(name = "shortDescription")
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@XmlElement(name = "resources")
	public Set<MediaResource> getResources() {
		return resources;
	}

	public void setResources(Set<MediaResource> resources) {
		this.resources = resources;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@XmlElement(name = "category")
	public Category getCategory() {
		return category;
	}

	@XmlElement(name = "createdAt")
	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "updatedAt")
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

	public void update(Article article) {
		this.content = article.getContent();
		this.subject = article.getSubject();
	}

}
