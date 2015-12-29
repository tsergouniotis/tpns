package com.tpns.article.repository.test;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public final class TestUtils {

	private TestUtils() {

	}

	public static void enrichWithLibraries(WebArchive war) {
		JavaArchive[] jsoup = Maven.resolver().resolve("org.jsoup:jsoup:1.7.2").withTransitivity().as(JavaArchive.class);
		JavaArchive[] luceneCore = Maven.resolver().resolve("org.apache.lucene:lucene-core:5.4.0").withTransitivity().as(JavaArchive.class);
		JavaArchive[] luceneQueryParser = Maven.resolver().resolve("org.apache.lucene:lucene-queryparser:5.4.0").withTransitivity().as(JavaArchive.class);
		JavaArchive[] luceneAnalyzers = Maven.resolver().resolve("org.apache.lucene:lucene-analyzers-common:5.4.0").withTransitivity().as(JavaArchive.class);
		JavaArchive[] cassandraLibraries = Maven.resolver().resolve("com.datastax.cassandra:cassandra-driver-core:2.1.9").withTransitivity().as(JavaArchive.class);

		war.addAsLibraries(jsoup);
		war.addAsLibraries(luceneCore);
		war.addAsLibraries(luceneQueryParser);
		war.addAsLibraries(luceneAnalyzers);
		war.addAsLibraries(cassandraLibraries);
	}

}
