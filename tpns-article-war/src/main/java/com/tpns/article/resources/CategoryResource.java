package com.tpns.article.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tpns.article.services.CategoryService;

@Path("/category")
public class CategoryResource {

	@EJB
	private CategoryService service;

	@GET
	@RolesAllowed({ "AUTHOR", "CHIEF_EDITOR" })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll() throws Exception {
		List<String> categories = service.getCategories();
		return Response.ok(categories).build();
	}

}
