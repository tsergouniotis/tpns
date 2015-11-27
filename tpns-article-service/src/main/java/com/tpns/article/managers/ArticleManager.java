package com.tpns.article.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.domain.Article;
import com.tpns.article.interceptors.Dispatch;
import com.tpns.article.interceptors.ValidateParams;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.error.BusinessException;
import com.tpns.utils.Assert;

@Stateless
public class ArticleManager {

	@EJB
	private ArticleDAO articleDAO;

	// The ejb way @Interceptors({ DispatcherInterceptor.class })
	@ValidateParams
	@Dispatch
	public void save(Article article) throws BusinessException {
		articleDAO.save(article);
	}

	public Article find(Long id) {
		return articleDAO.find(id);
	}

	public List<Article> findAll() {
		return articleDAO.findAll();
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	@ValidateParams
	public void update(Article article) throws BusinessException {
		Article persistent = articleDAO.find(article.getId());
		Assert.notNull(persistent);
		persistent.update(article);
	}

}
