package com.tpns.article.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.services.ArticleService;
import com.tpns.utils.CollectionUtils;

@Path("/article")
@RolesAllowed({ "AUTHOR", "CHIEF_EDITOR" })
public class ArticleResource {

	@EJB
	private ArticleService service;

	@GET
	@Path("/published")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findPublished() throws Exception {
		List<ArticleDTO> articles = service.findPublished();
		return Response.ok(articles).build();
	}

	@GET
	@Path("/findPublishedByCategory")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findPublished(@QueryParam("catName") String catName) throws Exception {
		List<ArticleDTO> articles = service.findByCategory(catName);
		return Response.ok(articles).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response find(@PathParam("id") Long id) throws Exception {
		ArticleDTO article = service.find(id);
		if (null != article) {
			return Response.ok(article).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/search/{key}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response seach(@PathParam("key") String key) throws Exception {
		List<ArticleDTO> articles = service.search(key);
		if (CollectionUtils.isNonEmpty(articles)) {
			return Response.ok(articles).build();
		} else {
			return Response.status(Status.NO_CONTENT).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response save(ArticleDTO article) throws Exception {
		service.save(article);
		return Response.ok(article).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response update(ArticleDTO article) throws Exception {
		service.update(article);
		return Response.ok(article).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delete(@PathParam("id") Long id) throws Exception {
		service.delete(id);
		return Response.ok().build();
	}

}
