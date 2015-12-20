package com.tpns.article.lucene.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
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

import com.tpns.article.repository.LuceneFields;
import com.tpns.utils.StreamUtils;

public class LuceneTestCase {

	private static final String INDEX_LOCATION = "/home/sergouniotis/Downloads/tpns/index";
	private static final String DOCUMENT_LOCATION = "/home/sergouniotis/Downloads/lucene/documents";

	@Test
	public void testLucene() {

		try {
			// Analyzer analyzer = ;

			File directoryAsFileObject = new File(INDEX_LOCATION);

			// Directory directory = new RAMDirectory();

			Directory directory = new SimpleFSDirectory(directoryAsFileObject.toPath(), NativeFSLockFactory.INSTANCE);

			/*			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			 try (IndexWriter indexWriter = new IndexWriter(directory, config)) {

			 File docs = new File(DOCUMENT_LOCATION);

			 for (File doc : docs.listFiles()) {
			 addDoc(indexWriter, doc);

			 }
			 }*/

			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			QueryParser parser = new QueryParser(LuceneFields.CONTENT.name(), new StandardAnalyzer());

			Query parse = parser.parse("τρυπα~1");

			TopDocs foo = indexSearcher.search(parse, 5);

			ScoreDoc[] scoreDocs = foo.scoreDocs;

			for (int i = 0; i < scoreDocs.length; i++) {
				ScoreDoc scoreDoc = scoreDocs[i];
				int docID = scoreDoc.doc;

				Document doc = indexSearcher.doc(docID);

				System.out.println(doc.get(LuceneFields.CONTENT.name()));
				System.out.println(doc.get(LuceneFields.TITLE.name()));

			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private void addDoc(IndexWriter indexWriter, File doc) throws IOException, FileNotFoundException {
		try (FileInputStream is = new FileInputStream(doc)) {
			String content = StreamUtils.read(is);
			Document document = createDocument(content, doc.getName());

			indexWriter.addDocument(document);
		}
	}

	private Document createDocument(String content, String name) {
		Document document = new Document();
		document.add(new LongField(LuceneFields.ID.name(), 1L, Field.Store.YES));
		document.add(new StringField(LuceneFields.TITLE.name(), name, Field.Store.YES));
		document.add(new TextField(LuceneFields.CONTENT.name(), content, Field.Store.YES));
		return document;
	}

}
