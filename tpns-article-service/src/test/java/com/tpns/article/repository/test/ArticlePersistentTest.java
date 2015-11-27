package com.tpns.article.repository.test;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.registry.infomodel.User;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.Category;
import com.tpns.article.domain.Keyword;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;

@RunWith(Arquillian.class)
public class ArticlePersistentTest {

	@PersistenceContext
	private EntityManager em;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClasses(Article.class, User.class, Role.class, Category.class, Keyword.class, MediaResource.class, MediaResourceType.class)
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml").addAsManifestResource("META-INF/orm.xml", "orm.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "WEB-INF/beans.xml");
	}

	// @Test
	// @ShouldMatchDataSet(value = "data/article.xml", excludeColumns = "image")
	public void testEncryption() {
		Article entity = em.find(Article.class, 1L);
		Assert.assertNotNull(entity);
	}

	@Test
	public void testKeywords() {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Article> query = criteriaBuilder.createQuery(Article.class);

		Root<Article> article = query.from(Article.class);

		List<Predicate> predicates = new ArrayList<>();

		Join<Article, Keyword> keywords1 = article.join("keywords");
		Predicate p1 = criteriaBuilder.equal(keywords1.get("key"), "foo");

		Join<Article, Keyword> keywords2 = article.join("keywords");
		Predicate p2 = criteriaBuilder.equal(keywords2.get("key"), "foo2");

		Predicate predicate = criteriaBuilder.and(p1, p2);

		predicates.add(predicate);

		List<Article> result = em.createQuery(query.select(article).where(predicates.toArray(new Predicate[predicates.size()])).distinct(true)).getResultList();

	}

}
