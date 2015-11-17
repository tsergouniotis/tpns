package com.tpns.article.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mediaResource", namespace = "{urn:com.tpns}")
public class MediaResource implements Serializable {

	private static final long serialVersionUID = -6820814412104405821L;

	private Long id;

	private MediaResourceType type;

	private String url;

	private Set<Article> articlesContained = new HashSet<Article>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "type")
	public MediaResourceType getType() {
		return type;
	}

	public void setType(MediaResourceType type) {
		this.type = type;
	}

	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Article> getArticlesContained() {
		return articlesContained;
	}

	public void setArticlesContained(Set<Article> articlesContained) {
		this.articlesContained = articlesContained;
	}

}
