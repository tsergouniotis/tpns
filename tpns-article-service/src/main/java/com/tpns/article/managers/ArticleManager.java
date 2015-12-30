package com.tpns.article.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.interceptors.ArticleManagerInterceptor;
import com.tpns.article.interceptors.Dispatch;
import com.tpns.article.lucene.LuceneRepository;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.common.domain.errors.BusinessException;
import com.tpns.utils.Assert;

@Stateless
@Interceptors(ArticleManagerInterceptor.class)
public class ArticleManager {

	@Inject
	private ArticleDAO articleDAO;

	@Inject
	private LuceneRepository luceneDAO;

	@Dispatch
	public void save(@Valid Article article) throws BusinessException {
		final Article entity = articleDAO.save(article);
		luceneDAO.save(entity);
	}

	public Article find(final Long id) {
		return articleDAO.find(id);
	}

	public List<Article> findAll() {
		return articleDAO.findAll();
	}

	public List<Article> findByStatus(final ArticleStatus status) {
		Assert.notNull(status);
		return articleDAO.findByStatus(status);
	}

	public List<Article> findByCategory(final String categoryName) {
		Assert.notNull(categoryName);
		return articleDAO.findByCategory(categoryName);
	}

	public List<Article> findByKey(final String key) {
		return luceneDAO.findArticles(key);
	}

	public void delete(@NotNull final Long id) {
		final Article article = articleDAO.find(id);
		Assert.notNull(article);
		articleDAO.delete(article);
	}

	public void update(final Long articleId, @Valid Article article) throws BusinessException {
		final Article persistent = articleDAO.find(articleId);
		Assert.notNull(persistent);
		persistent.update(article);
	}

}
