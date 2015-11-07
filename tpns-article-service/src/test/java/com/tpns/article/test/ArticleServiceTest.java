package com.tpns.article.test;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.repository.ArticleDAO;
import com.tpns.article.services.ArticleService;
import com.tpns.domain.article.Article;
import com.tpns.domain.utils.StringUtils;
import com.tpns.repository.GenericDAO;

@RunWith(Arquillian.class)
public class ArticleServiceTest {

	Logger log = LoggerFactory.getLogger(ArticleServiceTest.class);

	@EJB
	private ArticleService articleService;

	@Deployment
	public static WebArchive createDeployment() {
		//		File[] dependencies = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.7").withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "tpns.war")
				.addPackages(true, ArticleService.class.getPackage())
				.addPackages(true, ArticleDAO.class.getPackage())
				.addPackages(true, GenericDAO.class.getPackage())
				.addPackages(true, Article.class.getPackage())
				.addPackages(true, StringUtils.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");


		return war;
	}

	@Test
	public void testCDI() throws Exception {
		Article find = articleService.find(1L);
		log.info(find.toString());
	}

}
