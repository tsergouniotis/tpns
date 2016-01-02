package com.tpns.user.resources;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tpns.user.domain.User;
import com.tpns.user.services.UserService;

@Path("/user")
public class UserResource {

	@EJB
	private UserService service;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response find(@PathParam("id") Long id) throws Exception {
		User user = service.find(id);
		return Response.ok(user).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response save(@Valid User user) throws Exception {
		service.save(user);
		return Response.ok(user).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response update(@Valid User article) throws Exception {
		service.update(article);
		return Response.ok(article).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response delete(@PathParam("id") Long id) throws Exception {
		service.delete(id);
		return Response.ok().build();
	}

}
