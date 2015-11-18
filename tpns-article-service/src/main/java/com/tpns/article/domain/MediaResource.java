package com.tpns.article.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class MediaResource implements Serializable {

	private static final long serialVersionUID = -6820814412104405821L;

	private MediaResourceType type;

	private String url;

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

}
