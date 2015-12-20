package com.tpns.article.repository.test;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.article.domain.Article;
import com.tpns.article.managers.ArticleManager;
import com.tpns.error.BusinessException;

@RunWith(Arquillian.class)
public class ArticlePersistentTest {

	@EJB
	private ArticleManager manager;

	@Deployment
	public static Archive<?> createDeployment() {

		JavaArchive[] jsoup = Maven.resolver().resolve("org.jsoup:jsoup:1.7.2").withTransitivity().as(JavaArchive.class);

		JavaArchive[] luceneCore = Maven.resolver().resolve("org.apache.lucene:lucene-core:5.4.0").withTransitivity().as(JavaArchive.class);
		JavaArchive[] luceneQueryParser = Maven.resolver().resolve("org.apache.lucene:lucene-queryparser:5.4.0").withTransitivity().as(JavaArchive.class);
		JavaArchive[] luceneAnalyzers = Maven.resolver().resolve("org.apache.lucene:lucene-analyzers-common:5.4.0").withTransitivity().as(JavaArchive.class);

		WebArchive shrinkWrap = ShrinkWrap.create(WebArchive.class);
		WebArchive war = addPackages(shrinkWrap).addClass(JsoupUtils.class).addAsResource("META-INF/persistence.xml").addAsResource("META-INF/orm.xml")
				.addAsWebInfResource("META-INF/beans.xml", "beans.xml");

		war.addAsLibraries(jsoup);
		war.addAsLibraries(luceneCore);
		war.addAsLibraries(luceneQueryParser);
		war.addAsLibraries(luceneAnalyzers);
		return war;
	}

	private static WebArchive addPackages(WebArchive shrinkWrap) {
		return addCommonPackages(shrinkWrap).addPackages(true, "com.tpns.article.domain").addPackages(true, "com.tpns.article.repository")
				.addPackages(true, "com.tpns.article.managers").addPackages(true, "com.tpns.article.lucene").addPackages(true, "com.tpns.article.converters")
				.addPackages(true, "com.tpns.article.dto").addPackages(true, "com.tpns.article.conf");
	}

	private static WebArchive addCommonPackages(WebArchive shrinkWrap) {
		return shrinkWrap.addPackages(true, "com.tpns.error").addPackages(true, "com.tpns.repository").addPackages(true, "com.tpns.common").addPackages(true, "com.tpns.utils");
	}

	@Test
	public void test() {
		try {
			List<Article> Articles = JsoupUtils.parseIndexPage("http://www.tovima.gr");
			for (Article article : Articles) {
				article.setShortDescription(article.getContent().substring(0, 30));
				manager.save(article);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
