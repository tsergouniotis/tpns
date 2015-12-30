package com.tpns.article.resource.test;

import java.io.InputStream;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
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

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.managers.BasicTpnsAuthenticator;
import com.tpns.article.repository.test.TestUtils;

@RunWith(Arquillian.class)
public class ArticleResourceTest {

	private static final String INPUT_JSON = "data/article.json";
	private static final String ARTICLE_SERVICE_URL = "http://localhost:8080/tpns/v1/article";

	@Deployment
	public static WebArchive createDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class, "tpns.war")
				.addPackages(true, "com.tpns")
				.addClass(BasicTpnsAuthenticator.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
				.addAsResource("META-INF/validation.xml", "META-INF/validation.xml")
				.addAsResource("META-INF/dto-constraints.xml", "META-INF/dto-constraints.xml")
				.addAsResource("META-INF/entity-constraints.xml", "META-INF/entity-constraints.xml")
				.addAsResource(INPUT_JSON)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		TestUtils.enrichWithLibraries(war);

		return war;
	}

	@Test
	// @RunAsClient
	public void testResourceSendingJson() throws Exception {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ARTICLE_SERVICE_URL).register(new BasicTpnsAuthenticator("author", "author"));

		ArticleDTO dto = new ArticleDTO();
		Entity<ArticleDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);
		entity = (Entity<ArticleDTO>) getEntityFromFile();
		Builder request = target.request(MediaType.APPLICATION_JSON);
		request.acceptLanguage(Locale.ENGLISH);
		Response res = request.put(entity);
		Status status = Response.Status.fromStatusCode(res.getStatus());
		org.junit.Assert.assertEquals(Response.Status.OK, status);

		client.close();

	}

	// @Test
	// @RunAsClient
	public void testResourceSendingXML() throws Exception {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ARTICLE_SERVICE_URL).register(new BasicTpnsAuthenticator("author", "author"));

		Entity<?> entity = getEntityFromFile();

		Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
		Status status = Response.Status.fromStatusCode(res.getStatus());
		org.junit.Assert.assertEquals(Response.Status.OK, status);

		client.close();

	}

	private Entity<?> getEntityFromFile() {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INPUT_JSON);
		JsonReader jsonReader = Json.createReader(resourceAsStream);
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		return Entity.json(object.toString());
	}

}
