package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.error.BusinessException;

@ManagedBean
@ViewScoped
public class ReviewArticleBean implements Serializable {

	private static final long serialVersionUID = 2981749493215231060L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewArticleBean.class);

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;

	private ArticleDTO selectedArticle;

	// Cache variables
	private Map<String, String> availableCategories = new HashMap<String, String>();

	// Helper variables
	private String selectedImageUrl;
	private String selectedVideoUrl;
	private String selectedAudioUrl;
	private String newImageUrl;
	private String newVideoUrl;
	private String newAudioUrl;

	@PostConstruct
	public void init() {
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("articleId");
		if (null == articleId) {
			LOGGER.error("Review called with empty article ID");
		} else {
			Long articleIdAsLong = Long.parseLong(articleId);
			selectedArticle = articleService.find(articleIdAsLong);
			if (null == selectedArticle) {
				LOGGER.error("Failed to load article with id " + articleId);
			}
		}
		for (String category : categoryService.getCategories()) {
			availableCategories.put(category, category);
		}
	}

	private void clearHelperValues() {
		selectedImageUrl = null;
		selectedVideoUrl = null;
		selectedAudioUrl = null;
		newImageUrl = null;
		newVideoUrl = null;
		newAudioUrl = null;
	}

	/*
	 * JSF Actions
	 */
	public String reviewArticle(boolean publish) {
		LOGGER.debug("Reviewing article: " + selectedArticle.toString());
		try {
			selectedArticle.setStatus(ArticleStatus.READY_FOR_PUBLISH.toString());
			articleService.update(selectedArticle);
			return publish ? "/pages/admin/publishArticle.xhtml" : "/pages/admin/index.xhtml";
		} catch (BusinessException businessException) {
			LOGGER.error("Article validation failed: " + businessException.getMessage());
			JSFUtils.outputBusinessExceptionToComponent(businessException, FacesContext.getCurrentInstance(), "form");
		}
		return null;
	}

	public void reloadArticle() {
		selectedArticle = articleService.find(selectedArticle.getId());
		clearHelperValues();
	}

	public void addImage() {
		selectedArticle.addImage(newImageUrl);
		newImageUrl = null;
	}

	public void addVideo() {
		selectedArticle.addVideo(newVideoUrl);
		newVideoUrl = null;
	}

	public void addAudio() {
		selectedArticle.addAudio(newAudioUrl);
		newAudioUrl = null;
	}

	public void deleteImage() {
		selectedArticle.removeImage(selectedImageUrl);
		selectedImageUrl = null;
	}

	public void deleteVideo() {
		selectedArticle.removeVideo(selectedVideoUrl);
		selectedVideoUrl = null;
	}

	public void deleteAudio() {
		selectedArticle.removeAudio(selectedAudioUrl);
		selectedAudioUrl = null;
	}

	/*
	 * Getter - setters
	 */
	public ArticleDTO getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(ArticleDTO selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public Map<String, String> getAvailableCategories() {
		return availableCategories;
	}

	public void setAvailableCategories(Map<String, String> availableCategories) {
		this.availableCategories = availableCategories;
	}

	public String getSelectedImageUrl() {
		return selectedImageUrl;
	}

	public void setSelectedImageUrl(String selectedImageUrl) {
		this.selectedImageUrl = selectedImageUrl;
	}

	public String getSelectedVideoUrl() {
		return selectedVideoUrl;
	}

	public void setSelectedVideoUrl(String selectedVideoUrl) {
		this.selectedVideoUrl = selectedVideoUrl;
	}

	public String getSelectedAudioUrl() {
		return selectedAudioUrl;
	}

	public void setSelectedAudioUrl(String selectedAudioUrl) {
		this.selectedAudioUrl = selectedAudioUrl;
	}

	public String getNewImageUrl() {
		return newImageUrl;
	}

	public void setNewImageUrl(String newImageUrl) {
		this.newImageUrl = newImageUrl;
	}

	public String getNewVideoUrl() {
		return newVideoUrl;
	}

	public void setNewVideoUrl(String newVideoUrl) {
		this.newVideoUrl = newVideoUrl;
	}

	public String getNewAudioUrl() {
		return newAudioUrl;
	}

	public void setNewAudioUrl(String newAudioUrl) {
		this.newAudioUrl = newAudioUrl;
	}

}