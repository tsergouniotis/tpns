package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.jsf.utils.JSFConstants;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.core.errors.BusinessException;
import com.tpns.user.domain.Roles;

@ManagedBean
@ViewScoped
public class EditArticleBean extends BaseTpnsBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditArticleBean.class);

	private static final long serialVersionUID = -7958535950984588697L;

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;

	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;

	private ArticleDTO selectedArticle;

	// Cache variables
	private Map<String, String> availableCategories = new HashMap<String, String>();

	// Helper variables
	private String newImageUrl;
	private String newVideoUrl;
	private String newAudioUrl;

	@PostConstruct
	public void init() {
		String articleId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(JSFConstants.PARAM_NAME_ARTICLE_ID);
		if (null == articleId) {
			initArticle();
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

	private void initArticle() {
		selectedArticle = new ArticleDTO();
		selectedArticle.setStatus(ArticleStatus.CREATED.toString());
		selectedArticle.setAuthorId(userSessionBean.getUser().getId());
	}

	private void clearHelperValues() {
		newImageUrl = null;
		newVideoUrl = null;
		newAudioUrl = null;
	}

	/*
	 * JSF Actions
	 */
	public String saveArticle() {
		LOGGER.debug("Saving article: " + selectedArticle.toString());
		try {
			if (null == selectedArticle.getId()) {
				articleService.save(selectedArticle);
			} else {
				articleService.update(selectedArticle);
			}
			return "/pages/index.xhtml";
		} catch (BusinessException businessException) {
			LOGGER.error("Article validation failed: " + businessException.getMessage());
			JSFUtils.outputBusinessExceptionToComponent(businessException, FacesContext.getCurrentInstance(), "mainForm");
		}
		return null;
	}

	public String moveArticle(boolean backToIndex) {
		LOGGER.debug("Moving article: " + selectedArticle.toString());
		selectedArticle.setStatus(ArticleStatus.nextStatus(ArticleStatus.valueOf(selectedArticle.getStatus())).toString());
		try {
			articleService.update(selectedArticle);
			if (backToIndex) {
				return "/pages/index.xhtml";
			}
		} catch (BusinessException businessException) {
			LOGGER.error("Article validation failed: " + businessException.getMessage());
			JSFUtils.outputBusinessExceptionToComponent(businessException, FacesContext.getCurrentInstance(), "mainForm");
		}
		return null;
	}

	public void reloadArticle() {
		if (null == selectedArticle.getId()) {
			initArticle();
		} else {
			selectedArticle = articleService.find(selectedArticle.getId());
		}
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

	public void deleteImage(String selectedImageUrl) {
		selectedArticle.removeImage(selectedImageUrl);
	}

	public void deleteVideo(String selectedVideoUrl) {
		selectedArticle.removeVideo(selectedVideoUrl);
	}

	public void deleteAudio(String selectedAudioUrl) {
		selectedArticle.removeAudio(selectedAudioUrl);
	}

	/*
	 * Getter Only
	 */
	public boolean isArticleNew() {
		return null == selectedArticle.getId();
	}

	public boolean isArticleOwnedByUser() {
		return selectedArticle.getAuthorId().equals(userSessionBean.getUser().getId());
	}

	public boolean isArticleAllowEdit() {
		return isUserChiefEditor() || (isArticleOwnedByUser() && (ArticleStatus.CREATED.toString().equals(selectedArticle.getStatus())));
	}

	public boolean isArticleAllowSave() {
		return (isUserChiefEditor() && (ArticleStatus.PUBLISHED.toString().equals(selectedArticle.getStatus())))
				|| (isArticleOwnedByUser() && (ArticleStatus.CREATED.toString().equals(selectedArticle.getStatus())));
	}

	public boolean isUserChiefEditor() {
		return userSessionBean.getUser().hasRole(Roles.CHIEF_EDITOR);
	}

	public boolean isArticleStatusCreated() {
		return ArticleStatus.CREATED.toString().equals(selectedArticle.getStatus());
	}

	public boolean isArticleStatusPosted() {
		return ArticleStatus.POSTED.toString().equals(selectedArticle.getStatus());
	}

	public boolean isArticleStatusReviewed() {
		return ArticleStatus.REVIEWED.toString().equals(selectedArticle.getStatus());
	}

	public boolean isArticleStatusPublished() {
		return ArticleStatus.PUBLISHED.toString().equals(selectedArticle.getStatus());
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

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

}
