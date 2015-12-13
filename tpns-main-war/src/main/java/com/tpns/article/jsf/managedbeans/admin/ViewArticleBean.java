package com.tpns.article.jsf.managedbeans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import com.tpns.utils.StringUtils;

@ManagedBean
@ViewScoped
public class ViewArticleBean extends BaseTpnsManagedBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewArticleBean.class);

	private static final long serialVersionUID = 6512180737722350944L;

	@EJB
	private CategoryService categoryService;

	@EJB
	private ArticleService articleService;

	private ArticleDTO selectedArticle;

	private List<ArticleDTO> availableArticles;
	private Map<String, String> articleStatusDisplay;

	@PostConstruct
	public void init() {
		this.availableArticles = articleService.findAll();
		this.articleStatusDisplay = new HashMap<String, String>();
		this.articleStatusDisplay.put(ArticleStatus.READY_FOR_REVIEW.toString(),
				JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforreview"));
		this.articleStatusDisplay.put(ArticleStatus.READY_FOR_PUBLISH.toString(),
				JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforpublish"));
		this.articleStatusDisplay.put(ArticleStatus.PUBLISHED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.publish"));
	}

	public String getArticleStatus(String status) {
		String statusString = articleStatusDisplay.get(status);
		return StringUtils.isEmptyString(statusString) ? status : statusString;
	}

	public boolean isReviewAllowed(String status) {
		return ArticleStatus.READY_FOR_REVIEW.toString().equals(status);
	}

	public boolean isPublishAllowed(String status) {
		return ArticleStatus.READY_FOR_PUBLISH.toString().equals(status);
	}

	/*
	 * JSF Actions
	 * */

	public String editArticle() {
		return "/pages/admin/editArticle.xhtml";
	}

	public String reviewArticle() {
		return "/pages/admin/reviewArticle.xhtml";
	}

	public String publishArticle() {
		return "/pages/admin/publishArticle.xhtml";
	}

	public String deleteArticle() {
		if (null == selectedArticle) {
			LOGGER.error("Unexpectidely asked to delete with no article selected");
		} else {
			articleService.delete(selectedArticle.getId());
			availableArticles.remove(selectedArticle);
			selectedArticle = null;
		}
		return null;
	}

	/*
	 * Getter - setters
	 * */
	public List<ArticleDTO> getAvailableArticles() {
		return availableArticles;
	}

	public void setAvailableArticles(List<ArticleDTO> availableArticles) {
		this.availableArticles = availableArticles;
	}

	public ArticleDTO getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(ArticleDTO selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

}
