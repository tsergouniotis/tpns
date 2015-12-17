package com.tpns.article.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.interceptors.Dispatch;
import com.tpns.article.interceptors.ValidateParams;
import com.tpns.article.lucene.LuceneRepository;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.article.repository.CategoryDAO;
import com.tpns.error.BusinessException;
import com.tpns.utils.Assert;

@Stateless
public class ArticleManager {

	@EJB
	private ArticleDAO articleDAO;

	@EJB
	private CategoryDAO categoryDAO;

	@EJB
	private LuceneRepository luceneDAO;

	// The ejb way @Interceptors({ DispatcherInterceptor.class })
	@ValidateParams
	@Dispatch
	public void save(Article article) throws BusinessException {
		Article entity = articleDAO.save(article);
		luceneDAO.save(entity);
	}

	public Article find(Long id) {
		return articleDAO.find(id);
	}

	public List<Article> findAll() {
		return articleDAO.findAll();
	}

	public List<Article> findByStatus(ArticleStatus status) {
		Assert.notNull(status);
		return articleDAO.findByStatus(status);
	}

	public List<Article> findByCategory(String categoryName) {
		Assert.notNull(categoryName);
		return articleDAO.findByCategory(categoryName);
	}

	public List<Article> findByKey(String key) {
		return luceneDAO.findArticles(key);
	}

	public void delete(Long id) {
		Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	@ValidateParams
	public void update(Long articleId, Article article) throws BusinessException {
		Article persistent = articleDAO.find(articleId);
		Assert.notNull(persistent);
		persistent.update(article);
	}

}
