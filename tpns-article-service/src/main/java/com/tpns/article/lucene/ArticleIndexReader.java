package com.tpns.article.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import com.tpns.article.domain.Article;

@RequestScoped
public class ArticleIndexReader {

	@Inject
	private Directory directory;

	@Inject
	@LuceneArticleConverter
	private DocumentConverter<Article> documentConverter;

	public List<Article> search(String key, String field) throws Exception {

		QueryParser parser = new QueryParser(field, new StandardAnalyzer());

		Query parse = parser.parse(key);

		try (IndexReader indexReader = DirectoryReader.open(directory)) {

			IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			TopDocs foo = indexSearcher.search(parse, 5);

			ScoreDoc[] scoreDocs = foo.scoreDocs;

			return toArticles(indexSearcher, scoreDocs);
		}

	}

	private List<Article> toArticles(IndexSearcher indexSearcher, ScoreDoc[] scoreDocs) throws IOException {
		List<Article> result = new ArrayList<>();
		for (int i = 0; i < scoreDocs.length; i++) {
			ScoreDoc scoreDoc = scoreDocs[i];
			int docID = scoreDoc.doc;

			Document doc = indexSearcher.doc(docID);

			result.add(documentConverter.convert(doc));

		}
		return result;
	}

}
