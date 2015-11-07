package com.tpns.article.repository.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.domain.article.Article;
import com.tpns.domain.article.Category;
import com.tpns.domain.user.Role;
import com.tpns.domain.user.User;

@RunWith(Arquillian.class)
public class ArticlePersistenceTest {

	@PersistenceContext
	private EntityManager em;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClasses(Article.class, User.class, Role.class, Category.class)
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml").addAsManifestResource("META-INF/orm.xml", "orm.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	@ShouldMatchDataSet(value = "data/article.xml", excludeColumns = "image")
	public void testEncryption() {
		Article entity = em.find(Article.class, 1L);
	}

}
