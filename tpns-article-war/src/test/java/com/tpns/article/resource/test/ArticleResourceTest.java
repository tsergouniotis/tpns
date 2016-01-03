package com.tpns.article.resource.test;

import java.io.InputStream;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tpns.article.repository.test.TestUtils;
import com.tpns.common.ws.rs.client.BasicTpnsAuthenticator;

@RunWith(Arquillian.class)
public class ArticleResourceTest {

	private static final String INPUT_JSON = "data/article.json";
	private static final String ARTICLE_SERVICE_URL = "http://localhost:8080/article-service/v1/article";

	@Inject
	private Client client;

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
	public void testResourceSendingXML() throws Exception {

		WebTarget target = client.target(ARTICLE_SERVICE_URL);

		Entity<?> entity = getEntityFromFile();

		Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
		Status status = Response.Status.fromStatusCode(res.getStatus());
		org.junit.Assert.assertEquals(Response.Status.OK, status);

	}

	private Entity<?> getEntityFromFile() {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(INPUT_JSON);
		JsonReader jsonReader = Json.createReader(resourceAsStream);
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		return Entity.json(object.toString());
	}

}
