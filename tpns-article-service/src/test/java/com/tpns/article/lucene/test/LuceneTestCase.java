package com.tpns.article.lucene.test;

import java.io.File;

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
import org.apache.lucene.store.NativeFSLockFactory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Assert;
import org.junit.Test;

import com.tpns.article.lucene.LuceneFields;

public class LuceneTestCase {

	private static final String INDEX_LOCATION = "/home/sergouniotis/Downloads/tpns/index";

	@Test
	public void testLucene() {

		try {
			// Analyzer analyzer = ;

			final File directoryAsFileObject = new File(INDEX_LOCATION);

			// Directory directory = new RAMDirectory();

			final Directory directory = new SimpleFSDirectory(directoryAsFileObject.toPath(), NativeFSLockFactory.INSTANCE);

			/*
			 * IndexWriterConfig config = new IndexWriterConfig(new
			 * StandardAnalyzer()); try (IndexWriter indexWriter = new
			 * IndexWriter(directory, config)) {
			 *
			 * File docs = new File(DOCUMENT_LOCATION);
			 *
			 * for (File doc : docs.listFiles()) { addDoc(indexWriter, doc);
			 *
			 * } }
			 */

			final IndexReader indexReader = DirectoryReader.open(directory);
			final IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			final QueryParser parser = new QueryParser(LuceneFields.CONTENT.name(), new StandardAnalyzer());

			final Query parse = parser.parse("news");

			final TopDocs foo = indexSearcher.search(parse, 5);

			final ScoreDoc[] scoreDocs = foo.scoreDocs;

			for (final ScoreDoc scoreDoc : scoreDocs) {
				final int docID = scoreDoc.doc;

				final Document doc = indexSearcher.doc(docID);

				System.out.println(doc.get(LuceneFields.CONTENT.name()));
				System.out.println(doc.get(LuceneFields.TITLE.name()));

			}

		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
