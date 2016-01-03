package com.tpns.common.ws.rs.client;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Named
@ApplicationScoped
public class ClientFactory {

	@Produces
	@RequestScoped
	public Client createClient() {
		return ClientBuilder.newClient().register(new BasicTpnsAuthenticator("author", "author"));
	}

	public void dispose(@Disposes Client client) {
		client.close();
	}

}
