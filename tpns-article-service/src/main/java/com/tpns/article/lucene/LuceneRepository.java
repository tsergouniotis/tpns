package com.tpns.article.lucene;

import java.util.Collections;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.repository.LuceneFields;

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
			return indexReader.search(key, LuceneFields.CONTENT.name());
		} catch (Exception e) {
			LOGGER.info("No articles found.");
			return Collections.emptyList();
		}
	}

	public void delete(Long id) {

		indexWriter.delete(id);

	}

}
