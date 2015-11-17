package com.tpns.article.resource.test;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.article.domain.Article;
import com.tpns.article.providers.TpnsResponseFilter;
import com.tpns.article.repository.ArticleDAO;
import com.tpns.article.resources.ArticleResource;
import com.tpns.article.services.ArticleService;
import com.tpns.article.validation.constraints.ExistingCategory;
import com.tpns.article.validation.validators.ArticleValidator;
import com.tpns.repository.GenericDAO;
import com.tpns.utils.StringUtils;

@RunWith(Arquillian.class)
public class ArticleResourceTest {

	private static final String INPUT_JSON = "data/article.json";
	private static final String ARTICLE_SERVICE_URL = "http://127.0.0.1:8080/tpns/v1/article";

	@Deployment
	public static WebArchive createDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class, "tpns.war").addPackages(true, ArticleResource.class.getPackage()).addPackages(true, ArticleService.class.getPackage())
				.addPackages(true, ArticleDAO.class.getPackage()).addPackages(true, GenericDAO.class.getPackage()).addPackages(true, Article.class.getPackage())
				.addPackages(true, StringUtils.class.getPackage()).addPackages(true, ExistingCategory.class.getPackage()).addPackages(true, ArticleValidator.class.getPackage())
				.addPackage(TpnsResponseFilter.class.getPackage()).addClass(BasicTpnsAuthenticator.class).addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml", "META-INF/orm.xml").addAsResource("META-INF/validation.xml", "META-INF/validation.xml")
				.addAsResource("META-INF/constraints.xml", "META-INF/constraints.xml").addAsResource(INPUT_JSON, INPUT_JSON)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}

	@Test
	// @RunAsClient
	public void testCDI() throws Exception {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INPUT_JSON);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ARTICLE_SERVICE_URL).register(new BasicTpnsAuthenticator("author", "author"));

		JsonReader jsonReader = Json.createReader(resourceAsStream);
		JsonObject object = jsonReader.readObject();
		jsonReader.close();

		Entity<?> entity = Entity.json(object.toString());
		Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
		Status status = Response.Status.fromStatusCode(res.getStatus());
		org.junit.Assert.assertEquals(Response.Status.OK, status);

		client.close();
	}

}
