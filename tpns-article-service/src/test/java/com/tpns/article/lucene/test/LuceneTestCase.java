package com.tpns.article.lucene.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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

import com.tpns.utils.StreamUtils;

public class LuceneTestCase {

	private static final String INDEX_LOCATION = "/home/sergouniotis/Downloads/lucene/index";
	private static final String DOCUMENT_LOCATION = "/home/sergouniotis/Downloads/lucene/documents";
	private static final String CONTENT = "content";
	private static final String SUBJECT = "subject";

	@Test
	public void testLucene() {

		try {
			Analyzer analyzer = new StandardAnalyzer();

			File directoryAsFileObject = new File(INDEX_LOCATION);

			// Directory directory = new RAMDirectory();

			Directory directory = new SimpleFSDirectory(directoryAsFileObject.toPath(), NativeFSLockFactory.INSTANCE);

			// Directory directory = new TpnsDirectory();

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			try (IndexWriter indexWriter = new IndexWriter(directory, config)) {

				File docs = new File(DOCUMENT_LOCATION);

				for (File doc : docs.listFiles()) {
					try (FileInputStream is = new FileInputStream(doc)) {
						String content = StreamUtils.read(is);
						Document document = createDocument(content);

						indexWriter.addDocument(document);
					}

				}
			}

			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			QueryParser parser = new QueryParser(CONTENT, analyzer);

			Query parse = parser.parse("access");

			TopDocs foo = indexSearcher.search(parse, 5);

			ScoreDoc[] scoreDocs = foo.scoreDocs;

			for (int i = 0; i < scoreDocs.length; i++) {
				ScoreDoc scoreDoc = scoreDocs[i];
				int docID = scoreDoc.doc;

				Document doc = indexSearcher.doc(docID);

				String string = doc.get(CONTENT);
				System.out.println(string);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private Document createDocument(String content) {
		Document document = new Document();
		document.add(new StringField(SUBJECT, "Title", Field.Store.YES));
		document.add(new TextField(CONTENT, content, Field.Store.YES));
		return document;
	}

}
