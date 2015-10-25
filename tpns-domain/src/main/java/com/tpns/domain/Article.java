package com.tpns.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "article", namespace = "{urn:com.tpns}")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String subject;

	private String text;

	private Calendar createdAt;

	private Calendar updatedAt;

	@XmlElement(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
		this.text = article.getText();
		this.subject = article.getSubject();
	}

}
