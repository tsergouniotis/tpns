package com.tpns.article.repository.test;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.managers.ArticleManager;
import com.tpns.article.managers.CategoryManager;
import com.tpns.common.domain.errors.BusinessException;
import com.tpns.utils.StringUtils;

@RunWith(Arquillian.class)
public class ArticlePersistentTest {

	@EJB
	private ArticleManager manager;

	@EJB
	private CategoryManager categoryManager;

	@Inject
	@News24ParserQualifier
	private Parser parser;

	@Deployment
	public static Archive<?> createDeployment() {

		final WebArchive shrinkWrap = ShrinkWrap.create(WebArchive.class);
		final WebArchive war = addPackages(shrinkWrap).addClass(ToVimaParser.class).addAsResource("META-INF/persistence.xml").addAsResource("META-INF/orm.xml")
				.addAsResource("META-INF/validation.xml").addAsResource("META-INF/dto-constraints.xml").addAsResource("META-INF/entity-constraints.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		TestUtils.enrichWithLibraries(war);

		return war;
	}

	private static WebArchive addPackages(final WebArchive shrinkWrap) {
		return addCommonPackages(shrinkWrap).addPackages(true, "com.tpns.article.domain").addPackages(true, "com.tpns.article.repository")
				.addPackages(true, "com.tpns.article.managers").addPackages(true, "com.tpns.article.lucene").addPackages(true, "com.tpns.article.converters")
				.addPackages(true, "com.tpns.article.dto").addPackages(true, "com.tpns.article.conf").addPackages(true, "com.tpns.article.interceptors")
				.addPackages(true, "com.tpns.article.validation");
	}

	private static WebArchive addCommonPackages(final WebArchive shrinkWrap) {
		return shrinkWrap.addPackages(true, "com.tpns.common.domain.errors").addPackages(true, "com.tpns.repository").addPackages(true, "com.tpns.common.domain").addPackages(true,
				"com.tpns.utils");
	}

	@Test
	@Transactional
	public void test() {
		try {
			final List<Article> Articles = parser.parse();

			for (final Article article : Articles) {
				article.setAuthorId(2L);
				article.setCategory(categoryManager.getByName("politics"));
				if (StringUtils.hasText(article.getShortDescription()) && article.getShortDescription().length() > 512) {
					article.setShortDescription(article.getShortDescription().substring(0, 511));
				}
				article.setStatus(ArticleStatus.PUBLISHED);
				manager.save(article);
			}
		} catch (final BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
