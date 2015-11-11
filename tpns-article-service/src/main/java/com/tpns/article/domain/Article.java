package com.tpns.article.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "article", namespace = "{urn:com.tpns}")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String subject;

	private String content;

	private Category category;

	private String image;

	private String video;

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

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
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

	public void update(Article article) {
		this.content = article.getContent();
		this.subject = article.getSubject();
	}

}
