package com.tpns.article.managed.beans.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.Category;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;

@ManagedBean
@ViewScoped
public class EditArticleBean implements Serializable {

	private final static Logger LOGGER = LoggerFactory.getLogger(EditArticleBean.class);

	private static final long serialVersionUID = -7958535950984588697L;

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;

	private Map<String, Category> availableCategories = new HashMap<String, Category>();

	private Article selectedArticle;

	private List<MediaResource> articleImages = new ArrayList<MediaResource>();

	private List<MediaResource> articleVideos = new ArrayList<MediaResource>();

	private List<MediaResource> articleAudio = new ArrayList<MediaResource>();

	@PostConstruct
	public void init() {
		// TODO
		// check in faces context and if there is an article load it ()
		LOGGER.info("New Article");
		selectedArticle = new Article();
		updateBeanFromArticle();
		for (Category category : categoryService.getCategories()) {
			availableCategories.put(category.getName(), category);
		}
	}

	private void updateBeanFromArticle() {
		articleImages.clear();
		articleVideos.clear();
		articleAudio.clear();
		for (MediaResource mediaResource : selectedArticle.getResources()) {
			switch (mediaResource.getType()) {
			case IMAGE:
				articleImages.add(mediaResource);
			case VIDEO:
				articleVideos.add(mediaResource);
			case AUDIO:
				articleAudio.add(mediaResource);
			}
		}
	}

	private void updateArticleFromBean() {
		List<MediaResource> articleMedia = new ArrayList<MediaResource>();
		articleMedia.addAll(articleImages);
		articleMedia.addAll(articleVideos);
		articleMedia.addAll(articleAudio);
		selectedArticle.setResources(articleMedia);
	}

	/*
	 * JSF Actions
	 * */
	public void saveArticle() {
		updateArticleFromBean();
		articleService.save(selectedArticle);
	}

	public void reloadArticle() {
		selectedArticle = new Article();
		updateBeanFromArticle();
	}

	public void addImage() {
		MediaResource imageMediaResource = new MediaResource();
		imageMediaResource.setType(MediaResourceType.IMAGE);
		articleImages.add(imageMediaResource);
	}

	public void addVideo() {
		MediaResource imageMediaResource = new MediaResource();
		imageMediaResource.setType(MediaResourceType.VIDEO);
		articleVideos.add(imageMediaResource);
	}

	public void addAudio() {
		MediaResource imageMediaResource = new MediaResource();
		imageMediaResource.setType(MediaResourceType.AUDIO);
		articleAudio.add(imageMediaResource);
	}

	/*
	 * Getter - setters
	 * */
	public Article getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(Article selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public Map<String, Category> getAvailableCategories() {
		return availableCategories;
	}

	public void setAvailableCategories(Map<String, Category> availableCategories) {
		this.availableCategories = availableCategories;
	}

	public List<MediaResource> getArticleImages() {
		return articleImages;
	}

	public void setArticleImages(List<MediaResource> articleImages) {
		this.articleImages = articleImages;
	}

	public List<MediaResource> getArticleVideos() {
		return articleVideos;
	}

	public void setArticleVideos(List<MediaResource> articleVideos) {
		this.articleVideos = articleVideos;
	}

	public List<MediaResource> getArticleAudio() {
		return articleAudio;
	}

	public void setArticleAudio(List<MediaResource> articleAudio) {
		this.articleAudio = articleAudio;
	}

}
