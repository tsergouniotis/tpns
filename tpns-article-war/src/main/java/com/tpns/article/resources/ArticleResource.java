package com.tpns.article.resources;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Path;

import com.tpns.article.services.ArticleService;

@Path("/article")
@RolesAllowed({ "AUTHOR", "CHIEF_EDITOR" })
public class ArticleResource {

	@EJB
	private ArticleService service;

	//	@GET
	//	@Path("/{id}")
	//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//	public Response find(@PathParam("id") Long id) throws Exception {
	//		Article article = service.find(id);
	//		return Response.ok(article).build();
	//	}
	//
	//	@PUT
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//	public Response save(Article article) throws Exception {
	//		service.save(article);
	//		return Response.ok(article).build();
	//	}
	//
	//	@POST
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//	public Response update(@Valid Article article) throws Exception {
	//		service.update(article);
	//		return Response.ok(article).build();
	//	}
	//
	//	@DELETE
	//	@Path("/{id}")
	//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//	public Response delete(@PathParam("id") Long id) throws Exception {
	//		service.delete(id);
	//		return Response.ok().build();
	//	}

}
