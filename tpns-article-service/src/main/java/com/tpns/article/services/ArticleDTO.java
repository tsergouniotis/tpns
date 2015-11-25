package com.tpns.article.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;

public class ArticleDTO implements Serializable {

	private static final long serialVersionUID = -8240430297526892096L;

	private Long id;

	private String subject;

	private String shortDescription;

	private String content;

	private CategoryDTO category;

	private List<String> imageUrls = new ArrayList<String>();

	private List<String> videoUrls = new ArrayList<String>();

	private List<String> audioUrls = new ArrayList<String>();

	//TODO 
	// private Set<Keyword> keywords;

	private Calendar createdAt;

	private Calendar updatedAt;

	private Calendar postedAt;

	private Long version;

	public ArticleDTO() {

	}

	public ArticleDTO(Article article) {
		this.id = article.getId();
		this.subject = article.getSubject();
		this.shortDescription = article.getShortDescription();
		this.content = article.getContent();
		this.category = new CategoryDTO(article.getCategory());
		this.createdAt = article.getCreatedAt();
		this.updatedAt = article.getUpdatedAt();
		this.postedAt = article.getPostedAt();
		// Media conversion
		imageUrls.clear();
		videoUrls.clear();
		audioUrls.clear();
		for (MediaResource mediaResource : article.getResources()) {
			switch (mediaResource.getType()) {
			case IMAGE:
				imageUrls.add(mediaResource.getUrl());
			case VIDEO:
				videoUrls.add(mediaResource.getUrl());
			case AUDIO:
				audioUrls.add(mediaResource.getUrl());
			}
		}
	}

	public Article getArticle() {
		Article article = new Article();
		article.setSubject(subject);
		article.setShortDescription(shortDescription);
		article.setContent(content);
		article.setCategory(category.getCategory());
		article.setCreatedAt(createdAt);
		article.setUpdatedAt(updatedAt);
		article.setPostedAt(postedAt);
		// Media conversion
		List<MediaResource> articleMedia = new ArrayList<MediaResource>();
		for (String imageUrl : imageUrls) {
			articleMedia.add(new MediaResource(MediaResourceType.IMAGE, imageUrl));
		}
		for (String videoUrl : videoUrls) {
			articleMedia.add(new MediaResource(MediaResourceType.VIDEO, videoUrl));
		}
		for (String audioUrl : audioUrls) {
			articleMedia.add(new MediaResource(MediaResourceType.AUDIO, audioUrl));
		}
		article.setResources(articleMedia);
		return article;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getVideoUrls() {
		return videoUrls;
	}

	public void setVideoUrls(List<String> videoUrls) {
		this.videoUrls = videoUrls;
	}

	public List<String> getAudioUrls() {
		return audioUrls;
	}

	public void setAudioUrls(List<String> audioUrls) {
		this.audioUrls = audioUrls;
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

	public void setVersion(Long version) {
		this.version = version;
	}

	public static List<ArticleDTO> convert(List<Article> entityList) {
		List<ArticleDTO> dtoList = new ArrayList<ArticleDTO>();
		for (Article article : entityList) {
			dtoList.add(new ArticleDTO(article));
		}
		return dtoList;
	}
}
