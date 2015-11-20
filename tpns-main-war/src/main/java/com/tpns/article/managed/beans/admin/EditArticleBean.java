package com.tpns.article.managed.beans.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.tpns.article.domain.Article;

@ManagedBean
@RequestScoped
public class EditArticleBean implements Serializable {

	private static final long serialVersionUID = -7958535950984588697L;

	private Article selectedArticle;

	@PostConstruct
	public void init() {
		// TODO
		// check in faces context and if there is an article load it ()
		selectedArticle = new Article();
	}

	public Article getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(Article selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

}
