package com.tpns.article.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.converters.ArticleConverter;
import com.tpns.article.domain.Article;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.repository.ApplicationDAO;
import com.tpns.common.domain.Application;
import com.tpns.utils.CollectionUtils;

@Stateless(name = "articleDispatcher")
@Asynchronous
public class ArticleDispatcher {

	private static Logger LOGGER = LoggerFactory.getLogger(ArticleDispatcher.class.getPackage().getName());

	@Resource
	private SessionContext sessionContext;

	@Inject
	private ApplicationDAO applicationDAO;

	@Inject
	private ArticleConverter articleConverter;

	public Future<Boolean> dispatch(final Article article) {

		final boolean result = false;

		// Checks to see if cancelled
		if (sessionContext.wasCancelCalled()) {
			return null;
		}

		// TODO forward the article to its destinations

		final Set<String> destinations = article.getDestinations();

		if (CollectionUtils.isNonEmpty(destinations)) {

			// String[] array = destinations.toArray(new
			// String[destinations.size()]);
			final List<Application> applications = applicationDAO.find(new ArrayList<String>(destinations));

			if (CollectionUtils.isNonEmpty(applications)) {
				applications.forEach(app -> send(article, app.getEndpoint()));
			}

		}

		return new AsyncResult<Boolean>(result);

	}

	private void send(final Article article, final String destination) {

		final Client client = ClientBuilder.newClient().register(new BasicTpnsAuthenticator("author", "author"));

		final WebTarget target = client.target(destination);

		final ArticleDTO dto = articleConverter.toDto(article);
		final Entity<ArticleDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		final Response res = target.request(MediaType.APPLICATION_JSON).put(entity);
		final Status status = Response.Status.fromStatusCode(res.getStatus());

		if (Response.Status.OK != status) {
			final StringBuilder builder = new StringBuilder("Article did not submitted successfully to : ").append(destination).append(". Status : ")
					.append(status.getStatusCode()).append(":").append(status.getReasonPhrase());
			LOGGER.warn(builder.toString());
		}

		client.close();

	}

}
