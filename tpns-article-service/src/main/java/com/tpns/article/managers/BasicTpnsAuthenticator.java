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

	private String username;
	private String password;

	public BasicTpnsAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		headers.add("Authorization", createBasicAuthentication());

	}

	private String createBasicAuthentication() {
		try {
			String token = createToken();
			return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}

	private String createToken() {
		StringBuilder tokenBuilder = new StringBuilder(this.username).append(":").append(this.password);
		return tokenBuilder.toString();
	}

}
