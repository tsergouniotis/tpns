package com.tpns.article.managers;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.repository.ApplicationDAO;
import com.tpns.common.Application;
import com.tpns.utils.CollectionUtils;

@Stateless(name = "articleDispatcher")
@Asynchronous
public class ArticleDispatcher {

	private static Logger LOGGER = LoggerFactory.getLogger(ArticleDispatcher.class.getPackage().getName());

	@Resource
	private SessionContext sessionContext;

	@EJB
	private ApplicationDAO applicationDAO;

	public Future<Boolean> dispatch(Article article) {

		boolean result = false;

		// Checks to see if cancelled
		if (sessionContext.wasCancelCalled()) {
			return null;
		}

		// TODO forward the article to its destinations

		Set<String> destinations = article.getDestinations();

		if (CollectionUtils.isNonEmpty(destinations)) {

			List<Application> applications = applicationDAO.find(destinations.toArray(new String[destinations.size()]));

			if (CollectionUtils.isNonEmpty(applications)) {
				applications.forEach(app -> send(article, app.getEndpoint()));
			}

		}

		send(article, "http://localhost:8080/article-service/v1/article");

		return new AsyncResult<Boolean>(result);

	}

	private void send(Article article, String destination) {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(destination);// .register(new
														// BasicTpnsAuthenticator("author",
														// "author"));

		Article dto = new Article();
		Entity<Article> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
		Status status = Response.Status.fromStatusCode(res.getStatus());

		if (Response.Status.OK != status) {
			StringBuilder builder = new StringBuilder("Article did not submitted successfully to : ").append(destination).append(". Status : ").append(status.getStatusCode())
					.append(":").append(status.getReasonPhrase());
			LOGGER.warn(builder.toString());
		}

		client.close();

	}

}
