package com.tpns.article.resource.test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
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
import org.junit.Assert;
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

		try {
			WebArchive war = ShrinkWrap.create(WebArchive.class, "tpns.war").addPackages(true, "com.tpns").addClass(BasicTpnsAuthenticator.class);

			TestUtils.addResources(war);
			TestUtils.enrichWithLibraries(war);
			war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsResource(INPUT_JSON);
			return war;
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
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
