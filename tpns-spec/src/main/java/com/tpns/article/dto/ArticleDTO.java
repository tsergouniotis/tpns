package com.tpns.article.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "article", namespace = "{urn:com.tpns}")
public class ArticleDTO implements Serializable {

	private static final long serialVersionUID = -8240430297526892096L;

	private Long id;

	private String subject;

	private String shortDescription;

	private String content;

	private CategoryDTO category;

	private List<String> imageUrls;

	private List<String> videoUrls;

	private List<String> audioUrls;

	// TODO
	// private Set<Keyword> keywords;

	private Calendar createdAt;

	private Calendar updatedAt;

	private Calendar postedAt;

	private Set<String> destinations;

	public ArticleDTO() {

	}

	public void addImage(String imageUrl) {
		imageUrls.add(imageUrl);
	}

	public void addVideo(String videoUrl) {
		videoUrls.add(videoUrl);
	}

	public void addAudio(String audioUrl) {
		audioUrls.add(audioUrl);
	}

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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@XmlElement(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@XmlElement(name = "category")
	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public List<String> getImageUrls() {
		if (null == imageUrls) {
			this.imageUrls = new ArrayList<>();
		}
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getVideoUrls() {
		if (null == videoUrls) {
			this.videoUrls = new ArrayList<>();
		}
		return videoUrls;
	}

	public void setVideoUrls(List<String> videoUrls) {
		this.videoUrls = videoUrls;
	}

	public List<String> getAudioUrls() {
		if (null == audioUrls) {
			this.audioUrls = new ArrayList<>();
		}
		return audioUrls;
	}

	public void setAudioUrls(List<String> audioUrls) {
		this.audioUrls = audioUrls;
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

	@XmlElement(name = "postedAt")
	public Calendar getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Calendar postedAt) {
		this.postedAt = postedAt;
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

}
