package com.tpns.article.providers;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class TpnsResponseFilter implements ContainerResponseFilter {

	private static final int AUTH_TOKEN = 0;
	private static final String SERVICE_KEY = null;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*"); // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", SERVICE_KEY + ", " + AUTH_TOKEN);
	}

}
