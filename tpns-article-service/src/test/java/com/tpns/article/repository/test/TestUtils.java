package com.tpns.article.repository.test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import com.tpns.article.domain.ApplicationParameter;

public final class TestUtils {

	private static final String ENTITY_CONSTRAINTS_XML = "META-INF/entity-constraints.xml";

	private static final String DTO_CONSTRAINTS_XML = "META-INF/dto-constraints.xml";

	private static final String VALIDATION_XML = "META-INF/validation.xml";

	private static final String ORM_XML = "META-INF/orm.xml";

	private static final String PERSISTENCE_XML = "META-INF/persistence.xml";

	private static final String[] RESOURCE_NAMES = { ENTITY_CONSTRAINTS_XML, DTO_CONSTRAINTS_XML, VALIDATION_XML, ORM_XML, PERSISTENCE_XML };

	private static final Set<String> RESOURCES = new HashSet<>(Arrays.asList(RESOURCE_NAMES));

	private TestUtils() {

	}

	public static void enrichWithLibraries(final WebArchive war) {
		final JavaArchive[] jsoup = Maven.resolver().resolve("org.jsoup:jsoup:1.7.2").withTransitivity().as(JavaArchive.class);
		final JavaArchive[] luceneCore = Maven.resolver().resolve("org.apache.lucene:lucene-core:5.4.0").withTransitivity().as(JavaArchive.class);
		final JavaArchive[] luceneQueryParser = Maven.resolver().resolve("org.apache.lucene:lucene-queryparser:5.4.0").withTransitivity().as(JavaArchive.class);
		final JavaArchive[] luceneAnalyzers = Maven.resolver().resolve("org.apache.lucene:lucene-analyzers-common:5.4.0").withTransitivity().as(JavaArchive.class);
		final JavaArchive[] cassandraLibraries = Maven.resolver().resolve("com.datastax.cassandra:cassandra-driver-core:2.1.9").withTransitivity().as(JavaArchive.class);

		war.addAsLibraries(jsoup);
		war.addAsLibraries(luceneCore);
		war.addAsLibraries(luceneQueryParser);
		war.addAsLibraries(luceneAnalyzers);
		war.addAsLibraries(cassandraLibraries);
	}

	public static void addResources(final WebArchive war) throws URISyntaxException {

		for (String resource : RESOURCES) {
			URL url = ApplicationParameter.class.getClassLoader().getResource(resource);
			File file = Paths.get(url.toURI()).toFile();
			war.addAsResource(file, resource);
		}

	}

}
