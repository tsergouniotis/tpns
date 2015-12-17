package com.tpns.article.lucene;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;

@RequestScoped
public class ArticleIndexWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleIndexWriter.class.getPackage().getName());

	@Inject
	private Directory directory;

	@Inject
	@LuceneArticleConverter
	private DocumentConverter<Article> documentConverter;

	public void index(Article article) {
		try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()))) {
			Document document = documentConverter.convert(article);
			indexWriter.addDocument(document);
		} catch (IOException e) {
			LOGGER.error("There was an error while writing on index ", e);
		}

	}

}
