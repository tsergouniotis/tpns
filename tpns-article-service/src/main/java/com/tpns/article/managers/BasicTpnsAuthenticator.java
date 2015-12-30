package com.tpns.article.managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

@Provider
public class BasicTpnsAuthenticator implements ClientRequestFilter {

	private final String username;
	private final String password;

	public BasicTpnsAuthenticator(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		final MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		headers.add("Authorization", getBasicAuthentication());

	}

	private String getBasicAuthentication() {
		try {
			final String token = this.username + ":" + this.password;
			return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
		} catch (final UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}

}
