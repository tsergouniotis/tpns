package com.tpns.article.lucene;

import java.util.Collections;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;

@Stateless
public class LuceneRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneRepository.class);

	@Inject
	private ArticleIndexWriter indexWriter;

	@Inject
	private ArticleIndexReader indexReader;

	@Asynchronous
	public void save(Article article) {
		indexWriter.index(article);
	}

	public List<Article> findArticles(String key) {
		try {
			return indexReader.search(key);
		} catch (Exception e) {
			LOGGER.info("No articles found.");
			return Collections.emptyList();
		}
	}

}
