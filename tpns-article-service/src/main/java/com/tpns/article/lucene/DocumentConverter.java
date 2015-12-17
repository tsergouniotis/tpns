package com.tpns.article.lucene;

import org.apache.lucene.document.Document;

public interface DocumentConverter<T> {

	Document convert(T t);

	T convert(Document doc);

}
